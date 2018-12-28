package org.soen387.ser.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.dsrg.soenea.service.UniqueIdFactory;
import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class UserTDG {
	
	public static final String TABLE = "user";
	
	public static final String INSERT = 
			"INSERT INTO " + TABLE + " (id, version, username, password) VALUES(?,?,?,?);";
	
	public static final String UPDATE = 
			"UPDATE " + TABLE + " SET version=version+1, username=?, password=? WHERE id=? AND version=?;";
	
	public static final String DELETE = 
			"DELETE FROM " + TABLE + " WHERE id=? AND version=?;";
	final static String[] CREATE_TABLES = {
			"CREATE TABLE IF NOT EXISTS user (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	username VARCHAR(80)," + 
			"	password VARCHAR(80)," + 
			"	UNIQUE (username)," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS player (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	user BIGINT NOT NULL," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS challenge (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	challenger BIGINT," + 
			"	challengee BIGINT," + 
			"	challengerdeck BIGINT," + 
			"	status INT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS card (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	type VARCHAR(1)," + 
			"	name VARCHAR(80)," + 
			"	basic VARCHAR(80)," + 
			"	deck BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS deck (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	player BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS game (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	challenger BIGINT," + 
			"	challengee BIGINT," + 
			"	challengerdeck BIGINT," + 
			"	challengeedeck BIGINT," + 
			"	challengerplay BIGINT," + 
			"	challengeeplay BIGINT," + 
			"	currentplayer BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS play (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	game BIGINT," + 
			"	player BIGINT," + 
			"	deck BIGINT," + 
			"	hand BIGINT," + 
			"	discard BIGINT," + 
			"	bench BIGINT," + 
			"	status INT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS hand (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	play BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");", 
			"CREATE TABLE IF NOT EXISTS discard (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	play BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");",
			"CREATE TABLE IF NOT EXISTS bench (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	play BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");",
			"CREATE TABLE IF NOT EXISTS cardinplay (" + 
			"	id BIGINT NOT NULL AUTO_INCREMENT," + 
			"	version BIGINT," + 
			"	play BIGINT," + 
			"	location int," + 
			"	card BIGINT, " + 
			"	energy BIGINT," + 
			"	basic BIGINT," + 
			"	PRIMARY KEY (id)" + 
			");"
	};
	final static String[] DROP_TABLES = {
			"DROP TABLE player;", 
			"DROP TABLE user;", 
			"DROP TABLE game;", 
			"DROP TABLE challenge;", 
			"DROP TABLE deck;", 
			"DROP TABLE card;",
			"DROP TABLE play;",
			"DROP TABLE hand;",
			"DROP TABLE discard;",
			"DROP TABLE bench;",
			"DROP TABLE cardinplay;"                               
		};
	
	public static void createTable() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		Statement update = con.createStatement();
		for (String sql : CREATE_TABLES) {
			update.executeUpdate(sql);
		}		
	}
	
	public static void dropTable() throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		Statement update = con.createStatement();
				
		for (String sql : DROP_TABLES) {
			try {
				update.executeUpdate(sql);
		
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public static int insert(long id, long version, String username, String password) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(INSERT);
		ps.setLong(1, id);
		ps.setLong(2, version);
		ps.setString(3, username);
		ps.setString(4, password);
		int count = ps.executeUpdate();
		return count;
	}
	
	public static int update(long id, long version, String username, String password) throws SQLException {
		Connection con = DbRegistry.getDbConnection();
		PreparedStatement ps = con.prepareStatement(UPDATE);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setLong(3, id);
		ps.setLong(4, version);
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