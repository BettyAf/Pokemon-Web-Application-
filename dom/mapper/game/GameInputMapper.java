package org.soen387.dom.mapper.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.deck.DeckProxy;
import org.soen387.dom.model.game.Game;
import org.soen387.dom.model.game.GameFactory;
import org.soen387.dom.model.game.GameProxy;
import org.soen387.dom.model.game.IGame;
import org.soen387.dom.model.play.PlayProxy;
import org.soen387.dom.model.player.IPlayer;
import org.soen387.dom.model.player.PlayerProxy;
import org.soen387.ser.game.GameFinder;

public class GameInputMapper {
	
	public static Game find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, Game.class);
		} catch(DomainObjectNotFoundException e) {
			// Just did not find in IdentityMap.
		}
		
		ResultSet rs = GameFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("Game with this id does not exist.");
		} 
		return getGame(rs);
	}
	
	public static List<IGame> findAllByPlayer(IPlayer player) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = GameFinder.findAllByPlayer(player.getId());
		return getGameList(rs);
	}
	
	private static Game getGame(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		Game c = GameFactory.createClean(id, rs.getLong("version"), new PlayerProxy(rs.getLong("challenger")), new PlayerProxy(rs.getLong("challengee")), new DeckProxy(rs.getLong("challengerdeck")), new DeckProxy(rs.getLong("challengeedeck")), new PlayProxy(rs.getLong("challengerplay")), new PlayProxy(rs.getLong("challengeeplay")), rs.getLong("currentplayer"));
		return c;
	}
	
	private static List<IGame> getGameList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IGame> Games = new ArrayList<IGame>();
		while(rs.next()) {
			Games.add(new GameProxy(rs.getLong("id")));
		}
		return Games; 
	}


}
