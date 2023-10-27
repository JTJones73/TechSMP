package tech.techsmp.core.commands;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;


public class Whitelist implements CommandExecutor {
	public static ArrayList<String> guests= new ArrayList<String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wl")) {
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(!p.isWhitelisted()){
					p.sendMessage(ConfigMessage.getMessage("WHITELIST_MUST_BE_VERIFIED", new String[]{" "}));
					return true;
				}
			}
					if(args.length == 1){
						if(!isPlayerWhitelisted(args[0])){
							guests.add(args[0].toLowerCase());
							sender.sendMessage(ConfigMessage.getMessage("WHITELIST_WHITELISTED", new String[]{args[0]}));
						}
						else{
							sender.sendMessage(ConfigMessage.getMessage("WHITELIST_ALREADY_WHITELISTED", new String[]{args[0]}));
						}
					
					}
					else {
						sender.sendMessage(ConfigMessage.getMessage("WHITELIST_ERROR_USAGE", new String[]{" "}));
					}
			}
		
    	return true;
	}
	public boolean isPlayerWhitelisted(String name){
			if(guests.contains(name.toLowerCase())){
				return true;
			}
			else{
				return false;
			}
	}
}
