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
import utils.ConfigMessage;
import utils.DiscordWebhook;

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
						sender.sendMessage(ConfigMessage.getMessage("TBAN_PLAYER_BANNED", new String[]{" "}));
						DiscordWebhook.sendDiscordMsg("", "Player Banned", p.getName() + " banned by " + sender.getName(), "Reason: " + reason);
						p.kickPlayer("§cYou are banned from this server. \n §7Reason: " + reason + "\n §ePlease message an admin on our discord to be given an unban date\n§7Discord: discord.ttumc.net");
						Bukkit.getConsoleSender().sendMessage("§c" + p.getName() + " has been Banned by " + sender.getName() + " for reason: " + reason);
					}
					catch(Exception exception){
						sender.sendMessage(ConfigMessage.getMessage("TBAN_ERROR_NO_PLAYER", new String[]{args[1]}));
					}
				}
				else {
					sender.sendMessage(ConfigMessage.getMessage("TBAN_ERROR_USAGE", new String[]{" "}));
				}
			}
			else sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
		return true;
	}
}