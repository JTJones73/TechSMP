package tech.techsmp.core.commands;
import tech.techsmp.core.cosmetic.Chat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mute")){
			if(sender.isOp() || sender.hasPermission("rank.trusted")) {
				if(args.length == 1) {
					try {
						Player p = Bukkit.getServer().getPlayer(args[0]);
						Chat.Muted.add(p.getName().toLowerCase());
						sender.sendMessage("§a" + p.getName() + " has been muted");
			            Logger.getLogger("Minecraft").info("§a" + p.getName() + " has been muted by " + sender.getName());
					}
					catch(Exception exception){
						sender.sendMessage("§cError could not find player " + args[0]);
					}
				}
			}
			else
				sender.sendMessage("§cSorry! you do not have permission to use this command");
		}
		return true;
	}
}
