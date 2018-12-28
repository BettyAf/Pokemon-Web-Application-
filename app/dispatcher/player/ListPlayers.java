package org.soen387.app.dispatcher.player;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.dom.command.ListPlayersCommand;

public class ListPlayers extends Dispatcher {

	public ListPlayers(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		try {
			new ListPlayersCommand(myHelper).execute();
			forward("/WEB-INF/jsp/listPlayers.jsp");
		} catch (CommandException e) {
			myHelper.setRequestAttribute("message", "Command Failure: " + e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		} 
		
	}
}
