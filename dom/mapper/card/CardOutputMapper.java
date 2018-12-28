package org.soen387.dom.mapper.card;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.card.Card;
import org.soen387.ser.card.CardTDG;

public class CardOutputMapper extends GenericOutputMapper<Long, Card> {

	@Override
	public void insert(Card c) throws MapperException {
		try {
			CardTDG.insert(c.getId(), c.getVersion(), c.getType(), c.getName(), c.getBasic(), c.getDeck().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Card c) throws MapperException {
		try {
			int count = CardTDG.update(c.getId(), c.getVersion(), c.getType(), c.getName(), c.getBasic(), c.getDeck().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Card with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			} else {
				c.setVersion(c.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Card c) throws MapperException {
		try {
			int count = CardTDG.delete(c.getId(), c.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Card with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
