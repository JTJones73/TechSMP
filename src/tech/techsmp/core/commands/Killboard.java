package tech.techsmp.core.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import utils.ConfigMessage;

import static java.lang.Integer.parseInt;

public class Killboard implements CommandExecutor {
    static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    //Scoreboard killboard = Bukkit.getScoreboardManager().getNewScoreboard();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission("rank.trusted")){
            if(args.length == 1 && args[0].equalsIgnoreCase("off")){
                killKillboard();
                sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_OFF", new String[]{" "}));
            }
            if(args.length >1){
                //Bukkit.broadcastMessage(" > 1");
                if(args[0].equalsIgnoreCase("on")){
                    String kbTitle = "";
                    for(int i = 1; i < args.length; i++){
                        if(kbTitle != ""){
                            kbTitle = kbTitle + " " + args[i];
                        }
                        else{
                            kbTitle = args[i];
                        }
                    }
                    kbTitle = kbTitle.replace('&', '§');
                    try{
                        sb.getObjective("killboard").unregister();
                    }catch (Exception e){}

                    sb.clearSlot(DisplaySlot.SIDEBAR);
                    sb.registerNewObjective("killboard", "score", kbTitle).setDisplaySlot(DisplaySlot.SIDEBAR);
                    sb.getObjective("killboard").setDisplayName(kbTitle);
                }
                /*else{
                    Bukkit.broadcastMessage("1st else");

                    sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_ERROR_USAGE", new String[]{" "}));

                }*/


            }
            if(args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                    try {
                        Score s = sb.getObjective("killboard").getScore(Bukkit.getPlayer(args[1]));
                        s.setScore(parseInt(args[2]));
                        for(Player p: Bukkit.getOnlinePlayers()){
                            p.setScoreboard(sb);
                        }
                    }catch (Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_SET_ERROR", new String[]{" "}));
                    }
                }
            }
            else if(args.length == 2){
                if(args[0].equalsIgnoreCase("remove")){
                    try{
                        sb.resetScores(args[1]);
                        sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_REMOVED_PLAYER", new String[]{args[1]}));
                    }
                    catch (Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_ERROR_NO_PLAYER", new String[]{args[1]}));
                    }
                }
            }
            else{
                sender.sendMessage(ConfigMessage.getMessage("KILLBOARD_ERROR_USAGE", new String[]{" "}));
            }

        }
        return true;
    }
    public static void initKillboard(String title){
        try{
            sb.getObjective("killboard").unregister();
        }catch (Exception e){}

        sb.clearSlot(DisplaySlot.SIDEBAR);
        sb.registerNewObjective("killboard", "score", title).setDisplaySlot(DisplaySlot.SIDEBAR);
        sb.getObjective("killboard").setDisplayName(title);
    }
    public static void sendKillboard(Player p){
        p.setScoreboard(sb);
    }
    public static void removeKillboard(Player p){
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    public static void setScore(Player p, int score){
        try {
            sb.getObjective("killboard").getScore(p.getName()).setScore(score);
        }catch (Exception e){}
    }
    public static int getScore(Player p){
        try {
            return sb.getObjective("killboard").getScore(p.getName()).getScore();
        }catch (Exception e){}
        return -1;
    }
    public static void killKillboard(){
        try{
            sb.getObjective("killboard").unregister();
        }catch (Exception e){}
    }
}

