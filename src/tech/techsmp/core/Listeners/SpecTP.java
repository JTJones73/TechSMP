package tech.techsmp.core.Listeners;


import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tech.techsmp.core.commands.Spec;

import static java.lang.Integer.parseInt;

public class SpecTP implements Listener{

@EventHandler
  public void onPreprocess(PlayerCommandPreprocessEvent event){
      if(event.getMessage().toLowerCase().startsWith("/help") || event.getMessage().toLowerCase().startsWith("/?")){
          event.setCancelled(true);
          Player p = event.getPlayer();
          p.sendMessage("§9------Welcome to the Tennessee Tech MC Server------");
          p.sendMessage("§9/help                    §7§7Displays help message");
          p.sendMessage("§9§9/discord                §7§7Gives link to our discord");
          p.sendMessage("§9§9/roundup                §7§7Teleports all of your loaded pets to you");
          p.sendMessage("§9/bedtp                   §7§7Teleports you to your bed");
          p.sendMessage("§9/tpa <player>          §7§7Sends teleport request to player");
          p.sendMessage("§9/wl <player>            §7§7Temporarily whitelists a player");
          p.sendMessage("§9/fullbright              §7§7Toggles night visions");
          p.sendMessage("§9/home                    §7§7Teleports you home (use: /sethome)");
          p.sendMessage("§9/help                     §7§7Displays help message");
          return;

      }
      if(!event.getPlayer().isOp()){
          if(event.getMessage().toLowerCase().startsWith("/me")){
              event.getPlayer().sendMessage("§cWe don't do that here");
             event.setCancelled(true);
             return;

            }
          if(event.getMessage().toLowerCase().startsWith("/co i") && !event.getMessage().equals("/co i dontdothis")) {
        	  event.setCancelled(true);
        	  event.getPlayer().sendMessage("§cPlease use /inspect <on|off>");
          }
      }
      if(!event.getPlayer().isOp()){
          if(event.getMessage().toLowerCase().startsWith("/whitelist")){
        	  if(event.getMessage().length() >= 12) {
        		  Bukkit.dispatchCommand(event.getPlayer(), "wl " + event.getMessage().substring(11));
        	  }
        	  else
        		  Bukkit.dispatchCommand(event.getPlayer(), "wl");
             event.setCancelled(true);
             return;

            }
      }
      if(!(event.getMessage().startsWith("/tp ") || (event.getMessage().length() < 4 && event.getMessage().startsWith("/tp"))) )
        return;
    Player p = event.getPlayer();
        event.setCancelled(true);

        if(p.getGameMode().equals(GameMode.SPECTATOR) || Spec.specOnLocation.containsKey(p.getPlayer().getName())){
            if(event.getMessage().split(" ").length == 2){
                String[] playerName = event.getMessage().split(" ");
                try{
                    Player tp = Bukkit.getServer().getPlayer(playerName[1]);
                    p.teleport(tp.getLocation());
                    p.sendMessage("§aTeleporting you to " + tp.getName());
                }
                catch(Exception e){
                    p.sendMessage("§cError: could not find player " + playerName[1]);
                }
            }
            else if(event.getMessage().split(" ").length == 4){
                String[] location = event.getMessage().split(" ");
                try{
                    p.teleport(new Location(Bukkit.getWorld("world"), parseInt(location[1]), parseInt(location[2]), parseInt(location[3])));
                }
                catch (Exception e){
                    p.sendMessage("§cError: could not find location");

                }
            }
            else if(event.getMessage().split(" ").length == 5){
                String[] location = event.getMessage().split(" ");
                try{
                    p.teleport(new Location(Bukkit.getWorld(location[4]), parseInt(location[1]), parseInt(location[2]), parseInt(location[3])));
                }
                catch (Exception e){
                    p.sendMessage("§cError: could not find location");

                }
            }
            else
                p.sendMessage("§cError usage: /tp <player>");
        }
        else
            p.sendMessage("§cError: you must be in spectator mode to use this command");
}
    
}
