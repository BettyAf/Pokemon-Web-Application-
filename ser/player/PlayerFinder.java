package org.soen387.ser.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayerFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT p.id, p.version, p.user FROM " + PlayerTDG.TABLE + " AS p WHERE p.id=?;";
	
	public static final String SELECT_BY_USER = 
			"SELECT p.id, p.version, p.user FROM " + PlayerTDG.TABLE + " AS p WHERE p.user=?;";

	public static final String SELECT_ALL = 
			"SELECT p.id FROM " + PlayerTDG.TABLE + " AS p;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByUser(long user) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_USER);
		ps.setLong(1, user);
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
