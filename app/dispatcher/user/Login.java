package org.soen387.app.dispatcher.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.dom.command.LoginCommand;

/*
 * LoginDispatcher: https://users.encs.concordia.ca/~sthiel/soen387/Thesis_Stuart.pdf
 * [November 16th]
 * */
public class Login extends Dispatcher {
	
	public Login(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}
	
	@Override
	public void execute() throws ServletException, IOException{
		try {
			myRequest.getSession().invalidate();
			new LoginCommand(myHelper).execute();
			myHelper.setRequestAttribute("message", "Command Success: Successfully logged in!");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (CommandException e) {
			myHelper.setRequestAttribute("message", "Command Failure: " + e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		} 
	}
}
