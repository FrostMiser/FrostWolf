package frostWolf;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FrostWolf extends JavaPlugin {
	public static List<UUID> wolfList = new ArrayList<UUID>(); //List of wolves, this holds the list of players who are wolves 

	public void onEnable() {

		
		//Send a message when the plugin being enabled
		this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[FrostWolf] Plugin is being enabled...");
		
		//Register player listener
		getServer().getPluginManager().registerEvents(new PlayerListener(),this);
		
		//Register togglewolf command to togglewolf class
		getCommand("togglewolf").setExecutor(new ToggleWolf(this));
		
		//Repeating task to check for wolves
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { public void run() {
			//For each wolf
			for (UUID wolfUUID: wolfList) {
				Player p = getServer().getPlayer(wolfUUID);
				//Check if its night time in the players world
				long time = p.getWorld().getTime();
				if (time > 12000 && time < 23000) {
					//Update frostWolf effects
					p.removePotionEffect(PotionEffectType.NIGHT_VISION);
					p.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.HUNGER);
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					p.removePotionEffect(PotionEffectType.JUMP);
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1200,0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1200,2));
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,1200,0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,1200,1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,1200,2));
				}
			}
		}}, 100L,100L);

		
		//Send a message when the plugin is enabled
		this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[FrostWolf] Plugin enabled.");
		return;
	}

	
	//Send a message when the plugin is disabled
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[FrostWolf] Plugin disabled.");
	}
}
