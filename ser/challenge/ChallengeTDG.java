package org.soen387.ser.challenge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class ChallengeTDG {
	
	public static final String TABLE = "challenge";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, challenger, challengee, challengerDeck, status) VALUES(?,?,?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, challenger=?, challengee=?, challengerDeck=?, status=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";

	public static int insert(long id, long version, long challenger, long challengee, long challengerDeck, int status) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setLong(3, challenger);
		ps.setLong(4, challengee);
		ps.setLong(5, challengerDeck);
		ps.setInt(6, status);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, long challenger, long challengee, long challengerDeck, int status) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setLong(1, challenger);
		ps.setLong(2, challengee);
		ps.setLong(3, challengerDeck);
		ps.setInt(4, status);
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
