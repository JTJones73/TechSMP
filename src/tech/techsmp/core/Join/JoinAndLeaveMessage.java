package tech.techsmp.core.Join;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tech.techsmp.core.commands.Spec;
import tech.techsmp.core.commands.Vanish;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;


public class JoinAndLeaveMessage implements Listener{
	Vanish v = new Vanish();

    @EventHandler
	public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("rank.admin")) {
        	v.handleAdmin(p, false, false);
        	e.setJoinMessage(null);
        }
        else {
        	e.setJoinMessage("§7(§a+§7) §a" + p.getName());
        }
        
    }
    @EventHandler
	public void onQuit(PlayerQuitEvent event){
        Player p = event.getPlayer();
        if(p.hasPermission("rank.admin") && !v.handleAdmin(p, false, true)) {
        	event.setQuitMessage(null);
            Bukkit.getConsoleSender().sendMessage("§cPlayer " + p.getName() + " silently left the game.");

        }
        else {
            for(Player vanishedPlayer: Bukkit.getOnlinePlayers()){
                if(v.isPlayerVanished(vanishedPlayer)){
                    p.hidePlayer(vanishedPlayer);
                }
            }
        	event.setQuitMessage("§7(§c-§7) §c" + p.getName());
        }
        if(Spec.specOnLocation.containsKey(p)) {
        	p.teleport(Spec.specOnLocation.get(p));
        	p.setGameMode(GameMode.SURVIVAL);
        }
        
        //no pet left behind
        
        for(World w : Bukkit.getWorlds()) {
            for(Entity e : w.getEntitiesByClasses(Wolf.class)) {
                Wolf pet = (Wolf) e;
                if(pet.isTamed() && pet.isSitting() && pet.getOwner().equals(p)){
                    try {
                        pet.teleport(Bukkit.getServer().getPlayer(pet.getOwner().getUniqueId()).getBedSpawnLocation());
                    } 
                    catch (Exception exception) {
                        pet.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                    }   
                    pet.setSitting(true);
                }
            }
        }
            for(World w : Bukkit.getWorlds()) {
                for(Entity e : w.getEntitiesByClasses(Cat.class)) {
                    Cat pet = (Cat) e;
                    if(pet.isTamed() && pet.isSitting() && pet.getOwner().equals(p)){
                        try {
                            pet.teleport(Bukkit.getServer().getPlayer(pet.getOwner().getUniqueId()).getBedSpawnLocation());
                        } 
                        catch (Exception exception) {
                            pet.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                        }   
                        pet.setSitting(true);
                    }
                }
            }
                for(World w : Bukkit.getWorlds()) {
                    for(Entity e : w.getEntitiesByClasses(Parrot.class)) {
                        Parrot pet = (Parrot) e;
                        if(pet.isTamed() && pet.getOwner().equals(p) ){
                            if(pet.isTamed() && pet.isSitting() && pet.getOwner().equals(p)){
                                try {
                                    pet.teleport(Bukkit.getServer().getPlayer(pet.getOwner().getUniqueId()).getBedSpawnLocation());
                                } 
                                catch (Exception exception) {
                                    pet.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                                }   
                                pet.setSitting(true);
                            }
                        }
                    }
                }
        
    }
}