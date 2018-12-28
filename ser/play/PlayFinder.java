package org.soen387.ser.play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class PlayFinder {
	
	public static final String SELECT_BY_ID = 
			"SELECT p.id, p.version, p.game, p.player, p.deck, p.hand, p.discard, p.bench, p.status FROM " + PlayTDG.TABLE + " AS p WHERE p.id=?;";
	
	public static final String SELECT_BY_PLAYER = 
			"SELECT p.id, p.version, p.game, p.player, p.deck, p.hand, p.discard, p.bench, p.status FROM " + PlayTDG.TABLE + " AS p WHERE p.player=?;";
	
	public static final String SELECT_BY_PLAYER_AND_GAME = 
			"SELECT p.id, p.version, p.game, p.player, p.deck, p.hand, p.discard, p.bench, p.status FROM " + PlayTDG.TABLE + " AS p WHERE p.player=? AND p.game=?;";

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
	
	public static ResultSet findByPlayerAndGame(long player, long game) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(SELECT_BY_PLAYER_AND_GAME);
		ps.setLong(1, player);
		ps.setLong(2, game);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}
