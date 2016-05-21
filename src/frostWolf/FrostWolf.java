package frostWolf;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.ChatColor;


public class FrostWolf extends JavaPlugin {

	
	public void onEnable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[FrostWolf] Plugin enabled.");

		//Placeholder for wolf checking
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { public void run() {
		}}, 24000L,7000L);
		
		return;
			
	}

	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[FrostWolf] Plugin disabled.");
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p;
		try {
			p = this.getServer().getPlayer(sender.getName());	
		}
		catch (Exception e) {
			return true;
		}
		return true;
	}


}
