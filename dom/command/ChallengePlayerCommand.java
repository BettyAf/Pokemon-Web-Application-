package org.soen387.dom.command;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.challenge.ChallengeInputMapper;
import org.soen387.dom.mapper.deck.DeckInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.dom.model.challenge.ChallengeFactory;
import org.soen387.dom.model.challenge.IChallenge;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.ChallengeStatus;

public class ChallengePlayerCommand extends ValidatorCommand {

	public ChallengePlayerCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to challenge another player.");
			}
			
			IPlayer challenger = PlayerInputMapper.find(user.getId()); 
			if(challenger.getDecks().isEmpty()) {
				throw new CommandException("Must have at least 1 deck uploaded to challenge another player.");
			}
			
			Long deck = helper.getLong("deck");
			IDeck challengerDeck = DeckInputMapper.find(deck);
			if(challengerDeck == null) {
				throw new CommandException("This deck does not exist.");
			} else if(challengerDeck.getPlayer().getId() != challenger.getId()) {
				throw new CommandException("This deck does not belong to this user.");
			}
			
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			IPlayer challengee = PlayerInputMapper.find(id);
			if(challengee == null) {
				throw new CommandException("Player does not exist.");
			} else if (challenger.getId() == challengee.getId()) {
				throw new CommandException("Cannot challenge yourself.");
			}
			
			List<IChallenge> challenges = ChallengeInputMapper.findByChallengerAndChallengee(challenger, challengee);
			if(!challenges.isEmpty() && openChallengeExists(challenges)) {
				throw new CommandException("An open challenge already exists between you and this player.");
			}
						
			Challenge c = ChallengeFactory.createNew(challenger, challengee, challengerDeck, ChallengeStatus.Open.ordinal());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}
	
	private boolean openChallengeExists(List<IChallenge> challenges) {
		for(IChallenge c : challenges) {
			if(c.getStatus() == ChallengeStatus.Open.ordinal()) {
				return true;
			}
		}
		return false;
	}
}
