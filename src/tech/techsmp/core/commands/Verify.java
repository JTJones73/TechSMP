package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.OfflinePlayer;

public class Verify implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Verify")) {
            if(sender.hasPermission("rank.trusted")){
                if(args.length == 1){
                    try {
                        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + p.getName());
                        if(p.isWhitelisted())
                        	sender.sendMessage("§aPlayer " + p.getName().toString() + " has been verified!");
                        else
                            sender.sendMessage("§cError: Could not find player");

                    }
                    catch(Exception e){
                        sender.sendMessage("§cError: Could not find player");
                    }
                }
            }
            else
            	sender.sendMessage("§cSorry! You do not have permission to use this command.");

        }
        else{
            sender.sendMessage("§cError usage: /verify <player>");
        }
        return true;
    }
}
