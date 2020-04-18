package com.stgaming.mc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {
	private Connection db;
	
	public LoginCommand(Connection con) {
		db = con;
	}

	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
        	Player player = (Player) sender;
        	if(args.length == 1) {
        		try {
        			PreparedStatement stmt = db.prepareStatement("SELECT password FROM stg_users WHERE username = '"+player.getName()+"' LIMIT 1");
        			ResultSet results = stmt.executeQuery();
        			if(!results.next()) {
        				new MessagingSystem().SendPlayerMessage(player, "You are not registered in the forums, please register first.");
        			}else {
        				final String hash = results.getString("password");
//						boolean match = BCrypt.checkpw(args[0], hash);
//        				if(match) {
//        					player.isPermissionSet("loggedin");
//        					new MessagingSystem().SendGlobalMessage(player.getName()+" has joined the game. Welcome them to gain reputation.");        					
//        				}
        			}
        			stmt.close();
        		}catch(SQLException e) {
        			e.printStackTrace();
        		}
        		return true;
        	}else {
        		new MessagingSystem().SendPlayerMessage(player, "/login <your-forum-password>");
        		return true;
        	}
		}
		return true;
	}
}