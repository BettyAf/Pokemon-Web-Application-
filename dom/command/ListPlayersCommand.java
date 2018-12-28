package org.soen387.dom.command;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.user.UserInputMapper;
import org.soen387.dom.model.user.IUser;

public class ListPlayersCommand extends ValidatorCommand {
	
	public ListPlayersCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view players.");
			}
			
			List<IUser> usersAsPlayers = UserInputMapper.findAll();
			helper.setSessionAttribute("users", usersAsPlayers); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}
}
