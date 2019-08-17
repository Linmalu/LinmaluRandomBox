package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluMain;

public class Main extends LinmaluMain
{
	public static Main getInstance()
	{
		return (Main)LinmaluMain.getInstance();
	}

	private RandomboxManager _randomboxManager;

	public void onEnable()
	{
		super.onEnable();
		getLogger().info("Loading . . . . .");
		_randomboxManager = new RandomboxManager();
		getLogger().info("Loading Complete!");
		registerCommand(new Main_Command());
		registerEvents(new Main_Event());
	}

	public RandomboxManager getRandomboxManager()
	{
		return _randomboxManager;
	}
}
