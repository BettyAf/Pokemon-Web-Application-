package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.challenge.ChallengeInputMapper;
import org.soen387.dom.mapper.deck.DeckInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.bench.Bench;
import org.soen387.dom.model.bench.BenchFactory;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.cardInPlay.CardInPlayFactory;
import org.soen387.dom.model.challenge.ChallengeFactory;
import org.soen387.dom.model.challenge.IChallenge;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.discard.Discard;
import org.soen387.dom.model.discard.DiscardFactory;
import org.soen387.dom.model.game.Game;
import org.soen387.dom.model.game.GameFactory;
import org.soen387.dom.model.hand.Hand;
import org.soen387.dom.model.hand.HandFactory;
import org.soen387.dom.model.play.Play;
import org.soen387.dom.model.play.PlayFactory;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.CardLocation;
import org.soen387.dom.status.ChallengeStatus;
import org.soen387.dom.status.PlayStatus;

public class AcceptChallengeCommand extends ValidatorCommand {
	
	public AcceptChallengeCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to accept a challenge.");
			}
			
			IPlayer player = PlayerInputMapper.find(user.getId()); 
		
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			Long version = helper.getLong("version");
			if(version == null) {
				throw new CommandException("Need to specify a challenge version.");
			}
			
			IChallenge challenge = ChallengeInputMapper.find(id);
			if(challenge == null) {
				throw new CommandException("Challenge does not exist.");
			} else if (challenge.getChallengee().getId() != player.getId()) {
				throw new CommandException("Cannot accecpt a challenge that is not issued to you.");
			} else if (challenge.getStatus() != ChallengeStatus.Open.ordinal()) {
				throw new CommandException("Cannot accept a challenge that is not open.");
			}
			
			Long deck = helper.getLong("deck");
			IDeck challengeeDeck = DeckInputMapper.find(deck);
			if(challengeeDeck == null) {
				throw new CommandException("This deck does not exist.");
			} else if(challengeeDeck.getPlayer().getId() != player.getId()) {
				throw new CommandException("This deck does not belong to this user.");
			}
			
			// Create Game
			Game g = GameFactory.createNew(challenge.getChallenger(), player, challenge.getChallengerDeck(), challengeeDeck, challenge.getChallenger().getId());
			
			// Create play for challenger (empty hand, discard, bench)
			Play challengerPlay = PlayFactory.createNew(g, challenge.getChallenger(), challenge.getChallengerDeck(), PlayStatus.playing.ordinal());
			Hand h1 = HandFactory.createNew(challengerPlay);
			Discard d1 = DiscardFactory.createNew(challengerPlay);
			Bench b1 = BenchFactory.createNew(challengerPlay);
			challengerPlay.setHand(h1);
			challengerPlay.setDiscard(d1);
			challengerPlay.setBench(b1);
			ICard card = challenge.getChallengerDeck().getCards().get(0);
			CardInPlayFactory.createNew(challengerPlay, CardLocation.Hand.ordinal(), card);
			
			// Create play for challengee (empty hand, discard, bench)
			Play challengeePlay = PlayFactory.createNew(g, player, challengeeDeck, PlayStatus.playing.ordinal());
			Hand h2 = HandFactory.createNew(challengeePlay);
			Discard d2 = DiscardFactory.createNew(challengeePlay);
			Bench b2 = BenchFactory.createNew(challengeePlay);
			challengeePlay.setHand(h2);
			challengeePlay.setDiscard(d2);
			challengeePlay.setBench(b2);
			
			// Set plays in game
			g.setChallengerPlay(challengerPlay);
			g.setChallengeePlay(challengeePlay);

			// Update challenge status
			ChallengeFactory.registerDirty(challenge.getId(), version, challenge.getChallenger(), challenge.getChallengee(), challenge.getChallengerDeck(), ChallengeStatus.Accepted.ordinal());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
