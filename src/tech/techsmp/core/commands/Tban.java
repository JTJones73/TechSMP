package tech.techsmp.core.commands;
import tech.techsmp.core.Join.PlayerPreJoin;
import tech.techsmp.core.cosmetic.Chat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tban implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(sender.hasPermission("rank.trusted")) {
				if(args.length > 1) {
					try {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						String reason = "";
						for(int i = 1; i < args.length; i++) {
							if(i == 1) reason = args[i];
							else reason = reason +  " " + args[i];
						}
						PlayerPreJoin.banList.put(p.getName().toLowerCase(), args[1]);
						sender.sendMessage("§a" + p.getName() + " has been banned!");
						p.kickPlayer("§cYou are banned from this server. \n §7Reason: " + reason + "\n §ePlease message an admin on our discord to be given an unban date\n§7Discord: discord.ttumc.net");
			            Logger.getLogger("Minecraft").info("§c" + p.getName() + " has been Banned by " + sender.getName() + " for reason: " + reason);
					}
					catch(Exception exception){
						sender.sendMessage("§cError could not find player " + args[0]);
					}
				}
				else {
					sender.sendMessage("§cError usage: /tban <player> <reason>");
				}
			}
			else sender.sendMessage("§cSorry! you do not have permission to use this command");
		return true;
	}
}