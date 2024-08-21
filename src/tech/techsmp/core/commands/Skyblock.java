/*
*
* Author:       James Jones
* Description:  Handles the skyblock command and invokes skyblock utils
*
* */


package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;
import utils.EventMode;
import utils.SkyblockUtil;

import java.nio.Buffer;
import java.util.LinkedList;


public class Skyblock implements CommandExecutor {
    LinkedList<Player> islandSpamPlayer = new LinkedList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("generate")){
                    if(sender instanceof Player && !islandSpamPlayer.contains((Player) sender)) {
                        sender.sendMessage(ConfigMessage.getMessage("SKYBLOCK_GENERATING", new String[]{" "}));
                        SkyblockUtil.generateSkyblock((Player) sender);
                    }
                    else
                        sender.sendMessage(ConfigMessage.getMessage("SKYBLOCK_ISLAND_SPAM", new String[]{" "}));
                }

            } else if (args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("tp")))) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ConfigMessage.getMessage("SKYBLOCK_TELEPORTED_SOLO", new String[]{" "}));
                    putPlayerInSolo(p);
                }
            }
                else if(args.length == 1 && (args[0].equalsIgnoreCase("coop") || args[0].equalsIgnoreCase("co-op"))){
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        p.sendMessage(ConfigMessage.getMessage("SKYBLOCK_TELEPORTED_COOP", new String[]{" "}));
                        putPlayerInCoop(p);
                    }
                }

        return true;
    }
}