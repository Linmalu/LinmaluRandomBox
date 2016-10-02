package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluMain;
import com.linmalu.randombox.data.LinmaluInventory;

public class Main extends LinmaluMain
{
	public static Main getMain()
	{
		return (Main)LinmaluMain.getMain();
	}

	private LinmaluInventory inventory;

	public void onEnable()
	{
		super.onEnable();
		inventory = new LinmaluInventory();
		registerCommand(new Main_Command());
		registerEvents(new Main_Event());
	}
	public LinmaluInventory getLinmaluInventory()
	{
		return inventory;
	}
}
