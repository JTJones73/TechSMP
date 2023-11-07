package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import utils.ConfigMessage;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Team implements CommandExecutor {
    public static boolean teamManagementOn = false;
    public static int teamSize = 0;
    public static ArrayList<String> teamList;
    //static Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")){
            if(args.length == 1){
                //TODO
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("management")){
                    if(args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("enable")){
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("TEAM_MANAGEMENT_ON", new String[]{" "}));
                        teamManagementOn = true;
                    }
                    else if(args[1].equalsIgnoreCase("off") || args[1].equalsIgnoreCase("disable")){
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("TEAM_MANAGEMENT_OFF", new String[]{" "}));
                        teamManagementOn = false;
                    }
                }
                else if(args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("limit")){
                    try{
                        teamSize = parseInt(args[1]);
                        Bukkit.broadcastMessage(ConfigMessage.getMessage("TEAM_TEAMSIZE_SET", new String[]{args[1]}));
                    }
                    catch (Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("TEAM_ERROR_ADMIN_USAGE", new String[]{" "}));
                    }

                }
            }

        }
        return true;
    }
}