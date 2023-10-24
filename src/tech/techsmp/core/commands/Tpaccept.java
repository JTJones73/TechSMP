package tech.techsmp.core.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;
import utils.Teleporter;

public class Tpaccept implements CommandExecutor {

    Tpa tpa = new Tpa();
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("tpaccept")){
            if(args.length == 1 && sender instanceof Player){
            Player p = (Player) sender;
            Boolean isCompleted = false;
            for(Map.Entry<String, String> set : Tpa.playerToTeleportPlayer.entrySet()){
                if(set.getValue().equalsIgnoreCase(p.getName()) && args[0].equalsIgnoreCase(set.getKey())){
                    try{
                        isCompleted = true;
                        Teleporter.teleport((Bukkit.getServer().getPlayer(set.getKey())), p.getLocation());

                        //(Bukkit.getServer().getPlayer(set.getKey())).teleport(p.getLocation());
                        Tpa.playerToTeleportPlayer.remove(set.getKey());
                        p.sendMessage("§aTeleporting...");
                        break;
                    }
                    catch(Exception e){
                        continue;
                    }
                }   
            }
            if(!isCompleted)
                sender.sendMessage("§cYou do not currently have any teleport requests");
        }
        else{
            sender.sendMessage(ConfigMessage.getMessage("TPA_TELEPORTING", new String[]{" "}));
        }
        }
        return true;
    }
}
