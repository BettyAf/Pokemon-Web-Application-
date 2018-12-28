package org.soen387.ser.play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayTDG {
	
	public static final String TABLE = "play";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, game, player, deck, hand, discard, bench, status) VALUES(?,?,?,?,?,?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, game=?, player=?, deck=?, hand=?, discard=?, bench=?, status=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, long game, long player, long deck, long hand, long discard, long bench, int status) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setLong(3, game);
		ps.setLong(4, player);
		ps.setLong(5, deck);
		ps.setLong(6, hand);
		ps.setLong(7, discard);
		ps.setLong(8, bench);
		ps.setInt(9, status);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, long game, long player, long deck, long hand, long discard, long bench, int status) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setLong(1, game);
		ps.setLong(2, player);
		ps.setLong(3, deck);
		ps.setLong(4, hand);
		ps.setLong(5, discard);
		ps.setLong(6, bench);
		ps.setInt(7, status);
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
