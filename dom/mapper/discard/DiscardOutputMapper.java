package org.soen387.dom.mapper.discard;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.discard.Discard;
import org.soen387.ser.discard.DiscardTDG;

public class DiscardOutputMapper extends GenericOutputMapper<Long, Discard> {

	@Override
	public void insert(Discard d) throws MapperException {
		try {
			DiscardTDG.insert(d.getId(), d.getVersion(), d.getPlay().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Discard d) throws MapperException {
		try {
			int count = DiscardTDG.update(d.getId(), d.getVersion(), d.getPlay().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Discard with id " + d.getId() + " and version " + d.getVersion() + " could not be found.");
			} else {
				d.setVersion(d.getVersion() + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Discard d) throws MapperException {
		try {
			int count = DiscardTDG.delete(d.getId(), d.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Discard with id " + d.getId() + " and version " + d.getVersion() + " could not be found.");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
