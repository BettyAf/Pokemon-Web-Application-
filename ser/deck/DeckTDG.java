package org.soen387.ser.deck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class DeckTDG {
	
public static final String TABLE = "deck";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, player) VALUES(?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, player=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, long player) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setLong(3, player);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, long player) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setLong(1, player);
		ps.setLong(2, id);
		ps.setLong(3, version);
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
