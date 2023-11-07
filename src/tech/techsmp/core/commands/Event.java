package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;
import utils.EventMode;

import java.nio.Buffer;

public class Event implements CommandExecutor {
    static EventMode event = null;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("end")){
                    if(event != null) {
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("EVENT_ENDED", new String[]{" "}));
                        event.endEvent();
                    }
                    else{
                        sender.sendMessage(ConfigMessage.getMessage("EVENT_ERROR_NOT_ENDED", new String[]{" "}));
                    }
                }

            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("start") && (args[1].equalsIgnoreCase("true")
                        || args[0].equalsIgnoreCase("false"))){
                    Player p = (Player) sender;
                    if(event != null){
                        sender.sendMessage(ConfigMessage.getMessage("EVENT_ERROR_NOT_ENDED", new String[]{" "}));
                    }
                    else if(args[1].equalsIgnoreCase("true")){
                        event = new EventMode(true, p.getLocation(), p.getLocation());
                    }
                    else{
                        event = new EventMode(false, p.getLocation(), p.getLocation());
                    }
                    event.startEvent();
                    Bukkit.broadcastMessage(ConfigMessage.getMessage("EVENT_STARTED", new String[]{" "}));
                }
                if(args[0].equalsIgnoreCase("imortal") && (args[1].equalsIgnoreCase("on")
                        || args[0].equalsIgnoreCase("off"))){
                    if(args[1].equalsIgnoreCase("on")){
                        event.setImortalEnabled(true);
                    }
                    else{
                        event.setImortalEnabled(false);
                    }
                }
                else if(args[0].equalsIgnoreCase("init")){
                    try{
                        event.initPlayers(new Player[]{Bukkit.getPlayer(args[1])});
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("EVENT_INIT_PLAYER", new String[]{Bukkit.getPlayer(args[1]).getName()}));
                    }
                    catch (Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("EVENT_ERROR_NO_PLAYER", new String[]{args[1]}));
                    }
                }
                else if(args[0].equalsIgnoreCase("uninit")){
                    try{
                        if(event.unInit(new Player[]{Bukkit.getPlayer(args[1])}))
                            Bukkit.broadcastMessage(ConfigMessage.getMessage("EVENT_UNINIT_PLAYER", new String[]{Bukkit.getPlayer(args[1]).getName()}));
                        else
                            sender.sendMessage(ConfigMessage.getMessage("EVENT_ERROR_NO_PLAYER", new String[]{args[1]}));
                    }
                    catch (Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("EVENT_ERROR_NO_PLAYER", new String[]{args[1]}));
                    }
                }
                else if(args[0].equalsIgnoreCase("permit")){
                    event.permitCmd(args[1]);
                    sender.sendMessage(ConfigMessage.getMessage("EVENT_PERMITTED_CMD", new String[]{args[1]}));
                }
                else if(args[0].equalsIgnoreCase("restrict")){
                    event.restrictCmd(args[1]);
                    sender.sendMessage(ConfigMessage.getMessage("EVENT_RESTRICTED_CMD", new String[]{args[1]}));
                }
            }
        }
        return true;
    }
}