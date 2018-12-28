package org.soen387.app.dispatcher.game;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.dom.command.ListGamesCommand;

public class ListGames extends Dispatcher {

	public ListGames(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}
	
	@Override
	public void execute() throws ServletException, IOException {
		try {
			new ListGamesCommand(myHelper).execute();
			forward("/WEB-INF/jsp/listGames.jsp");
		} catch (CommandException e) {
			myHelper.setRequestAttribute("message", "Command Failure: " + e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		} 
		
	}
}
