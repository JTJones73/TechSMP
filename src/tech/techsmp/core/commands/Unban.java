package tech.techsmp.core.commands;
import tech.techsmp.core.Join.PlayerPreJoin;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Unban implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")) {
            if(args.length == 1) {
                try {
                    PlayerPreJoin.banList.remove(args[0].toLowerCase());
                    Logger.getLogger("Minecraft").info("§c" + args[0] + " has been unbanned by " + sender.getName());
                    sender.sendMessage("§cUnbanned " + args[0]);
                }
                catch(Exception exception){
                    sender.sendMessage("§cError: player is not banned (or you mistyped)");
                }
            }
            else {
                sender.sendMessage("§cError usage: /unban <player>");
            }
        }
        else sender.sendMessage("§cSorry! you do not have permission to use this command");
        return true;
    }
}