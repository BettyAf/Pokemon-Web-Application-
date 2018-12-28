package org.soen387.dom.mapper.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;
import org.soen387.dom.model.user.IUser;
import org.soen387.dom.model.user.User;
import org.soen387.dom.model.user.UserFactory;
import org.soen387.dom.model.user.UserProxy;
import org.soen387.ser.user.UserFinder;

public class UserInputMapper {

	public static User find(long id) throws SQLException, MapperException, DomainObjectCreationException {
		try {
			return IdentityMap.get(id, User.class);
		} catch(DomainObjectNotFoundException e) {
		} catch(ObjectRemovedException e) {
		}
		
		ResultSet rs = UserFinder.find(id);
		if(!rs.next()) {
			throw new MapperException("User with this id does not exist.");
		} 
		return getUser(rs);
	}
	
	public static User findByUsername(String username) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = UserFinder.findByUsername(username);
		if(!rs.next()) {
			throw new MapperException("User with this username does not exist.");
		} 
		try {
			return IdentityMap.get(rs.getLong("id"), User.class);
		} catch(DomainObjectNotFoundException e) {
		} catch(ObjectRemovedException e) {
		}
		return getUser(rs);
	}
	
	public static User findByUsernameAndPass(String username, String password) throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = UserFinder.findByUsernameAndPass(username, password);
		if(!rs.next()) {
			throw new MapperException("User with this username does not exist.");
		} 
		try {
			return IdentityMap.get(rs.getLong("id"), User.class);
		} catch(DomainObjectNotFoundException e) {
		} catch(ObjectRemovedException e) {
		}
		
		return getUser(rs);
	}
	
	public static List<IUser> findAll() throws SQLException, MapperException, DomainObjectCreationException {
		ResultSet rs = UserFinder.findAll();
		return getUserList(rs);
	}
	
	
	private static User getUser(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		long id = rs.getLong("id");
		User u = UserFactory.createClean(id, rs.getLong("version"), rs.getString("username"), rs.getString("password"));
		return u;
	}
	
	private static List<IUser> getUserList(ResultSet rs) throws SQLException, MapperException, DomainObjectCreationException {
		List<IUser> users = new ArrayList<IUser>();
		while(rs.next()) {
			users.add(new UserProxy(rs.getLong("id")));
		}
		return users; 
	}
}
