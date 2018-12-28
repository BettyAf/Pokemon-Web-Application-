package org.soen387.dom.model.player;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.user.IUser;
import org.soen387.ser.player.PlayerTDG;

public class PlayerFactory {

	public static Player createNew(IUser user) throws SQLException {
		Player p = new Player(PlayerTDG.getMaxId(), 1, user);
		try {
			UoW.getCurrent().registerNew(p);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return p;
	}
	
	public static Player createClean(long id, long version, IUser user, List<IDeck> decks) throws SQLException {
		Player p = new Player(id, version, user, decks);
		UoW.getCurrent().registerClean(p);
		return p;	
	}
}
