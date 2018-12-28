package org.soen387.ser.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT u.id, u.version, u.username, u.password FROM " + UserTDG.TABLE + " AS u WHERE u.id=?;";
	
	public static final String SELECT_BY_USERNAME = 
			"SELECT u.id, u.version, u.username, u.password FROM " + UserTDG.TABLE + " AS u WHERE u.username=?;";
	
	public static final String SELECT_BY_USERNAME_AND_PASS = 
			"SELECT u.id, u.version, u.username, u.password FROM " + UserTDG.TABLE + " AS u WHERE u.username=? AND u.password=?;";

	public static final String SELECT_ALL = 
			"SELECT u.id FROM " + UserTDG.TABLE + " AS u;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByUsername(String username) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_USERNAME);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByUsernameAndPass(String username, String password) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_USERNAME_AND_PASS);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findAll() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_ALL);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
