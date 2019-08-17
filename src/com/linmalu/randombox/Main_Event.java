package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluEvent;
import com.linmalu.library.api.LinmaluMain;
import com.linmalu.library.api.LinmaluServer;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Main_Event extends LinmaluEvent
{
	private final RandomBoxManager _randomBoxManager = Main.getInstance().getRandomBoxManager();

	public Main_Event(LinmaluMain main)
	{
		super(main);
	}

	@EventHandler
	public void event(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.isOp())
		{
			LinmaluServer.version(Main.getInstance(), player);
		}
	}

	@EventHandler
	public void event(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.TRAPPED_CHEST)
		{
			Player player = event.getPlayer();
			if(_randomBoxManager.useRandomBox(player))
			{
				event.getClickedBlock().setType(Material.AIR);
				event.getClickedBlock().getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, Material.TRAPPED_CHEST);
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void event(InventoryCloseEvent event)
	{
		if(event.getInventory().getHolder() instanceof RandomBoxHolder)
		{
			_randomBoxManager.save(((RandomBoxHolder)event.getInventory().getHolder()).getTitle());
		}
	}
}
