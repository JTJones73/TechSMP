package tech.techsmp.core.commands;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class Whitelist implements CommandExecutor {
	public static ArrayList<String> guests= new ArrayList<String>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wl")) {
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(!p.isWhitelisted()){
					p.sendMessage("§cSorry! You must be a verified player to use this command. Get verified by joining our discord ");
					return true;
				}
			}
					if(args.length == 1){
						if(!isPlayerWhitelisted(args[0])){
							guests.add(args[0].toLowerCase());
							sender.sendMessage("§aAdded player " + args[0].toString() + " to whitelist");
						}
						else{
							sender.sendMessage("§c" + args[0] + " is already whitelisted");
						}
					
					}
					else {
						sender.sendMessage("§cError usage: /wl <player> (allows you to temporarily whitelist a player)");
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
