package com.stgaming.mc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

public class ConnectDB {
	
	Connection connection = null;
	
	private Initialize init;
	
	public ConnectDB(FileConfiguration config) {
		final String host = config.getString("hostname");
		final Integer port = config.getInt("port");
		final String dbname = config.getString("forum_database");
		final String user = config.getString("username");
		final String pass = config.getString("password");
		final String url = "jdbc:mysql://"+host+":"+port+"/"+dbname;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			init.ConsoleLog("JDBC driver unavailable.");
		}
		try {
			connection = DriverManager.getConnection(url, user, pass);
		} catch(SQLException e) {
			e.printStackTrace();
			init.ConsoleLog("There was a problem creating a connection!");
		}
	}

}
