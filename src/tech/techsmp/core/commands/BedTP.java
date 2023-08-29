package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BedTP implements CommandExecutor {

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("bedtp")){
            if(sender instanceof Player){
                Player p = (Player) sender;
            try {
                p.teleport(p.getBedSpawnLocation());
                p.sendMessage("§aTeleporting you to your bed...");
            } catch (Exception e) {
                p.sendMessage("§cYour bed is missing...");

            }
        }
    }
    return true;
    }
    
}
