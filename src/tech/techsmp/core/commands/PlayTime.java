package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;
import utils.OnlineTime;

public class PlayTime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            int ms = OnlineTime.getActiveMs((OfflinePlayer) sender);

            sender.sendMessage(ConfigMessage.getMessage("PLAY_TIME_DISPLAY", new String[]{sender.getName(), String.valueOf(ms/86400000), String.valueOf((ms % 86400000)/3600000), String.valueOf((ms % 3600000)/60000), String.valueOf((ms % 60000)/1000)}));

        }
        if(args.length == 1){
            try {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                int ms = OnlineTime.getActiveMs(target);
                sender.sendMessage(ConfigMessage.getMessage("PLAY_TIME_DISPLAY", new String[]{target.getName(), String.valueOf(ms/86400000), String.valueOf((ms % 86400000)/3600000), String.valueOf((ms % 3600000)/60000), String.valueOf((ms % 60000)/1000)}));
            }
            catch (Exception e){
                sender.sendMessage(ConfigMessage.getMessage("PLAY_TIME_ERROR_NO_PLAYER", new String[]{args[0]}));

            }


        }
        return true;
    }
}
