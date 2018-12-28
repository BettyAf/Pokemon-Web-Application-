package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.mapper.play.PlayInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.status.PlayStatus;

public class ViewHandCommand extends ValidatorCommand {
	
	public ViewHandCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your hand.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			
			IGame game = GameInputMapper.find(id);
			if(game == null) {
				throw new CommandException("Game does not exist.");
			} else if (game.getChallengee().getId() != player.getId() && game.getChallenger().getId() != player.getId()) {
				throw new CommandException("Cannot view the hand in a game you're not playing in.");
			}
			
			IPlay play = PlayInputMapper.findByPlayerAndGame(player, game);
			if(play.getStatus() == PlayStatus.retired.ordinal()) {
				throw new CommandException("Cannot view your hand in a game that you retired from.");
			}
			
			helper.setSessionAttribute("hand", play.getHand().getCardsInPlay()); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
