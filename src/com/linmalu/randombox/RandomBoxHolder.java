package com.linmalu.randombox;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RandomBoxHolder implements InventoryHolder
{
	private final Inventory _inventory;
	private final String _title;

	public RandomBoxHolder(int size, String title)
	{
		_inventory = Bukkit.createInventory(this, size, Main.getInstance().getTitle() + title);
		_title = title;
	}

	@Override
	public Inventory getInventory()
	{
		return _inventory;
	}

	public String getTitle()
	{
		return _title;
	}
}
