package org.soen387.dom.model.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;
import org.soen387.ser.user.UserTDG;

public class UserFactory {
	
	public static User createNew(String username, String password) throws SQLException {
		User u = new User(UserTDG.getMaxId(), 1, username, password);
		try {
			UoW.getCurrent().registerNew(u);
		} catch (MissingMappingException | MapperException e) {
			e.printStackTrace();
		} 
		return u;
	}
	
	public static User createClean(long id, long version, String username, String password) throws SQLException {
		User u = new User(id, version, username, password);
		UoW.getCurrent().registerClean(u);
		return u;	
	}
	
}
