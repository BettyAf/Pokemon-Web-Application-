package org.soen387.dom.mapper.hand;

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
import org.soen387.dom.model.hand.Hand;
import org.soen387.dom.model.hand.HandFactory;
import org.soen387.dom.model.hand.HandProxy;
import org.soen387.dom.model.hand.IHand;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.PlayProxy;
import org.soen387.dom.status.CardLocation;
import org.soen387.ser.hand.HandFinder;

public class HandInputMapper {

	public static Hand find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Hand.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = HandFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Hand with this id does not exist.");
		} 
		return getHand(rs);
	}
	
	public static List<IHand> findByPlay(IPlay play) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = HandFinder.findByPlay(play.getId());
		if(!rs.next()) {
			throw new MapperException("Play with this id does not exist.");
		} 
		return getHandList(rs);
	}
	
	public static List<IHand> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = HandFinder.findAll();
		return getHandList(rs);
	}
	
	
	private static Hand getHand(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		long playId = rs.getLong("play");
		IPlay play = new PlayProxy(playId);
		List<ICardInPlay> cards = CardInPlayInputMapper.findByPlayAndLocation(play, CardLocation.Hand.ordinal());
		Hand h = HandFactory.createClean(id, rs.getLong("version"), new PlayProxy(playId), cards);
		return h;
	}
	
	private static List<IHand> getHandList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IHand> hands = new ArrayList<IHand>();
		while(rs.next()) {
			hands.add(new HandProxy(rs.getLong("id")));
		}
		return hands; 
	}
}
