package com.linmalu.randombox.data;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.randombox.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LinmaluInventory
{
	private final LinmaluConfig config = new LinmaluConfig(new File(Main.getMain().getDataFolder(), "inventorys.yml"));
	private final HashMap<String, Inventory> inventorys = new HashMap<>();

	@SuppressWarnings("unchecked")
	public void load()
	{
		synchronized(inventorys)
		{
			inventorys.clear();
			config.getKeys(false).forEach(key ->
			{
				Inventory inv = Bukkit.createInventory(null, 9 * 6, key);
				List<ItemStack> items = ((List<ItemStack>)config.getList(key));
				inv.setContents(items.toArray(new ItemStack[items.size()]));
				if(!isEmptyInventory(inv))
				{
					inventorys.put(key, inv);
				}
			});
		}
	}

	public void save(Inventory inv)
	{
		synchronized(inventorys)
		{
			String name = inv.getTitle().replace(Main.getMain().getTitle(), "");
			if(!isEmptyInventory(inv))
			{
				config.set(name, inv.getContents());
				inventorys.put(name, inv);
			}
			else
			{
				config.remove(name);
				inventorys.remove(name);
			}
		}
	}

	public void clear()
	{
		synchronized(inventorys)
		{
			config.clear();
			inventorys.clear();
		}
	}

	public boolean isEmptyInventory(Inventory inv)
	{
		for(ItemStack item : inv.getContents())
		{
			if(item != null && item.getType() != Material.AIR)
			{
				return false;
			}
		}
		return true;
	}

	public Inventory getInventory(String name)
	{
		synchronized(inventorys)
		{
			return inventorys.containsKey(name) ? inventorys.get(name) : Bukkit.createInventory(null, 9 * 6, Main.getMain().getTitle() + name);
		}
	}

	public boolean hasItem()
	{
		synchronized(inventorys)
		{
			for(Inventory inv : inventorys.values())
			{
				if(!isEmptyInventory(inv))
				{
					return true;
				}
			}
			return false;
		}
	}

	public ItemStack getItem()
	{
		synchronized(inventorys)
		{
			Inventory inv = inventorys.values().toArray(new Inventory[inventorys.size()])[new Random().nextInt(inventorys.size())];
			ArrayList<Integer> items = new ArrayList<>();
			for(int i = 0; i < inv.getSize(); i++)
			{
				ItemStack item = inv.getContents()[i];
				if(item != null && item.getType() != Material.AIR)
				{
					items.add(i);
				}
			}
			int number = items.get(new Random().nextInt(items.size()));
			ItemStack item = inv.getItem(number);
			inv.clear(number);
			save(inv);
			return item;
		}
	}
}
