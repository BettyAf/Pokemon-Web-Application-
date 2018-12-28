package org.soen387.dom.mapper.bench;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;
import org.soen387.dom.model.bench.Bench;
import org.soen387.ser.bench.BenchTDG;

public class BenchOutputMapper extends GenericOutputMapper<Long, Bench> {

	@Override
	public void insert(Bench b) throws MapperException {
		try {
			BenchTDG.insert(b.getId(), b.getVersion(), b.getPlay().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Bench b) throws MapperException {
		try {
			int count = BenchTDG.update(b.getId(), b.getVersion(), b.getPlay().getId());
			if(count == 0) {
				throw new LostUpdateException("Could not update: Bench with id " + b.getId() + " and version " + b.getVersion() + " could not be found.");
			} else {
				b.setVersion(b.getVersion() + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Bench b) throws MapperException {
		try {
			int count = BenchTDG.delete(b.getId(), b.getVersion());
			if(count == 0) {
				throw new LostUpdateException("Could not delete: Bench with id " + b.getId() + " and version " + b.getVersion() + " could not be found.");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
