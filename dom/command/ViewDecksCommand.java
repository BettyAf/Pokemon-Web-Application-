package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;

public class ViewDecksCommand extends ValidatorCommand {
	
	public ViewDecksCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view your decks.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			helper.setSessionAttribute("playerDecks", player.getDecks()); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
