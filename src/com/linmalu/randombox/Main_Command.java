package com.linmalu.randombox;

import com.linmalu.library.api.LinmaluCommand;
import com.linmalu.library.api.LinmaluMain;
import com.linmalu.library.api.LinmaluTellraw;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Main_Command extends LinmaluCommand
{
	private final RandomBoxManager _randomBoxManager = Main.getInstance().getRandomBoxManager();

	public Main_Command(LinmaluMain main)
	{
		super(main);
	}

	@Override
	protected List<String> TabCompleter(CommandSender sender, Command command, String alias, String[] args)
	{
		List<String> list = new ArrayList<>();
		if(args.length == 1)
		{
			list.add("use");
			list.add("clear");
			list.add("reload");
			list.addAll(_randomBoxManager.getNames());
		}
		return list;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(!(sender instanceof Player && sender.isOp()))
		{
			sender.sendMessage(ChatColor.RED + "권한이 없습니다.");
			return true;
		}
		Player player = (Player)sender;
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("use"))
			{
				_randomBoxManager.useRandomBox(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("clear"))
			{
				_randomBoxManager.clear();
				sender.sendMessage(Main.getInstance().getTitle() + ChatColor.GREEN + "랜덤상자가 초기화되었습니다.");
				return true;
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				sender.sendMessage(Main.getInstance().getTitle() + ChatColor.YELLOW + "랜덤상자 리로드중입니다...");
				_randomBoxManager.reload();
				sender.sendMessage(Main.getInstance().getTitle() + ChatColor.GREEN + "랜덤상자 리로드가 완료되었습니다.");
				return true;
			}
			else
			{
				player.openInventory(_randomBoxManager.getRandomBox(args[0]));
				return true;
			}
		}
		sender.sendMessage(ChatColor.GREEN + " = = = = = [ Linmalu RandomBox ] = = = = =");
		LinmaluTellraw.sendChat(sender, "/" + label + " use ", ChatColor.GOLD + "/" + label + " use" + ChatColor.GRAY + " : 랜덤상자 작동");
		LinmaluTellraw.sendChat(sender, "/" + label + " clear ", ChatColor.GOLD + "/" + label + " clear" + ChatColor.GRAY + " : 랜덤상자 초기화");
		LinmaluTellraw.sendChat(sender, "/" + label + " reload ", ChatColor.GOLD + "/" + label + " reload" + ChatColor.GRAY + " : 랜덤상자 리로드");
		LinmaluTellraw.sendChat(sender, "/" + label + " ", ChatColor.GOLD + "/" + label + " <이름>" + ChatColor.GRAY + " : 랜덤상자 열기");
		sender.sendMessage(ChatColor.YELLOW + "제작자 : " + ChatColor.AQUA + "린마루(Linmalu)" + ChatColor.WHITE + " - http://blog.linmalu.com");
		return true;
	}
}
