package tech.techsmp.core.commands;
import tech.techsmp.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;


public class Rank implements CommandExecutor {
    private final Main plugin;
        
    public Rank(Main plugin) {
        this.plugin = plugin;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Rank")) {
            if(sender.hasPermission("rank.admin")){

                if(args.length == 2 && (args[1].equalsIgnoreCase("trusted") || args[1].equalsIgnoreCase("admin") || args[1].equalsIgnoreCase("owner"))){
                    try{
                        Player p = Bukkit.getServer().getPlayer(args[0]);
                        PermissionAttachment attachment = p.addAttachment(plugin);
                        if(args[1].equalsIgnoreCase("trusted")){ 
                            attachment.setPermission("rank.admin", false);
                            attachment.setPermission("rank.owner", false);
                            attachment.setPermission("rank.trusted", true);
                        }
                        if(args[1].equalsIgnoreCase("admin")){ 
                            attachment.setPermission("rank.admin", true);
                            attachment.setPermission("rank.owner", false);
                            attachment.setPermission("rank.trusted", true);
                        }
                        if(args[1].equalsIgnoreCase("owner")){ 
                            attachment.setPermission("rank.admin", true);
                            attachment.setPermission("rank.owner", true);
                            attachment.setPermission("rank.trusted", true);
                        }
                        sender.sendMessage("§aPlayer " + p.getName().toString() + " has Has been given rank of " + args[1].toLowerCase());
                    }
                    catch(Exception e){
                        sender.sendMessage("§cError: Could not find player");
                    }
                }
                else{
                    sender.sendMessage("§cError usage: /rank <player> <admin|trusted|owner>");
                }
            }
            else{
                sender.sendMessage("§fUnknown command. Type \"/help\" for help.");

            }

        }
        return true;
    }
}
