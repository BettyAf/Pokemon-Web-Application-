package org.soen387.ser.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class GameFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT g.id, g.version, g.challenger, g.challengee, g.challengerdeck, g.challengeedeck, g.challengerplay, g.challengeeplay, g.currentplayer FROM " + GameTDG.TABLE + " AS g WHERE g.id=?;";
	
	public static final String SELECT_BY_PLAYER = 
			"SELECT g.id, g.version, g.challenger, g.challengee, g.challengerdeck, g.challengeedeck, g.challengerplay, g.challengeeplay, g.currentplayer FROM " + GameTDG.TABLE + " AS g WHERE g.challenger=? OR g.challengee=?;";
	
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

}
