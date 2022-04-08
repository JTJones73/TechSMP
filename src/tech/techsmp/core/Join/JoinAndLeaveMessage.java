package tech.techsmp.core.Join;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


public class JoinAndLeaveMessage implements Listener{

    @EventHandler
	public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        e.setJoinMessage("§7(§a+§7) §a" + p.getName());
        
    }
    @EventHandler
	public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.setQuitMessage("§7(§c-§7) §c" + p.getName());
        
    }
}