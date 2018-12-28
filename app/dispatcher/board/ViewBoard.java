package org.soen387.app.dispatcher.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsrg.soenea.application.servlet.dispatcher.Dispatcher;
import org.dsrg.soenea.domain.command.CommandException;
import org.soen387.dom.command.ViewBoardCommand;

public class ViewBoard extends Dispatcher {

	public ViewBoard(HttpServletRequest request, HttpServletResponse response) {
		super.init(request, response);
	}

	@Override
	public void execute() throws ServletException, IOException {
		try {
			String path = myRequest.getServletPath();
			myHelper.setRequestAttribute("path", path);
			new ViewBoardCommand(myHelper).execute();
			forward("/WEB-INF/jsp/viewBoard.jsp");
		} catch (CommandException e) {
			myHelper.setRequestAttribute("message", "Command Failure: " + e.getMessage());
			forward("/WEB-INF/jsp/fail.jsp");
		} 
		
	}
}
