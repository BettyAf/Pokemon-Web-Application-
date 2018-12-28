package org.soen387.app.dispatcher.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;

/*
 * LogoutDispatcher: https://users.encs.concordia.ca/~sthiel/soen387/Thesis_Stuart.pdf
 * [November 16th]
 * */
public class Logout extends Dispatcher {
	
	public Logout(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}
	
	@Override
	public void execute() throws ServletException, IOException {
		try {
			if(myHelper.getSessionAttribute("CurrentUser") == null) {
				myHelper.setRequestAttribute("message", "No user is logged in!");
				forward("/WEB-INF/jsp/fail.jsp");
			}
			myRequest.getSession().invalidate();
			myHelper.setRequestAttribute("message", "Command Success: Successfully logged out!");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (Exception e) {
			myHelper.setRequestAttribute("message", "Command Failure: Could not log out.");
			forward("/WEB-INF/jsp/fail.jsp");
		}
	}
}
