package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.mapper.play.PlayInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.challenge.Challenge;
import org.soen387.dom.model.challenge.ChallengeFactory;
import org.soen387.dom.model.game.GameFactory;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.PlayFactory;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.ChallengeStatus;
import org.soen387.dom.status.PlayStatus;

public class RetireCommand extends ValidatorCommand {
	
	public RetireCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to retire from a game.");
			}
			
			IPlayer player = PlayerInputMapper.find(user.getId()); 
		
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			IGame game = GameInputMapper.find(id);
			if(game == null) {
				throw new CommandException("Game does not exist.");
			} else if (game.getChallengee().getId() != player.getId() && game.getChallenger().getId() != player.getId()) {
				throw new CommandException("Cannot retire from a game that is not yours.");
			} 
						
			IPlay playerPlay = PlayInputMapper.findByPlayerAndGame(player, game);
			
			if(playerPlay.getStatus() == PlayStatus.retired.ordinal()) {
				throw new CommandException("You're already retired from this game.");
			}
			
			PlayFactory.registerDirty(playerPlay.getId(), playerPlay.getVersion(), game, player,playerPlay.getDeck(), playerPlay.getHand(), playerPlay.getDiscard(), playerPlay.getBench(), PlayStatus.retired.ordinal());
			GameFactory.registerDirty(game.getId(), game.getVersion(), game.getChallenger(), game.getChallengee(), game.getChallengerDeck(), game.getChallengeeDeck(), game.getChallengerPlay(), game.getChallengeePlay(), game.getCurrentPlayer());
			
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
