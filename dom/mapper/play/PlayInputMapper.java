package org.soen387.dom.mapper.play;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.bench.BenchProxy;
import org.soen387.dom.model.deck.DeckProxy;
import org.soen387.dom.model.discard.DiscardProxy;
import org.soen387.dom.model.game.GameProxy;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.hand.HandProxy;
import org.soen387.dom.model.play.Play;
import org.soen387.dom.model.play.PlayFactory;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.player.PlayerProxy;
import org.soen387.ser.play.PlayFinder;

public class PlayInputMapper {
	
	public static Play find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Play.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = PlayFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Play with this id does not exist.");
		} 
		return getPlay(rs);
	}
	
	public static Play findByPlayerAndGame(IPlayer player, IGame game) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = PlayFinder.findByPlayerAndGame(player.getId(), game.getId());
		if(!rs.next()) {
			throw new MapperException("Play with this id does not exist.");
		} 
		return getPlay(rs);
	}
	
	private static Play getPlay(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		Play p = PlayFactory.createClean(id, rs.getLong("version"), new GameProxy(rs.getLong("game")), new PlayerProxy(rs.getLong("player")), new DeckProxy(rs.getLong("deck")), new HandProxy(rs.getLong("hand")), new DiscardProxy(rs.getLong("discard")), new BenchProxy(rs.getLong("bench")), rs.getInt("status"));
		return p;
	}

}
