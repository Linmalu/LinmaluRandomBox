package com.linmalu.randombox;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.library.api.LinmaluVersion;
import com.linmalu.randombox.data.LinmaluInventory;

public class Main_Event implements Listener
{
	private LinmaluInventory inventory = Main.getMain().getLinmaluInventory();

	@EventHandler
	public void Event(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.isOp())
		{
			LinmaluVersion.check(Main.getMain(), player);
		}
	}
	@EventHandler
	public void Event(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.TRAPPED_CHEST)
		{
			Player player = event.getPlayer();
			if(inventory.hasItem())
			{
				event.getClickedBlock().setType(Material.AIR);
				ItemStack item = inventory.getItem();
				player.getInventory().addItem(item).values().forEach(Item ->
				{
					player.getWorld().dropItem(player.getLocation(), Item);
				});
				LinmaluTellraw.sendItem(player, item, Main.getMain().getTitle() + ChatColor.GREEN + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString()) + ChatColor.GOLD + "(" + item.getAmount() + ")" + ChatColor.GRAY + " 아이템을 획득했습니다.");
				player.getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, Material.TRAPPED_CHEST);
				player.getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			}
			else
			{
				player.sendMessage(Main.getMain().getTitle() + ChatColor.YELLOW + "아이템이 없습니다.");
				player.getWorld().playSound(event.getClickedBlock().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			}
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void Event(InventoryCloseEvent event)
	{
		if(event.getInventory().getTitle().startsWith(Main.getMain().getTitle()))
		{
			inventory.save(event.getInventory());
		}
	}
}
