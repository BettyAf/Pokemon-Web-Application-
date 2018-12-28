package org.soen387.ser.challenge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class ChallengeFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT c.id, c.version, c.challenger, c.challengee, c.challengerdeck, c.status FROM " + ChallengeTDG.TABLE + " AS c WHERE c.id=?;";
	
	public static final String SELECT_BY_PLAYER = 
			"SELECT c.id, c.version, c.challenger, c.challengee, c.challengerdeck, c.status FROM " + ChallengeTDG.TABLE + " AS c WHERE c.challenger=? OR c.challengee=?;";
	
	public static final String SELECT_BY_CHALLENGER_AND_CHALLENGEE = 
			"SELECT c.id, c.version, c.challenger, c.challengee, c.challengerdeck, c.status FROM " + ChallengeTDG.TABLE + 
			" AS c WHERE (c.challenger=? AND c.challengee=?) OR (c.challenger=? AND c.challengee=?);";
	
	public static ResultSet find(long id) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findAllByPlayer(long player) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_PLAYER);
		ps.setLong(1, player);
		ps.setLong(2, player);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public static ResultSet findByChallengerAndChallengee(long challenger, long challengee) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_CHALLENGER_AND_CHALLENGEE);
		ps.setLong(1, challenger);
		ps.setLong(2, challengee);
		ps.setLong(3, challengee);
		ps.setLong(4, challenger);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
