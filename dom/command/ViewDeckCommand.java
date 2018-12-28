package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.deck.DeckInputMapper;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.user.IUser;

public class ViewDeckCommand extends ValidatorCommand {

	public ViewDeckCommand(Helper helper) {
		super(helper);
	}

	@Override
	public void process() throws CommandException {
		try {
			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to view decks.");
			}
			String path = (String) helper.getRequestAttribute("path");
			path = path.replaceAll("[a-zA-Z/]+", "");
			long id = Long.parseLong(path);
			IDeck deck = DeckInputMapper.find(id);
			helper.setRequestAttribute("deck", deck); 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}		
	}

}
