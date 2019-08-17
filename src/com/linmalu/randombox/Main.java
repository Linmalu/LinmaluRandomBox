package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluMain;

public class Main extends LinmaluMain
{
	public static Main getInstance()
	{
		return (Main)LinmaluMain.getInstance();
	}

	private RandomBoxManager _randomBoxManager;

	public void onEnable()
	{
		super.onEnable();
		getLogger().info("Loading . . . . .");
		_randomBoxManager = new RandomBoxManager();
		getLogger().info("Loading Complete!");
		new Main_Command(this);
		new Main_Event(this);
	}

	public RandomBoxManager getRandomBoxManager()
	{
		return _randomBoxManager;
	}
}
