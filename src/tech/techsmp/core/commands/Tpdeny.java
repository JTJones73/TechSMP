package tech.techsmp.core.commands;

import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Tpdeny implements CommandExecutor {

    Tpa tpa = new Tpa();
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("tpdeny")){
            if(args.length == 1){
                Player p = (Player) sender;
                for(Map.Entry<String, String> set : Tpa.playerToTeleportPlayer.entrySet()){
                    if(set.getValue().equalsIgnoreCase(p.getName()) && args[0].equalsIgnoreCase(set.getKey())){
                        try{
                            Tpa.playerToTeleportPlayer.remove(set.getKey());
                            (Bukkit.getServer().getPlayer(set.getKey())).sendMessage("§c" + p.getName() + " has denied your teleport request");
                            p.sendMessage("§cYou have denied " + set.getKey() + "'s teleport request");
                            return true;
                        }
                        catch(Exception e){
                            continue;
                        }
                    }   
                }
                p.sendMessage("§cError you do not have a teleport request from " + args[0]);
                return true;
            }
            else{
                sender.sendMessage("§cError usage: /tpdeny <player> (denies a teleport request)");
                return true;
            }
        }
        return true;
    }
}   
