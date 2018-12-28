package org.soen387.app.dispatcher.challenge;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.command.ChallengePlayerCommand;

public class ChallengePlayer extends Dispatcher {

	public ChallengePlayer(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		try {
			String path = myRequest.getServletPath();
			myHelper.setRequestAttribute("path", path);
			new ChallengePlayerCommand(myHelper).execute();
			UoW.getCurrent().commit();
			myHelper.setRequestAttribute("message", "Command Success: Challenge issued to player!");
			forward("/WEB-INF/jsp/success.jsp");
		} catch (CommandException | InstantiationException | IllegalAccessException | MapperException | SQLException e) {
			myHelper.setRequestAttribute("message", "Command Failure: " + e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		} 
		
	}
}
