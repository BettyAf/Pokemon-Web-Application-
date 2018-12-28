package org.soen387.dom.mapper.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.mapper.deck.DeckInputMapper;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.player.Player;
import org.soen387.dom.model.player.PlayerFactory;
import org.soen387.dom.model.player.PlayerProxy;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.model.user.UserProxy;
import org.soen387.ser.player.PlayerFinder;

public class PlayerInputMapper {

	public static Player find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Player.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentotyMap.
		}
		
		ResultSet rs = PlayerFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Player with this id does not exist.");
		} 
		return getPlayer(rs);
	}
	
	public static Player findByUser(IUser user) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = PlayerFinder.findByUser(user.getId());
		if(!rs.next()) {
			throw new MapperException("Player with this user id does not exist.");
		} 
		try {
			return IdentityMap.get(rs.getLong("id"), Player.class);
		} catch(DomainObjectNotFoundException e) {
		} catch(ObjectRemovedException e) {
		}
		return getPlayer(rs);
	}
	
	public static List<IPlayer> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = PlayerFinder.findAll();
		return getPlayerList(rs);
	}
	
	
	private static Player getPlayer(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		IPlayer player = new PlayerProxy(id);
		List<IDeck> decks = DeckInputMapper.findByPlayer(player);
		Player p = PlayerFactory.createClean(id, rs.getLong("version"), new UserProxy(rs.getLong("user")), decks);
		return p;
	}
	
	private static List<IPlayer> getPlayerList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IPlayer> players = new ArrayList<IPlayer>();
		while(rs.next()) {
			players.add(new PlayerProxy(rs.getLong("id")));
		}
		return players; 
	}
}
