package org.soen387.ser.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GameTDG {
	
	public static final String TABLE = "game";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, challenger, challengee, challengerdeck, challengeedeck, challengerplay, challengeeplay, currentPlayer) VALUES(?,?,?,?,?,?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, challenger=?, challengee=?, challengerdeck=?, challengeedeck=?, challengerplay=?, challengeeplay=?, currentplayer=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, long challenger, long challengee, long challengerDeck, long challengeeDeck, long challengerPlay, long challengeePlay, long currentPlayer) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setLong(3, challenger);
		ps.setLong(4, challengee);
		ps.setLong(5, challengerDeck);
		ps.setLong(6, challengeeDeck);
		ps.setLong(7, challengerPlay);
		ps.setLong(8, challengeePlay);
		ps.setLong(9, currentPlayer);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, long challenger, long challengee, long challengerDeck, long challengeeDeck, long challengerPlay, long challengeePlay, long currentPlayer) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setLong(1, challenger);
		ps.setLong(2, challengee);
		ps.setLong(3, challengerDeck);
		ps.setLong(4, challengeeDeck);
		ps.setLong(5, challengerPlay);
		ps.setLong(6, challengeePlay);
		ps.setLong(7, currentPlayer);
		ps.setLong(8, id);
		ps.setLong(9, version);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int delete(long id, long version) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(DELETE);
		ps.setLong(1, id);
		ps.setLong(2, version);
		int count = ps.executeUpdate();
		return count;
	}

	public static long getMaxId() throws SQLException {
		return UniqueIdFactory.getMaxId(TABLE, "id");
	}

}
