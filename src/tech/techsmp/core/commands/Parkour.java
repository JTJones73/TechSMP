package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.techsmp.core.Listeners.ParkourListener;
import utils.ConfigMessage;

public class Parkour implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("start")){
                        ParkourListener.initEvent();
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("PARKOUR_EVENT_STARTED", new String[]{" "}));

                }
                if(args[0].equalsIgnoreCase("end")){
                    if(ParkourListener.inEvent){
                        ParkourListener.restrictList.clear();
                        ParkourListener.inEvent = false;
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("PARKOUR_EVENT_ENDED", new String[]{" "}));

                    }
                }
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("init")){
                    try{
                        ParkourListener.initPlayer(Bukkit.getPlayer(args[1]));
                        Bukkit.getPlayer(args[1]).sendMessage(ConfigMessage.getMessage("PARKOUR_READY_TO_BEGIN", new String[]{Bukkit.getPlayer(args[1]).getName()}));
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("PARKOUR_ACTIVE_PLAYER", new String[]{Bukkit.getPlayer(args[1]).getName()}));

                    }
                    catch (Exception e){}
                }
            }

        }

        return true;
    }
}
