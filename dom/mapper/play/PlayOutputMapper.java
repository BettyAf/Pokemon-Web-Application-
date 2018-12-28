package org.soen387.dom.mapper.play;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.play.Play;
import org.soen387.ser.play.PlayTDG;

public class PlayOutputMapper extends GenericOutputMapper<Long, Play>{

	@Override
	public void insert(Play p) throws MapperException {
		try {
			PlayTDG.insert(p.getId(), p.getVersion(), p.getGame().getId(), p.getPlayer().getId(), p.getDeck().getId(), p.getHand().getId(), p.getDiscard().getId(), p.getBench().getId(), p.getStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Play p) throws MapperException {
		try {
			int count = PlayTDG.update(p.getId(), p.getVersion(), p.getGame().getId(), p.getPlayer().getId(), p.getDeck().getId(), p.getHand().getId(), p.getDiscard().getId(), p.getBench().getId(), p.getStatus());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Play with id " + p.getId() + " and version " + p.getVersion() + " could not be found.");
			} else {
				p.setVersion(p.getVersion() + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Play p) throws MapperException {
		try {
			int count = PlayTDG.delete(p.getId(), p.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Play with id " + p.getId() + " and version " + p.getVersion() + " could not be found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
