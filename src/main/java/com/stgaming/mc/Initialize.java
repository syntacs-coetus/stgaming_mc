package com.stgaming.mc;
import java.sql.Connection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mindrot.jbcrypt.*;


public class Initialize extends JavaPlugin {
	String sname = "[STGSC]: ";
	Connection db = null;
	@Override
	public void onEnable() {
		
		this.saveDefaultConfig();
		
		ConsoleLog("Server is ready!!!");
		
		hashTester("TestHash");
		
		FileConfiguration config = this.getConfig();
		
		if(config.getString("hostname") != "") {
			
			ConsoleLog("Making Secure Connection to Forums");
			
			db = new ConnectDB(config).connection;
			
			if(db != null) {
				ConsoleLog("Connection Success!");
				this.getCommand("login").setExecutor(new LoginCommand(db));
				getServer().getPluginManager().registerEvents(new OnPlayerConnect(db), this);
			}else {
				ConsoleLog("Connection Failed!");
			}
			
		}else {
			ConsoleLog("Configuration file is empty please modify it before running the server again.");
		}
	}
	
	@Override
	public void onDisable() {
		ConsoleLog("Shutting Down!!!");
	    // invoke on disable.
	    try { //using a try catch to catch connection errors (like wrong sql password...)
	        if (db!=null && !db.isClosed()){ //checking if connection isn't null to
	            //avoid receiving a nullpointer
	            db.close(); //closing the connection field variable.
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void ConsoleLog(String text) {
		getLogger().info(sname+text);
	}
	
	public void hashTester(String text) {
		String hashed = BCrypt.hashpw(text, BCrypt.gensalt(12));
		
		getLogger().info(hashed);
		
		boolean verified = BCrypt.checkpw(text, hashed);
		
		getLogger().info("Hashed? "+verified);
	}
}
