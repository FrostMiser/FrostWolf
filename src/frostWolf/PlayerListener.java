package frostWolf;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import org.bukkit.ChatColor;



public class PlayerListener implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		//Players being damaged by wolves in winter biomes have a chance to become a FrostWolf
		
		//If damager is a wolf and entity being damaged is a player, and player is not already a FrostWolf
		if (event.getDamager() instanceof Wolf &&  event.getEntity() instanceof Player && 
			!(FrostWolf.wolfList.contains(event.getEntity().getUniqueId()))) {		
			Biome biome = event.getEntity().getLocation().getBlock().getBiome();
			
			//Only occurs in a winter biome
			if (biome.equals(Biome.TAIGA) || 
				biome.equals(Biome.TAIGA_HILLS) ||
				biome.equals(Biome.TAIGA_MOUNTAINS) ||
				biome.equals(Biome.SNOWY_TAIGA) ||
				biome.equals(Biome.SNOWY_TAIGA_HILLS) ||
				biome.equals(Biome.SNOWY_MOUNTAINS) ||
				biome.equals(Biome.FROZEN_RIVER) ||
				biome.equals(Biome.FROZEN_OCEAN) ||
				biome.equals(Biome.DEEP_FROZEN_OCEAN) ||
				biome.equals(Biome.SNOWY_BEACH) ||
				biome.equals(Biome.ICE_SPIKES) ||
				biome.equals(Biome.SNOWY_TUNDRA)) 
			{
								
				//Chance of a player becoming a FrostWolf
				Random randomGen = new Random();
				int frostWolfChance = randomGen.nextInt(10);
				
				if (frostWolfChance == 5) {
					Player p = (Player) event.getEntity();
					p.sendMessage(ChatColor.AQUA + "[FrostWolf] You feel the power of a FrostWolf flowing through you.");
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1200,0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1200,2));
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,1200,0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,1200,1));
					FrostWolf.wolfList.add(event.getEntity().getUniqueId());
				}
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
			if (itemInHand.getType().equals(Material.MUSHROOM_STEW)) {
				FrostWolf.wolfList.remove(p.getUniqueId());
				p.getInventory().setItemInMainHand(new ItemStack(Material.AIR,1));
				p.updateInventory();
				p.sendMessage(ChatColor.AQUA + "[FrostWolf] You feel your body slowly going back to normal.");
			}
		}
	}	
}
