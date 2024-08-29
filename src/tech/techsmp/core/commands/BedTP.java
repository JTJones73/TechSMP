/*
 * 	Author: 		James Jones
 * 	Description:	This was the alternative to /home as it teleports you to your bed
 * */


package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;
import utils.Teleporter;

public class BedTP implements CommandExecutor {

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("bedtp")){
            if(sender instanceof Player){
                Player p = (Player) sender;
            try {
                Teleporter.teleport(p, p.getBedSpawnLocation());
                //p.teleport(p.getBedSpawnLocation());
                p.sendMessage(ConfigMessage.getMessage("BED_TELEPORT", new String[]{" "}));
            } catch (Exception e) {
                p.sendMessage(ConfigMessage.getMessage("BED_FAILED", new String[]{" "}));

            }
        }
    }
    return true;
    }
    
}
