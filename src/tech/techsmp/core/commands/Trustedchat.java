package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.techsmp.core.cosmetic.Chat;
import utils.ConfigMessage;

import javax.net.ssl.SSLEngine;
import java.util.logging.Logger;

public class Trustedchat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(sender.hasPermission("rank.trusted")){
        String trustedChatMsg = "";
        for(int i = 0; i < args.length; i++){
            trustedChatMsg = trustedChatMsg + " " + args[i];
        }
        for(Player trustedPlayer : Bukkit.getOnlinePlayers()) {
            if(trustedPlayer.hasPermission("rank.trusted")){
                trustedPlayer.sendMessage(ConfigMessage.getMessage("TRUSTED_CHAT", new String[]{sender.getName(), trustedChatMsg}));
            }
        }
        Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("TRUSTED_CHAT", new String[]{sender.getName(), trustedChatMsg}));

    }
    else{
        sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
    }
    return true;
    }
}
