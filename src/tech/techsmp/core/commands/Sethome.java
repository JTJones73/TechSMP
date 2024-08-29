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
import tech.techsmp.core.Main;
import utils.ConfigHandler;
import utils.ConfigMessage;

import javax.print.DocFlavor;

public class Sethome implements CommandExecutor {
	File homes = new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "homes.yml");
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
				if(p.getLocation().getWorld().getName().toLowerCase().contains("end")){
					sender.sendMessage(ConfigMessage.getMessage("SETHOME_NO_END_HOMES", new String[]{}));
					return true;
				}
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
					sender.sendMessage(ConfigMessage.getMessage("SETHOME_DUPLICATE_HOME", new String[]{args[0]}));
				}
				else if(numHomes >= ConfigHandler.getInt("home_NumHomes") && p.isWhitelisted()) {
					sender.sendMessage(ConfigMessage.getMessage("SETHOME_AT_HOME_LIMIT", new String[]{" "}));
				}
				else if(numHomes >= 1 && !p.isWhitelisted()) {
					sender.sendMessage((ConfigMessage.getMessage("SETHOME_GET_VERIFIED_FOR_MORE", new String[]{" "})));
				}
				else {
					try
					{
					    FileWriter fw = new FileWriter(new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "homes.yml"),true); //the true will append the new data
					    fw.write(p.getUniqueId().toString() + "|" + args[0].toLowerCase() + "|" + p.getLocation().getWorld().getName() + "|" + p.getLocation().getBlockX()+ "|" + p.getLocation().getBlockY()+ "|" + p.getLocation().getBlockZ() + "|\n");//appends the string to the file
					    p.sendMessage(ConfigMessage.getMessage("SETHOME_ADDED_HOME", new String[]{args[0]}));
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
