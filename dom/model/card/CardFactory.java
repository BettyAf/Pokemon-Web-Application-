package org.soen387.dom.model.card;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.ser.card.CardTDG;

public class CardFactory {

	public static Card createNew(String type, String name, String basic, IDeck deck) throws SQLException {
		Card c = new Card(CardTDG.getMaxId(), 1, type, name, basic, deck);
		try {
			UoW.getCurrent().registerNew(c);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return c;
	}
	
	public static Card createClean(long id, long version, String type, String name, String basic, IDeck deck) throws SQLException {
		Card c = new Card(id, version, type, name, basic, deck);
		UoW.getCurrent().registerClean(c);
		return c;	
	}
}
