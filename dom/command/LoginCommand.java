package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.user.UserInputMapper;
import org.soen387.dom.model.user.User;

public class LoginCommand extends ValidatorCommand {
	
	public User user;
	
	public LoginCommand(Helper helper) {
		super(helper);
	}
	
	@Override
	public void process() throws CommandException {
		try {
			String username = helper.getString("user");
			String password = helper.getString("pass");
			
			if(username == null && password == null) {
				throw new CommandException("Username and password not given.");				
			}

			User user = UserInputMapper.findByUsernameAndPass(username, password);
			helper.setSessionAttribute("CurrentUser", user); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommandException(e);
		} catch (MapperException e) {
			throw new CommandException("No user exists for that username and password combo.");
		}
	}
}
