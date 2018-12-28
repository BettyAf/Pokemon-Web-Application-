package org.soen387.ser.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class CardTDG {
	public static final String TABLE = "card";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, type, name, basic, deck) VALUES(?,?,?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, type=?, name=?, basic=?, deck=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, String type, String name, String basic, long deck) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setString(3, type);
		ps.setString(4, name);
		ps.setString(5, basic);
		ps.setLong(6, deck);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, String type, String name, String basic, long deck) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setString(1, type);
		ps.setString(2, name);
		ps.setString(3, basic);
		ps.setLong(4, deck);
		ps.setLong(5, id);
		ps.setLong(6, version);
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
