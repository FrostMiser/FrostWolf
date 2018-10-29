package frostWolf;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ToggleWolf implements CommandExecutor  {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;
	
		//Make sure sender is a player and not the console
		if (sender.equals(Bukkit.getConsoleSender())) { 
			p = null;
			sender.sendMessage(ChatColor.AQUA + "[FrostWolf] This command may not be run from the console.");
			return false;
		}
		else { p = (Player) sender; }
		
		//toggleWolf command
		if (cmd.getName().equalsIgnoreCase("togglewolf")) {			
			//Check to see if a player was specified in the command
			if (args.length == 0) {
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] Invalid use of command, use /toggleWolf <player>");
				return true;
			}
			
			//Get the player which togglewolf will be used on
			Player toggleWolfPlayer;
			try {
				toggleWolfPlayer = Bukkit.getServer().getPlayer(args[0]);
			}
			catch (Exception e) {
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] Could not find a player online by that name.");
				return true;
			}
			
			//If the target player is a FrostWolf, change them back to normal
			if (FrostWolf.wolfList.contains(toggleWolfPlayer.getUniqueId())) {
				FrostWolf.wolfList.remove(toggleWolfPlayer.getUniqueId());
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] " + toggleWolfPlayer.getName() + " is no longer a FrostWolf.");				
			}
			else {
				FrostWolf.wolfList.add(toggleWolfPlayer.getUniqueId());
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] " + toggleWolfPlayer.getName() + " is now a FrostWolf.");
			}
		}
		return true;
	}
}
