package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.techsmp.core.Listeners.SpleefListener;
import utils.ConfigMessage;
import utils.Teleporter;

public class Spleef implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("rank.trusted") || args.length == 0){
                p.sendMessage(ConfigMessage.getMessage("SPLEEF_WELCOME", new String[]{" "}));
                Teleporter.teleport(p, SpleefListener.spleefOffLocation);
            } else if (args.length == 1) {
                if(args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")){
                    Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_OFF", new String[]{" "}));
                    SpleefListener.disableSpleef();
                }
                if(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")){
                    Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_ON", new String[]{" "}));

                    SpleefListener.enableSpleef();
                }

            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("broadcastspleefs")){
                    if(args[1].equalsIgnoreCase("on")){
                        SpleefListener.broadcastSpleefs = true;
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_BROADCAST_ON", new String[]{" "}));

                    }
                    if(args[1].equalsIgnoreCase("off")){
                        SpleefListener.broadcastSpleefs = false;
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_BROADCAST_OFF", new String[]{" "}));


                    }
                }
            }
        }
        return true;
    }
}
