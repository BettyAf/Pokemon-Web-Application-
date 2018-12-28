package org.soen387.dom.command;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.game.GameInputMapper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;

public class ListGamesCommand extends ValidatorCommand {
	
	public ListGamesCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your games.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			List<IGame> games = GameInputMapper.findAllByPlayer(player);
			helper.setRequestAttribute("games", games); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
