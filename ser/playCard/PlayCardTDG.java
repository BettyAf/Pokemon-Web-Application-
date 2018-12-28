package org.soen387.ser.playCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayCardTDG {
	
public static final String TABLE = "cardinplay";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, play, location, card) VALUES(?,?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, play=?, location=?, card=?, energy=?, basic=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, long play, int location, long card) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setLong(3,play);
		ps.setInt(4, location);
		ps.setLong(5, card);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, long play, int location, long card, long energy, long basic) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setLong(1,play);
		ps.setInt(2, location);
		ps.setLong(3, card);
		ps.setLong(4, energy);
		ps.setLong(5, basic);
		ps.setLong(6, id);
		ps.setLong(7, version);
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
