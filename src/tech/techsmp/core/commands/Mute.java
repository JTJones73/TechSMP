package tech.techsmp.core.commands;
import tech.techsmp.core.cosmetic.Chat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;

public class Mute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mute")){
			if(sender.isOp() || sender.hasPermission("rank.trusted")) {
				if(args.length == 1) {
					try {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						Chat.Muted.add(p.getName().toLowerCase());
						sender.sendMessage(ConfigMessage.getMessage("MUTED_PLAYER_MUTED", new String[]{p.getName()}));
						Bukkit.getConsoleSender().sendMessage("§a" + p.getName() + " has been muted by " + sender.getName());
					}
					catch(Exception exception){
						sender.sendMessage(ConfigMessage.getMessage("MUTED_COULD_NOT_FIND_PLAYER", new String[]{args[0]}));
					}
				}
			}
			else
				sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
		}
		return true;
	}
}
