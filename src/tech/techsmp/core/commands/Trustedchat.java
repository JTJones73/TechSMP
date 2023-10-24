package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.techsmp.core.cosmetic.Chat;

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
                trustedPlayer.sendMessage(ChatColor.AQUA + "[Trusted Chat] " + ChatColor.WHITE + sender.getName() + ":" + trustedChatMsg);
            }
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Trusted Chat] " + ChatColor.WHITE + sender.getName() + ":" + trustedChatMsg);

    }
    else{
        sender.sendMessage("§cSorry! you do not have permission to use this command");
    }
    return true;
    }
}
