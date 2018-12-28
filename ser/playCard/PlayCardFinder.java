package org.soen387.ser.playCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayCardFinder {

	public static final String SELECT_BY_ID = 
			"SELECT c.id, c.version, c.play, c.location, c.card, c.energy, c.basic FROM " + PlayCardTDG.TABLE + " AS c WHERE c.id=?;";
	
	public static final String SELECT_BY_PLAY_AND_LOCATION = 
			"SELECT c.id, c.version, c.play, c.location, c.card, c.energy, c.basic FROM " + PlayCardTDG.TABLE + " AS c WHERE c.play=? AND c.location=?;";
	
	public static final String SELECT_ALL = 
			"SELECT c.id FROM " + PlayCardTDG.TABLE + " AS c;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByPlayAndLocation(long play, int location) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_PLAY_AND_LOCATION);
		ps.setLong(1, play);
		ps.setInt(2, location);
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
