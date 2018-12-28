package org.soen387.dom.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.dsrg.soenea.domain.role.IRole;
import org.dsrg.soenea.domain.role.impl.GuestRole;
import org.soen387.dom.mapper.user.UserInputMapper;
import org.soen387.dom.model.player.Player;
import org.soen387.dom.model.player.PlayerFactory;
import org.soen387.dom.model.role.LoggedInRole;
import org.soen387.dom.model.user.User;
import org.soen387.dom.model.user.UserFactory;

public class RegisterCommand extends ValidatorCommand {

	public RegisterCommand(Helper helper) {
		super(helper);
	}
	
	@Override
	public void process() throws CommandException {
		try {
			String username = helper.getString("user");
			String password = helper.getString("pass");
			
			if(username == "" && password == "") {
				throw new CommandException("Username and password not given.");				
			} else if(username == "") {
				throw new CommandException("Username not given.");								
			} else if(password == "") {
				throw new CommandException("Password not given.");								
			}
			
			try {
				User user = UserInputMapper.findByUsername(username);
				throw new CommandException("A User already exists with this username");
			} catch(MapperException e) {
				// Bad practice, will fix if I have time. 
			}
			
			User u = UserFactory.createNew(username, password);
			Player p = PlayerFactory.createNew(u);
			helper.setSessionAttribute("CurrentUser", u); 
		} catch(SQLException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}
	}

}
