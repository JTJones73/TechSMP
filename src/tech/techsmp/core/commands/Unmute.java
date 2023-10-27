package tech.techsmp.core.commands;
import org.bukkit.Bukkit;
import tech.techsmp.core.Join.PlayerPreJoin;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tech.techsmp.core.cosmetic.Chat;
import utils.ConfigMessage;

public class Unmute implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")) {
            if(args.length == 1) {
                try {
                    Chat.Muted.remove(args[0].toLowerCase());
                    Bukkit.getConsoleSender().sendMessage("§c" + args[0] + " has been unmuted by " + sender.getName());
                    sender.sendMessage(ConfigMessage.getMessage("UNMUTE_UNMUTED", new String[]{args[0]}));

                }
                catch(Exception exception){
                    sender.sendMessage(ConfigMessage.getMessage("UNMUTE_ERROR_NO_PLAYER", new String[]{" "}));
                }
            }
            else {
                sender.sendMessage(ConfigMessage.getMessage("UNMUTE_ERROR_USAGE", new String[]{" "}));
            }
        }
        else sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
        return true;
    }
}