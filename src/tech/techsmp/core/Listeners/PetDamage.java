package tech.techsmp.core.Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
public class PetDamage implements Listener{
	@EventHandler (priority  = EventPriority.HIGHEST)                      
    public void onPetDamage(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Tameable) {
			Tameable pet = (Tameable)event.getEntity();
			Player p = (Player)event.getDamager();
		if(p.getItemInHand().getType().equals(Material.GOLDEN_HOE) && pet.getOwner().equals(p)) {
			if(event.getDamage() >= pet.getHealth()) {
				event.setCancelled(true);
				pet.remove();
			}
			else {
				event.setCancelled(true);
				p.sendMessage("§cWarning: hitting your pet with a golden hoe WILL kill them");
				pet.damage(event.getDamage());
			}
		}
	}
	}
@EventHandler (priority = EventPriority.LOW, ignoreCancelled = true)                        
    public void onPetDamage(EntityDamageEvent event){
        Entity e = event.getEntity();
        if(e.getType().equals(EntityType.WOLF)){
            Wolf w = (Wolf) e;
            if(w.isSitting()){
                event.setCancelled(true);
                return;
            }
            if(w.isTamed()){
                if(event.getDamage() >= w.getHealth() && !event.isCancelled()){
                    w.setHealth(1);
                    try {
                        w.teleport(Bukkit.getServer().getPlayer(w.getOwner().getUniqueId()).getBedSpawnLocation());
                        w.setSitting(true);
                        Bukkit.getServer().getPlayer(w.getOwner().getUniqueId()).sendMessage("§c" + w.getName() + " has fainted! Do not worry they are safe and sound at your bed. Feed them to make them well again!");
                        event.setCancelled(true);

                    } catch (Exception exception) {
                        w.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                        w.setSitting(true);
                        try {
                            Player p = Bukkit.getPlayer(w.getOwner().getUniqueId());
                            p.sendMessage("§c" + w.getName() + " has fainted! Do not worry they are safe and sound at the animal shelter. Do /roundup to teleport them to you and feed them to make them better");
                            event.setCancelled(true);
                        } catch (Exception ex) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
        else if(e.getType().equals(EntityType.CAT)){
            Cat c = (Cat) e;
            if(c.isSitting()){
            event.setCancelled(true);
            return;
            }
        if(c.isTamed()){
            if(event.getDamage() > c.getHealth() && !event.isCancelled()){
                c.setHealth(1);
                try {
                    c.teleport(Bukkit.getServer().getPlayer(c.getOwner().getUniqueId()).getBedSpawnLocation());
                    c.setSitting(true);
                    Bukkit.getServer().getPlayer(c.getOwner().getUniqueId()).sendMessage("§c" + c.getName() + " has fainted! Do not worry they are safe and sound at your bed. Feed them to make them well again!");
                    event.setCancelled(true);

                } catch (Exception exception) {
                    c.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                    c.setSitting(true);
                    try {
                        Player p = Bukkit.getPlayer(c.getOwner().getUniqueId());
                        p.sendMessage("§c" + c.getName() + " has fainted! Do not worry they are safe and sound at the animal shelter. Do /roundup to teleport them to you and feed them to make them better");
                        event.setCancelled(true);
                    } catch (Exception ex) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        }
        else if(e.getType().equals(EntityType.PARROT)){
            Parrot p = (Parrot) e;
            if(p.isSitting()){
                event.setCancelled(true);
                return;
            }
        if(p.isTamed()){
            if(event.getDamage() > p.getHealth() && !event.isCancelled()){
                p.setHealth(0.1);
                try {
                    p.teleport(Bukkit.getServer().getPlayer(p.getOwner().getUniqueId()).getBedSpawnLocation());
                    p.setSitting(true);
                    Bukkit.getServer().getPlayer(p.getOwner().getUniqueId()).sendMessage("§c" + p.getName() + " has fainted! Do not worry they are safe and sound at your bed. Feed them to make them well again!");
                    event.setCancelled(true);

                } catch (Exception exception) {
                    p.teleport(new Location(Bukkit.getWorld("world"), -1, 106, 1));
                    p.setSitting(true);
                    try {
                        Player player = Bukkit.getPlayer(p.getOwner().getUniqueId());
                        player.sendMessage("§c" + p.getName() + " has fainted! Do not worry they are safe and sound at the animal shelter. Do /roundup to teleport them to you and feed them to make them better");
                        event.setCancelled(true);
                    } catch (Exception ex) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        }

    }   
}
