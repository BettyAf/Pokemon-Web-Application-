package org.soen387.dom.mapper.bench;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.mapper.cardInPlay.CardInPlayInputMapper;
import org.soen387.dom.model.bench.Bench;
import org.soen387.dom.model.bench.BenchFactory;
import org.soen387.dom.model.bench.BenchProxy;
import org.soen387.dom.model.bench.IBench;
import org.soen387.dom.model.cardInPlay.ICardInPlay;
import org.soen387.dom.model.play.IPlay;
import org.soen387.dom.model.play.PlayProxy;
import org.soen387.dom.status.CardLocation;
import org.soen387.ser.bench.BenchFinder;

public class BenchInputMapper {

	public static Bench find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Bench.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = BenchFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Bench with this id does not exist.");
		} 
		return getBench(rs);
	}
	
	public static List<IBench> findByPlay(IPlay play) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = BenchFinder.findByPlay(play.getId());
		if(!rs.next()) {
			throw new MapperException("Play with this id does not exist.");
		} 
		return getBenchList(rs);
	}
	
	public static List<IBench> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = BenchFinder.findAll();
		return getBenchList(rs);
	}
	
	
	private static Bench getBench(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		long playId = rs.getLong("play");
		IPlay play = new PlayProxy(playId);
		List<ICardInPlay> cards = CardInPlayInputMapper.findByPlayAndLocation(play, CardLocation.Bench.ordinal());
		Bench b = BenchFactory.createClean(id, rs.getLong("version"), new PlayProxy(playId), cards);
		return b;
	}
	
	private static List<IBench> getBenchList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IBench> bench = new ArrayList<IBench>();
		while(rs.next()) {
			bench.add(new BenchProxy(rs.getLong("id")));
		}
		return bench; 
	}
}
