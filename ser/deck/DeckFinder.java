package org.soen387.ser.deck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;
import org.soen387.ser.card.CardTDG;

public class DeckFinder {

	public static final String SELECT_BY_ID = 
			"SELECT d.id, d.version, d.player FROM " + DeckTDG.TABLE + " AS d WHERE d.id=?;";
	
	public static final String SELECT_BY_PLAYER = 
			"SELECT d.id, d.version, d.player FROM " + DeckTDG.TABLE + " AS d WHERE d.player=?;";

	public static final String SELECT_ALL = 
			"SELECT d.id FROM " + CardTDG.TABLE + " AS d;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByPlayer(long player) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_PLAYER);
		ps.setLong(1, player);
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
