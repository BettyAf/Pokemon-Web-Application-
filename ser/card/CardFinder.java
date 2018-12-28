package org.soen387.ser.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class CardFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT c.id, c.version, c.type, c.name, c.basic, c.deck FROM " + CardTDG.TABLE + " AS c WHERE c.id=?;";
	
	public static final String SELECT_BY_DECK = 
			"SELECT c.id, c.version, c.type, c.name, c.basic, c.deck FROM " + CardTDG.TABLE + " AS c WHERE c.deck=?;";
	
	public static final String SELECT_ALL = 
			"SELECT c.id FROM " + CardTDG.TABLE + " AS c;";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByDeck(long deck) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_DECK);
		ps.setLong(1, deck);
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
