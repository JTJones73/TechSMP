package tech.techsmp.core.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import tech.techsmp.core.Main;



public class Inspect implements CommandExecutor {
    private final Main plugin;
    
    public Inspect(Main plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("rank.trusted")){
            sender.sendMessage("§cSorry! you do not have permission to use this command");
            return true;
        }
        if(args.length == 1){
            if(sender instanceof Player){
                if(args[0].equalsIgnoreCase("on")){
                    Player p = (Player) sender;
                	if(Spec.specOnLocation.containsKey(p)) {
                		p.sendMessage("§cYou cannot use inspect in this mode!");
                		Spec.specOnLocation.put(p,p.getLocation());
                	}
                	else {
	                    p.setGameMode(GameMode.SURVIVAL);
	                    Spec.specOnLocation.put(p, p.getLocation());
                        sender.sendMessage("§aYou now an inspector!");
	                    PermissionAttachment pA = p.addAttachment(plugin);
	                    pA.setPermission("coreprotect.inspect", true);
	                    pA.setPermission("coreprotect.rollback", true);
	                    pA.setPermission("coreprotect.inspect", true);
	                    p.performCommand("co i dontdothis");
                	}
                }
                if(args[0].equalsIgnoreCase("off")){
                    Player p = (Player) sender;
                    
                    if(Spec.specOnLocation.containsKey(p) && p.getGameMode().equals(GameMode.SURVIVAL)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        PermissionAttachment pA = p.addAttachment(plugin);
                    	try {
                    		p.teleport(Spec.specOnLocation.get(p));
                    		Spec.specOnLocation.remove(p);
                    	}
                    	catch(Exception ex) {}
                        p.performCommand("co i dontdothis");
                        sender.sendMessage("§cYou no longer an inspector");
                        pA.setPermission("coreprotect.inspect", false);
                        pA.setPermission("coreprotect.rollback", false);
                        pA.setPermission("coreprotect.inspect", false);
                    }
                    else {
                    	p.sendMessage("§cYou must be an inspector to not be an inspector... The kid gets it");
                    }

                }
            }
            else{
                sender.sendMessage("§cError usage: /spec <on|off> player");
            }
        }
        else if(args.length == 2){
            try{
                Player p =  Bukkit.getServer().getPlayer(args[1]);
                if(args[0].equalsIgnoreCase("on")){
	            	if(Spec.specOnLocation.containsKey(p)) {
	            		sender.sendMessage("§c" + p.getName() + " is not in the right mode");
	            	}
	            	else {
	                    p.setGameMode(GameMode.SURVIVAL);
	                    Spec.specOnLocation.put(p, p.getLocation());
	                    sender.sendMessage("§a" + p.getName() + " is now an inspector");
	                    p.sendMessage("§aYou now an inspector!");
	                    PermissionAttachment pA = p.addAttachment(plugin);
	                    pA.setPermission("coreprotect.inspect", true);
	                    pA.setPermission("coreprotect.rollback", true);
	                    pA.setPermission("coreprotect.inspect", true);
	                    p.performCommand("co i dontdothis");
	            	}
                }
            if(args[0].equalsIgnoreCase("off")){                
                if(Spec.specOnLocation.containsKey(p) && p.getGameMode().equals(GameMode.SURVIVAL)) {
                    p.setGameMode(GameMode.SURVIVAL);
                    PermissionAttachment pA = p.addAttachment(plugin);
                    p.performCommand("co i dontdothis");
                    sender.sendMessage("§aRemoved " + p.getName() + " from inspector mode");
                    p.sendMessage("§cYou no longer an inspector");
                    pA.setPermission("coreprotect.inspect", false);
                    pA.setPermission("coreprotect.rollback", false);
                    pA.setPermission("coreprotect.inspect", false);
                	try {
                		p.teleport(Spec.specOnLocation.get(p));
                		Spec.specOnLocation.remove(p);
                	}
                	catch(Exception ex) {}
                    Spec.specOnLocation.remove(p);
                }
                else {
                	sender.sendMessage("§cError " + p.getName() + " is not an inspector");
                }

            }
            }
            catch(Exception e){
                sender.sendMessage("§cError: Player not found");
            }
        }
        else{
            sender.sendMessage("§cError usage: /inspect <on|off> player");
        }
    
        return true;
    }
}

