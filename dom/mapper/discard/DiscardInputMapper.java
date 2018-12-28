package org.soen387.dom.mapper.discard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.mapper.cardInPlay.CardInPlayInputMapper;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.discard.Discard;
import org.soen387.dom.model.discard.DiscardFactory;
import org.soen387.dom.model.discard.DiscardProxy;
import org.soen387.dom.model.discard.IDiscard;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.PlayProxy;
import org.soen387.dom.status.CardLocation;
import org.soen387.ser.discard.DiscardFinder;

public class DiscardInputMapper {

	public static Discard find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Discard.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = DiscardFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Discard with this id does not exist.");
		} 
		return getDiscard(rs);
	}
	
	public static List<IDiscard> findByPlay(IPlay play) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = DiscardFinder.findByPlay(play.getId());
		if(!rs.next()) {
			throw new MapperException("Play with this id does not exist.");
		} 
		return getDiscardList(rs);
	}
	
	public static List<IDiscard> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = DiscardFinder.findAll();
		return getDiscardList(rs);
	}
	
	
	private static Discard getDiscard(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		long playId = rs.getLong("play");
		IPlay play = new PlayProxy(playId);
		List<ICardInPlay> cards = CardInPlayInputMapper.findByPlayAndLocation(play, CardLocation.Discard.ordinal());
		Discard d = DiscardFactory.createClean(id, rs.getLong("version"), new PlayProxy(playId), cards);
		return d;
	}
	
	private static List<IDiscard> getDiscardList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IDiscard> discards = new ArrayList<IDiscard>();
		while(rs.next()) {
			discards.add(new DiscardProxy(rs.getLong("id")));
		}
		return discards; 
	}
}
