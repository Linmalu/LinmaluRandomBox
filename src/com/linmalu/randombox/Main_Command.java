package com.linmalu.randombox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.linmalu.library.api.LinmaluTellraw;
import com.linmalu.library.api.LinmaluVersion;
import com.linmalu.randombox.data.LinmaluInventory;

public class Main_Command implements CommandExecutor
{
	private LinmaluInventory inventory = Main.getMain().getLinmaluInventory();

	public Main_Command()
	{
		Main.getMain().getCommand(Main.getMain().getDescription().getName()).setTabCompleter(new TabCompleter()
		{
			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
			{
				ArrayList<String> list = new ArrayList<>();
				if(args.length == 1)
				{
					list.add("초기화");
					list.add("clear");
				}
				return list.stream().filter(msg -> msg.startsWith(args[args.length - 1])).count() == 0 ? list : list.stream().filter(msg -> msg.startsWith(args[args.length - 1])).collect(Collectors.toList());
			}
		});
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String args[])
	{
		if(sender instanceof Player && sender.isOp())
		{
			Player player = (Player)sender;
			if(args.length == 1)
			{
				if(args[0].equals("초기화") || args[0].equalsIgnoreCase("clear"))
				{
					inventory.clear();
					sender.sendMessage(Main.getMain().getTitle() + ChatColor.GREEN + "인벤토리가 초기화되었습니다.");
					return true;
				}
				else
				{
					player.openInventory(inventory.getInventory(args[0]));
					return true;
				}
			}
			sender.sendMessage(ChatColor.GREEN + " = = = = = [ Linmalu Random Box ] = = = = =");
			LinmaluTellraw.sendChat(sender, "/" + label + " ", ChatColor.GOLD + "/" + label + " <이름>" + ChatColor.GRAY + " : 인벤토리 열기");
			LinmaluTellraw.sendChat(sender, "/" + label + " clear ", ChatColor.GOLD + "/" + label + " 초기화/clear" + ChatColor.GRAY + " : 인벤토리 초기화");
			sender.sendMessage(ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
			if(sender.isOp())
			{
				LinmaluVersion.check(Main.getMain(), sender);
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
		}
		return true;
	}
}
