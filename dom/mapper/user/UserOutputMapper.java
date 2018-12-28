package org.soen387.dom.mapper.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.soen387.dom.model.user.User;
import org.soen387.ser.user.UserTDG;

public class UserOutputMapper extends GenericOutputMapper<Long, User> {

	@Override
	public void insert(User u) throws MapperException {
		try {
			UserTDG.insert(u.getId(), u.getVersion(), u.getUsername(), u.getPassword());
		} catch(SQLException e) {
			throw new MapperException("Could not add User " + u.getId(), e);
		}
	}

	@Override
	public void update(User u) throws MapperException {
		try {
			UserTDG.update(u.getId(), u.getVersion(), u.getUsername(), u.getPassword());
		} catch(SQLException e) {
			throw new MapperException("Could not update User " + u.getId(), e);
		}
	}

	@Override
	public void delete(User u) throws MapperException {
		try {
			UserTDG.delete(u.getId(), u.getVersion());
		} catch(SQLException e) {
			throw new MapperException("Could not delete User " + u.getId(), e);
		}
		
	}

	
}
