package org.soen387.dom.mapper.cardInPlay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.card.CardProxy;
import org.soen387.dom.model.cardInPlay.CardInPlay;
import org.soen387.dom.model.cardInPlay.CardInPlayFactory;
import org.soen387.dom.model.cardInPlay.CardInPlayProxy;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.PlayProxy;
import org.soen387.ser.playCard.PlayCardFinder;

public class CardInPlayInputMapper {
	
	public static CardInPlay find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, CardInPlay.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = PlayCardFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Card in play with this id does not exist.");
		} 
		return getCardInPlay(rs);
	}
	
	public static List<ICardInPlay> findByPlayAndLocation(IPlay play, int location) throws SQLException, MapperException, DomainObjectCreationException {	
		ResultSet rs = PlayCardFinder.findByPlayAndLocation(play.getId(), location);
		return getCardInPlayList(rs);
	}
	
	public static List<ICardInPlay> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = PlayCardFinder.findAll();
		return getCardInPlayList(rs);
	}
	
	
	private static CardInPlay getCardInPlay(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		CardInPlay c = CardInPlayFactory.createClean(id, rs.getLong("version"), new PlayProxy(rs.getLong("play")), rs.getInt("location"), new CardProxy(rs.getLong("card")), new CardProxy(rs.getLong("energy")), new CardProxy(rs.getLong("basic")));
		return c;
	}
	
	private static List<ICardInPlay> getCardInPlayList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<ICardInPlay> cards = new ArrayList<ICardInPlay>();
		while(rs.next()) {
			cards.add(new CardInPlayProxy(rs.getLong("id")));
		}
		return cards; 
	}

}
