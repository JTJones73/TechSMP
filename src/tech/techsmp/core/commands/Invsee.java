package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Invsee implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("invsee")){
            if(sender.hasPermission("rank.trusted")){
                if(args.length == 1){
                    Player p = (Player) sender;
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    p.openInventory(target.getInventory());
                }
                else if(args.length == 2 && args[1].equalsIgnoreCase("-e")){
                    Player p = (Player) sender;
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    p.openInventory(target.getEnderChest());
                }
            }
            else {
                sender.sendMessage("§cSorry! you do not have permission to use this command");
            }

        }  
        return true;  
    }
}
