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

public class ViewDiscardPileCommand extends ValidatorCommand {
	
	public ViewDiscardPileCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your discard piles.");
			}
			IPlayer userPlayer = PlayerInputMapper.find(user.getId());
			
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("(/[a-zA-Z]+)*", "");

			// �\_(@.@)_/�
			long gameId = Long.parseLong(path.substring(1, path.indexOf("/", 1)));
			long playerId = Long.parseLong(path.substring(path.indexOf("/", 1)+1));
			
			IGame game = GameInputMapper.find(gameId);
			if(game == null) {
				throw new CommandException("Game does not exist.");
			} else if(game.getChallengee().getId() != playerId && game.getChallenger().getId() != playerId) {
				throw new CommandException("This player is not a part if this game.");
			} else if (game.getChallengee().getId() != userPlayer.getId() && game.getChallenger().getId() != userPlayer.getId()) {
				throw new CommandException("Cannot view the discard pile in a game you're not playing in.");
			}
			
			IPlayer player = PlayerInputMapper.find(playerId);
			IPlay play = PlayInputMapper.findByPlayerAndGame(player, game);
			
			helper.setSessionAttribute("discard", play.getDiscard().getCardsInPlay()); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
