package tech.techsmp.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Discord implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("discord")){
            sender.sendMessage("§6Join our discord here: §ldiscord.ttumc.tech");
        }  
        return true;  
    }
}
