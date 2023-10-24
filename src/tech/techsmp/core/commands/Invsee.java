package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tech.techsmp.core.Listeners.GuiListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;

public class Invsee implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("invsee")){
            if(sender.hasPermission("rank.trusted")){
                if(args.length == 1){
                	try {
                        Player p = (Player) sender;
                        Player target = Bukkit.getServer().getPlayer(args[0]);
                        Inventory inv = Bukkit.getServer().createInventory(null, 45, target.getName() + "'s Inventory");                            //Inventory inv = plugin.getServer().createInventory(p, 45);
                        for(int i = 0; i < 40; i++){
                            if(target.getInventory().getItem(i) != null && !target.getInventory().getItem(i).getType().equals(Material.AIR)){
                                inv.setItem(i, target.getInventory().getItem(i));
                            }
                        }
                        if(target.getInventory().getItemInOffHand() != null && !target.getInventory().getItemInOffHand().getType().equals(Material.AIR)){
                            inv.setItem(44, target.getInventory().getItemInOffHand());
                        }
                        GuiListener.inGui.add(p);
                        p.openInventory(inv);;	
                	}
                	catch(Exception ex) {
                		sender.sendMessage(ConfigMessage.getMessage("INVSEE_NO_SUCH_PLAYER", new String[]{" "}));
                	}

                }
                else if(args.length == 2 && args[1].equalsIgnoreCase("-e")){
                	try {
	                    Player p = (Player) sender;
	                    Player target = Bukkit.getServer().getPlayer(args[0]);
	                    GuiListener.inGui.add(p);
	                    p.openInventory(target.getEnderChest());
                	}
                	catch(Exception ex) {
                		sender.sendMessage(ConfigMessage.getMessage("INVSEE_NO_SUCH_PLAYER", new String[]{" "}));
                	}
                }
            }
            else {
                sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
            }

        }  
        return true;  
    }
}
