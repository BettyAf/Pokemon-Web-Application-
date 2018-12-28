package org.soen387.dom.mapper.card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.card.Card;
import org.soen387.dom.model.card.CardFactory;
import org.soen387.dom.model.card.CardProxy;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.deck.DeckProxy;
import org.soen387.ser.card.CardFinder;

public class CardInputMapper {
	
	public static Card find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Card.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = CardFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Card with this id does not exist.");
		} 
		return getCard(rs);
	}
	
	public static List<ICard> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = CardFinder.findAll();
		return getCardList(rs);
	}
	
	
	private static Card getCard(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		Card c = CardFactory.createClean(id, rs.getLong("version"), rs.getString("type"), rs.getString("name"), rs.getString("basic"), new DeckProxy(rs.getLong("deck")));
		return c;
	}
	
	private static List<ICard> getCardList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<ICard> cards = new ArrayList<ICard>();
		while(rs.next()) {
			cards.add(new CardProxy(rs.getLong("id")));
		}
		return cards; 
	}

}
