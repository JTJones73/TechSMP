package tech.techsmp.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener{

    @EventHandler
	public void playerDeath(PlayerDeathEvent event){
    	Player p = (Player) event.getEntity();
        for(World w : Bukkit.getWorlds()) {
            for(Entity e : w.getEntitiesByClasses(Wolf.class)) {
                Wolf pet = (Wolf) e;
                if(pet.isTamed() && pet.getOwner().equals(p) ){
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
                    if(pet.isTamed() && pet.getOwner().equals(p) ){
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
                            if(pet.isTamed() && pet.getOwner().equals(p) ){
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
