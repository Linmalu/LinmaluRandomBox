package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluConfig;
import com.linmalu.library.api.LinmaluPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class RandomBoxManager
{
	private final LinmaluConfig _config = new LinmaluConfig(new File(Main.getInstance().getDataFolder(), "config.yml"), false);
	private final Map<String, Inventory> _randomBoxMap = new HashMap<>();

	public RandomBoxManager()
	{
		reload();
	}

	public void reload()
	{
		_randomBoxMap.clear();
		closeInventory();
		_config.reload();
		for(String key : _config.getKeys(false))
		{
			List<ItemStack> list = _config.getListData(key);
			if(list == null)
			{
				continue;
			}
			ItemStack[] items = list.toArray(new ItemStack[0]);
			if(isEmptyItems(items))
			{
				continue;
			}
			Inventory inv = getRandomBox(key);
			inv.setContents(items);
			_randomBoxMap.put(key, inv);
		}
		_config.clear();
		for(Map.Entry<String, Inventory> entry : _randomBoxMap.entrySet())
		{
			_config.set(entry.getKey(), entry.getValue().getContents());
		}
		_config.save();
	}

	public void save(String name)
	{
		if(!_randomBoxMap.containsKey(name))
		{
			return;
		}
		Inventory inv = _randomBoxMap.get(name);
		if(isEmptyItems(inv.getContents()))
		{
			_config.remove(name);
			_randomBoxMap.remove(name);
		}
		else
		{
			_config.set(name, inv.getContents());
			_randomBoxMap.put(name, inv);
		}
		_config.save();
	}

	public Set<String> getNames()
	{
		return _randomBoxMap.keySet();
	}

	public Inventory getRandomBox(String name)
	{
		name = name.replace(".", "");
		if(!_randomBoxMap.containsKey(name))
		{
			_randomBoxMap.put(name, new RandomBoxHolder(9 * 6, name).getInventory());
		}
		return _randomBoxMap.get(name);
	}

	public void clear()
	{
		closeInventory();
		for(Inventory inv : _randomBoxMap.values())
		{
			inv.clear();
		}
		_randomBoxMap.clear();
		_config.clear();
		_config.save();
	}

	public boolean useRandomBox(Player player)
	{
		ItemStack item = getRandomItemStack();
		if(item.getType() == Material.AIR)
		{
			player.sendMessage(Main.getInstance().getTitle() + ChatColor.YELLOW + "아이템이 없습니다.");
			return false;
		}
		else
		{
			player.getInventory().addItem(item).values().forEach(i -> player.getWorld().dropItem(player.getLocation(), i));
			player.sendMessage(Main.getInstance().getTitle() + ChatColor.GREEN + (item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString()) + ChatColor.GOLD + "(" + item.getAmount() + ")" + ChatColor.GRAY + " 아이템을 획득했습니다.");
			return true;
		}
	}

	public ItemStack getRandomItemStack()
	{
		Random ran = new Random();
		List<String> names = new ArrayList<>(_randomBoxMap.keySet());
		Collections.shuffle(names);
		for(String name : names)
		{
			Inventory inv = _randomBoxMap.get(name);
			if(isEmptyItems(inv.getContents()))
			{
				continue;
			}
			List<Integer> indexList = new ArrayList<>();
			for(int i = 0; i < inv.getSize(); i++)
			{
				ItemStack item = inv.getItem(i);
				if(item != null && item.getType() != Material.AIR)
				{
					indexList.add(i);
				}
			}
			int num = indexList.get(ran.nextInt(indexList.size()));
			ItemStack item = inv.getItem(num);
			inv.clear(num);
			save(name);
			return item;
		}
		return new ItemStack(Material.AIR);
	}

	public void closeInventory()
	{
		for(Player player : LinmaluPlayer.getOnlinePlayers())
		{
			if(player.getOpenInventory().getTopInventory().getHolder() instanceof RandomBoxHolder)
			{
				player.closeInventory();
			}
		}
	}

	private boolean isEmptyItems(ItemStack[] items)
	{
		for(ItemStack item : items)
		{
			if(item != null && item.getType() != Material.AIR)
			{
				return false;
			}
		}
		return true;
	}
}
