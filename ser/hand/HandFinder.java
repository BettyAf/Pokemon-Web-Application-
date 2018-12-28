package org.soen387.ser.hand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class HandFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT h.id, h.version, h.play FROM " + HandTDG.TABLE + " AS h WHERE h.id=?;";
	
	public static final String SELECT_BY_PLAY = 
			"SELECT h.id, h.version, h.play FROM " + HandTDG.TABLE + " AS h WHERE h.play=?;";

	public static final String SELECT_ALL = 
			"SELECT h.id FROM " + HandTDG.TABLE + " AS h;";
	
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
