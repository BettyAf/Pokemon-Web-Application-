package org.soen387.ser.discard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class DiscardFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT d.id, d.version, d.play FROM " + DiscardTDG.TABLE + " AS d WHERE d.id=?;";
	
	public static final String SELECT_BY_PLAY = 
			"SELECT d.id, d.version, d.play FROM " + DiscardTDG.TABLE + " AS d WHERE d.play=?;";

	public static final String SELECT_ALL = 
			"SELECT d.id FROM " + DiscardTDG.TABLE + " AS d;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByPlay(long play) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_PLAY);
		ps.setLong(1, play);
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
