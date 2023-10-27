package tech.techsmp.core.commands;

import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;

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
                            p.sendMessage(ConfigMessage.getMessage("TPDENY_YOU_DENIED", new String[]{set.getKey()}));
                            return true;
                        }
                        catch(Exception e){
                            continue;
                        }
                    }   
                }
                p.sendMessage(ConfigMessage.getMessage("TPDENY_ERROR_NO_TPA", new String[]{args[0]}));
                return true;
            }
            else{
                sender.sendMessage(ConfigMessage.getMessage("TPDENY_ERROR_USAGE", new String[]{""}));
                return true;
            }
        }
        return true;
    }
}   
