package org.soen387.dom.mapper.hand;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.hand.Hand;
import org.soen387.ser.hand.HandTDG;

public class HandOutputMapper extends GenericOutputMapper<Long, Hand> {

	@Override
	public void insert(Hand h) throws MapperException {
		try {
			HandTDG.insert(h.getId(), h.getVersion(), h.getPlay().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Hand h) throws MapperException {
		try {
			int count = HandTDG.update(h.getId(), h.getVersion(), h.getPlay().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Hand with id " + h.getId() + " and version " + h.getVersion() + " could not be found.");
			} else {
				h.setVersion(h.getVersion() + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Hand h) throws MapperException {
		try {
			int count = HandTDG.delete(h.getId(), h.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Hand with id " + h.getId() + " and version " + h.getVersion() + " could not be found.");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
