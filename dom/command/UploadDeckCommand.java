package org.soen387.dom.command;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.ValidatorCommand;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.dom.mapper.player.PlayerInputMapper;
import org.soen387.dom.model.card.CardFactory;
import org.soen387.dom.model.deck.Deck;
import org.soen387.dom.model.deck.DeckFactory;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.user.IUser;

public class UploadDeckCommand extends ValidatorCommand {

	public UploadDeckCommand(Helper helper) {
		super(helper);
	}
	
	@Override
	public void process() throws CommandException {
		try {
			String deck = helper.getString("deck");
		
			if(deck == null) {
				throw new CommandException("Deck was not given.");				
			} 
			
			String[] cardRow = deck.split("\n");
			if(cardRow.length > 40 || cardRow.length < 40) {
				throw new CommandException("Deck must have 40 cards. This one has " + cardRow.length + " cards.");
			}

			IUser user = (IUser) helper.getSessionAttribute("CurrentUser");
			if(user == null) {
				throw new CommandException("Must be logged in to upload a deck.");
			}
			IPlayer player = PlayerInputMapper.find(user.getId());
			Deck d = DeckFactory.createNew(player);
			buildCardsFromFile(cardRow, d);
			 
		} catch (SQLException | MapperException e) {
			e.printStackTrace();
			throw new CommandException(e);
		}
	}
	
	private void buildCardsFromFile(String[] cardRows, Deck deck) throws SQLException {
		for(String row : cardRows) {
			String[] cardValues = row.split(" ");
			cardValues[1] = cardValues[1].replaceAll("\"", "");
			if(cardValues.length == 3) {
				cardValues[2] = cardValues[2].replaceAll("\"", "");
				CardFactory.createNew(cardValues[0], cardValues[1], cardValues[2], deck);
			} else {
				CardFactory.createNew(cardValues[0], cardValues[1], "", deck);
			}
		}
	}
}
