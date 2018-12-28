package org.soen387.dom.mapper.cardInPlay;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.cardInPlay.CardInPlay;
import org.soen387.ser.playCard.PlayCardTDG;

public class CardInPlayOutputMapper extends GenericOutputMapper<Long, CardInPlay> {
	
	@Override
	public void insert(CardInPlay c) throws MapperException {
		try {
			PlayCardTDG.insert(c.getId(), c.getVersion(), c.getPlay().getId(), c.getLocation(), c.getCard().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(CardInPlay c) throws MapperException {
		try {
			int count = PlayCardTDG.update(c.getId(), c.getVersion(), c.getPlay().getId(), c.getLocation(), c.getCard().getId(), c.getEnergy().getId(), c.getBasic().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Card in play with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			} else {
				c.setVersion(c.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(CardInPlay c) throws MapperException {
		try {
			int count = PlayCardTDG.delete(c.getId(), c.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Card in play with id " + c.getId() + " and version " + c.getVersion() + " could not be found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
