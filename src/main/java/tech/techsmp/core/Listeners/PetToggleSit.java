package tech.techsmp.core.Listeners;
import tech.techsmp.core.Main;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
public class PetToggleSit implements Listener{

@EventHandler
    public void onPetRightClick(PlayerInteractEntityEvent e) {
       
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();
        if(e.getRightClicked().getType().equals(EntityType.WOLF)) {
            Wolf pet = (Wolf) entity;
            if(pet.isSitting() && pet.isTamed() && pet.getHealth() <= 1){
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            
                    @Override
                        public void run() {
                            if(pet.getHealth() <= 1 && !pet.isSitting()){
                                pet.setSitting(true);
                                p.sendMessage("§c" + pet.getName() + " is too tired! Feed them to make them well again!");
                            }
                        }
                    }, 5L);
            }
            else if(pet.isTamed() && pet.isAngry()) {
            	pet.setAngry(false);
            }
        }
        if(e.getRightClicked().getType().equals(EntityType.CAT)) {
            Cat pet = (Cat) entity;
            if(pet.isSitting() && pet.isTamed() && pet.getHealth() <= 1){
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            
                    @Override
                        public void run() {
                            if(pet.getHealth() <= 1 && !pet.isSitting()){
                                pet.setSitting(true);
                                p.sendMessage("§c" + pet.getName() + " is too tired! Feed them to make them well again!");
                            }
                        }
                    }, 5L);
            }
        }
        if(e.getRightClicked().getType().equals(EntityType.PARROT)) {
            Parrot pet = (Parrot) entity;
            if(pet.isSitting() && pet.isTamed() && pet.getHealth() <= 0.1 ){
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            
                    @Override
                        public void run() {
                            if(pet.getHealth() <= 1 && !pet.isSitting()){
                                pet.setSitting(true);
                                p.sendMessage("§c" + pet.getName() + " is too tired! Feed them to make them well again!");
                            }
                        }
                    }, 5L);
            }
        }

    }
}
