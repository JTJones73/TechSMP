package tech.techsmp.core.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sethome implements CommandExecutor {
	File homes = new File("/home/container/plugins/TechSMP/homes.yml");
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int numHomes = 0;
		boolean duplicateHome = false;
		
		if (cmd.getName().equalsIgnoreCase("sethome")){
			if(args.length == 1 && args[0].contains("|")) {
				sender.sendMessage("§cInvaid character /sethome <home name>");
				return true;
			}
			if(args.length == 1 && sender instanceof Player) {
				Player p = (Player) sender;
				try {
					Scanner scanner = new Scanner(homes);
					while (scanner.hasNextLine()) {
						String Line = scanner.nextLine();
						if(Line.startsWith(p.getUniqueId().toString() + "|" + args[0])) {
							duplicateHome = true;
							break;
						}
						else if(Line.startsWith(p.getUniqueId().toString())) {
							numHomes++;
						}
					}
					scanner.close();
				}
				catch (FileNotFoundException exception) {
					exception.printStackTrace();
				}
				if(duplicateHome) {
					sender.sendMessage("§cYou already have a home by that name do /delhome " + args[0] + " to delete that home");
				}
				else if(numHomes >=3 && p.isWhitelisted()) {
					sender.sendMessage("§cSorry but there is a limit of three homes per player please use /delhome <home name> to remove a home");
				}
				else if(numHomes >= 1 && !p.isWhitelisted()) {
					sender.sendMessage("§cSorry but there is a limit of one home /delhome <home name> to remove a home or join our discord to get this limit increased to 3");
				}
				else {
					try
					{
					    FileWriter fw = new FileWriter("/home/container/plugins/TechSMP/homes.yml",true); //the true will append the new data
					    fw.write(p.getUniqueId().toString() + "|" + args[0].toLowerCase() + "|" + p.getLocation().getWorld().getName() + "|" + p.getLocation().getBlockX()+ "|" + p.getLocation().getBlockY()+ "|" + p.getLocation().getBlockZ() + "|\n");//appends the string to the file
					    p.sendMessage("§aSuccessfully set home named " + args[0]);
					    fw.close();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}

				}
				}
		}
		return true;
	}

}
