package tech.techsmp.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.EulerAngle;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Devfeature implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(sender.isOp()) {
				Player p = (Player) sender;
				
                for(Entity e : p.getLocation().getWorld().getEntitiesByClasses(ArmorStand.class)) {
                    ArmorStand as = (ArmorStand) e;
                    //headpose: pitch yaw left/right +-pi radians
                    //bodypose same back+ forward - left + right - cc + ccw -
                    as.setLeftArmPose(new EulerAngle(Float.parseFloat(args[0]),Float.parseFloat(args[1]),Float.parseFloat(args[2])));
                    if(args[3].equalsIgnoreCase("a")) {
                    	as.setSmall(true);
                    	as.
                    }
                    if(args[4].equalsIgnoreCase("a")) {
                    	as.setArms(true);
                    }
                    if(args[5].equalsIgnoreCase("a")) {
                    	as.setBasePlate(false);
                    }
                }
 			}

        return true;  
    }
}