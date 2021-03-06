package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluMain;

public class Main extends LinmaluMain
{
	public static Main getInstance()
	{
		return (Main)LinmaluMain.getInstance();
	}

	private RandomBoxManager _randomBoxManager;

	@Override
	public void onEnable()
	{
		super.onEnable();
		getLogger().info("Loading . . . . .");
		_randomBoxManager = new RandomBoxManager();
		getLogger().info("Loading Complete!");
		new Main_Command(this);
		new Main_Event(this);
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		_randomBoxManager.closeInventory();
	}

	public RandomBoxManager getRandomBoxManager()
	{
		return _randomBoxManager;
	}
}
