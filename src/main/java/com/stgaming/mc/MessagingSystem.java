package com.stgaming.mc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessagingSystem {

	String sname = "[STGSC]: ";
	
	public void SendPlayerMessage(Player player, String text) {
		player.sendMessage(sname+text);
	}
	
	public void SendGlobalMessage(String text) {
		Bukkit.broadcastMessage(sname+text);
	}
	
}
