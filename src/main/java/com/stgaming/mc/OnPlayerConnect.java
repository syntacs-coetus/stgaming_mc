package com.stgaming.mc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerConnect implements Listener {
	
	Connection db = null;
	
	OnPlayerConnect(Connection connection){
		db = connection;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = (Player) event.getPlayer();
		
		try {
			PreparedStatement stmt = db.prepareStatement("SELECT uid FROM stg_users WHERE username = '"+player.getName()+"' LIMIT 1");
			ResultSet results = stmt.executeQuery();
			if(!results.next()) {
				new MessagingSystem().SendPlayerMessage(player, "You are not registered in the forums, please register first.");
				player.kickPlayer("");
			}else {
				if(!player.getPlayer().hasPlayedBefore()) {
					new MessagingSystem().SendPlayerMessage(player, "Welcome to STGaming's MC Server "+player.getName());
				}else {
					new MessagingSystem().SendPlayerMessage(player, "Welcome back to STGaming's MC Server "+player.getName());
				}
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}