package tech.techsmp.core.commands;
import tech.techsmp.core.Main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpa implements CommandExecutor {

    public static Map<String, String> playerToTeleportPlayer = new HashMap<String, String>(10);
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Tpa")){
            Player p = (Player) sender;
            if(args.length == 1){
                if(playerToTeleportPlayer.containsKey(p.getName())){
                    p.sendMessage("§cYou already have a teleport request active to " + playerToTeleportPlayer.get(p.getName()));
                }
                try{
                    Player tPlayer = Bukkit.getServer().getPlayer(args[0]);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + tPlayer.getName() + " {\"text\":\"" + p.getName() + "  wants to teleport to you \",\"color\":\"yellow\",\"bold\":\"false\",\"extra\":[{\"text\":\"§l[Accept] \",\"color\":\"green\",\"bold\":\"false\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept " + p.getName() + "\"},\"extra\":[{\"text\":\"§l[Deny]\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpdeny " + p.getName() + "\"}}]}]}");
                    tPlayer.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 2F, 1F);
                    playerToTeleportPlayer.put(p.getName(), tPlayer.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {      
                        @Override
                        public void run() {
                            try {
                                if(playerToTeleportPlayer.containsKey(p.getName()) && playerToTeleportPlayer.get(p.getName()) != null){
                                String tpExpire = playerToTeleportPlayer.get(p.getName());
                                playerToTeleportPlayer.remove(p.getName());
                                p.sendMessage("§c your teleport request to " + tpExpire + " has expired");
                                }
                            } catch (Exception e) {}
                        }          

                    }, 3600L);
                }
                catch(Exception e){
                    p.sendMessage("§cError usage: /tpa <player> (allows you to teleport to a player by sending a request)");
                }

            }
        }
        return true;
    }
}
