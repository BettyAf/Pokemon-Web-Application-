package org.soen387.dom.mapper.player;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.deck.IDeck;
import org.soen387.dom.model.player.Player;
import org.soen387.ser.deck.DeckTDG;
import org.soen387.ser.player.PlayerTDG;

public class PlayerOutputMapper extends GenericOutputMapper<Long, Player> {

	@Override
	public void insert(Player p) throws MapperException {
		try {
			PlayerTDG.insert(p.getId(), p.getVersion(), p.getUser().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Player p) throws MapperException {
		try {
			int count = PlayerTDG.update(p.getId(), p.getVersion(), p.getUser().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Player with id " + p.getId() + " and version " + p.getVersion() + " could not be found.");
			} else {
				p.setVersion(p.getVersion() + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Player p) throws MapperException {
		try {
			int count = PlayerTDG.delete(p.getId(), p.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Player with id " + p.getId() + " and version " + p.getVersion() + " could not be found.");
			} else {
				for(IDeck deck : p.getDecks()) {
					DeckTDG.delete(deck.getId(), deck.getVersion());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
