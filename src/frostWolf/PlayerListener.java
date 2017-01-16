package frostWolf;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerListener implements Listener {


	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		//If damager is a FrostWolf
		if (!(event.getDamager() instanceof Player)) { return;} //Exit if damager is not a player
		Player damager = (Player) event.getDamager();
		if (!FrostWolf.wolfList.contains(damager.getUniqueId())) { return; } //Exit if damager is not a FrostWolf

		long time = damager.getWorld().getTime();
		if (time > 12000 && time < 23000) {
			//Increase the amount of damage done by 10x
			event.setDamage((event.getDamage() * 10));
		}
	}
	

	@EventHandler
	public void onPlayerEat(PlayerInteractEvent event) {

	}	
}
