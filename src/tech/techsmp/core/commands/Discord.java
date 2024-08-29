/*
 * 	Author: 		James Jones
 * 	Description:	This command sends you the discord link
 * */

package tech.techsmp.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;

public class Discord implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("discord")){
            sender.sendMessage(ConfigMessage.getMessage("DISCORD", new String[]{" "}));
        }  
        return true;  
    }
}
