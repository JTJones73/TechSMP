package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Parrot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;

public class Roundup implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("roundup")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage(ConfigMessage.getMessage("ROUNDUP_TRYING_TO_FIND_PETS", new String[]{" "}));
                int foundPets  = 0;
                for(World w : Bukkit.getWorlds()) {
                    for(Entity e : w.getEntitiesByClasses(Wolf.class)) {
                        Wolf pet = (Wolf) e;
                        if(pet.isTamed() && pet.getOwner().equals(p) ){
                        	foundPets++;
                            pet.teleport(p.getLocation());
                            pet.setSitting(true);
                        }
                    }
                }
                    for(World w : Bukkit.getWorlds()) {
                        for(Entity e : w.getEntitiesByClasses(Cat.class)) {
                            Cat pet = (Cat) e;
                            if(pet.isTamed() && pet.getOwner().equals(p) ){
                            	foundPets++;
                                pet.teleport(p.getLocation());
                                pet.setSitting(true);
                            }
                        }
                    }
                        for(World w : Bukkit.getWorlds()) {
                            for(Entity e : w.getEntitiesByClasses(Parrot.class)) {
                                Parrot pet = (Parrot) e;
                                if(pet.isTamed() && pet.getOwner().equals(p) ){
                                	foundPets++;
                                    pet.teleport(p.getLocation());
                                    pet.setSitting(true);
                                }
                            }
                        }
                        p.sendMessage(ConfigMessage.getMessage("ROUNDUP_FOUNDPETS", new String[]{foundPets + ""}));
            }
        }
        return true;
    }
}
