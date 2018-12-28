package org.soen387.dom.mapper.deck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.card.CardProxy;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.deck.Deck;
import org.soen387.dom.model.deck.DeckFactory;
import org.soen387.dom.model.deck.DeckProxy;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.player.PlayerProxy;
import org.soen387.ser.card.CardFinder;
import org.soen387.ser.deck.DeckFinder;

public class DeckInputMapper {
	
	public static Deck find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Deck.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = DeckFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Deck with this id does not exist.");
		} 
		return getDeck(rs);
	}
	
	public static List<IDeck> findByPlayer(IPlayer player) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = DeckFinder.findByPlayer(player.getId());
		return getDeckList(rs);
	}
	
	private static Deck getDeck(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		ResultSet rsc = CardFinder.findByDeck(id);
		List<ICard> cards = getCardList(rsc);
		Deck d = DeckFactory.createClean(id, rs.getLong("version"), new PlayerProxy(rs.getLong("player")), cards);
		return d;
	}
	
	private static List<IDeck> getDeckList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IDeck> decks = new ArrayList<IDeck>();
		while(rs.next()) {
			decks.add(new DeckProxy(rs.getLong("id")));
		}
		return decks; 
	}
	
	private static List<ICard> getCardList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<ICard> cards = new ArrayList<ICard>();
		while(rs.next()) {
			cards.add(new CardProxy(rs.getLong("id")));
		}
		return cards; 
	}
}
