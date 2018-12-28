package org.soen387.dom.mapper.deck;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.card.ICard;
import org.soen387.dom.model.deck.Deck;
import org.soen387.ser.card.CardTDG;
import org.soen387.ser.deck.DeckTDG;

public class DeckOutputMapper extends GenericOutputMapper<Long, Deck>{
	
	@Override
	public void insert(Deck d) throws MapperException {
		try {
			DeckTDG.insert(d.getId(), d.getVersion(), d.getPlayer().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Deck d) throws MapperException {
		try {
			int count = DeckTDG.update(d.getId(), d.getVersion(), d.getPlayer().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Deck with id " + d.getId() + " and version " + d.getVersion() + " could not be found.");
			} else {
				d.setVersion(d.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Deck d) throws MapperException {
		try {
			int count = DeckTDG.delete(d.getId(), d.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Deck with id " + d.getId() + " and version " + d.getVersion() + " could not be found.");
			} else {
				for(ICard card : d.getCards()) {
					CardTDG.delete(card.getId(), card.getVersion());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
