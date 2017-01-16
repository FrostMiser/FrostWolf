package frostWolf;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import org.bukkit.ChatColor;


public class PlayerListener implements Listener {


	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		
		//Players being damaged by wolves in winter biomes have a chance to become a FrostWolf

		//If damager is a wolf and entity being damaged is a player, and player is not already a FrostWolf
		if (event.getDamager() instanceof Wolf && 
			event.getEntity() instanceof Player && 
			!(FrostWolf.wolfList.contains(event.getEntity().getUniqueId()))) {
			
			//Chance of player becoming a FrostWolf
			Random randomGen = new Random();
			int frostWolfChance = randomGen.nextInt(10);
			
			if (frostWolfChance == 5) {
				event.getEntity().sendMessage(ChatColor.AQUA + "[FrostWolf] You feel the power of a FrostWolf flowing through you.");
				//todo add inital effects
				FrostWolf.wolfList.add(event.getEntity().getUniqueId());
			}

		}
		
		
		
		
		//If damager is a FrostWolf, increase the amount of damage
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
		Player p = event.getPlayer();
		
		if (!FrostWolf.wolfList.contains(p.getUniqueId())) { return; } //Exit if player eating is not a FrostWolf
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack itemInHand = p.getInventory().getItemInMainHand();
			if (itemInHand.getType().equals(Material.MUSHROOM_SOUP)) {
				FrostWolf.wolfList.remove(p.getUniqueId());
				p.getInventory().setItemInMainHand(new ItemStack(Material.AIR,1));
				p.updateInventory();
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] You feel your body slowly going back to normal.");
			}
		}
	}	
}
