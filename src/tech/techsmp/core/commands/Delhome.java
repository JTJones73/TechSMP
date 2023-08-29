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

public class Delhome implements CommandExecutor {
	File homes = new File("/home/container/plugins/TechSMP/homes.yml");
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {	
			String homesList = "";
			Player p = (Player) sender;
			boolean hasHome = false;
			try {
				Scanner scanner = new Scanner(homes);
				while (scanner.hasNextLine()) {
					String Line = scanner.nextLine();
					if(Line.toLowerCase().startsWith(p.getUniqueId().toString() + "|" + args[0].toLowerCase() + "|")) {
						hasHome = true;
					}
					else {
						homesList = homesList + Line + "\n";
					}
				}
				scanner.close();
			}
			catch (FileNotFoundException exception) {
				exception.printStackTrace();
			}
			if(!hasHome) {
				sender.sendMessage("§cYou do not have a home by the name of " + args[0]);
			}
			else {
				try {
				    FileWriter fw = new FileWriter("/home/container/plugins/TechSMP/homes.yml", false); //the true will append the new data
				    fw.write(homesList);//appends the string to the file
				    p.sendMessage("§aSuccessfully deleted home named " + args[0]);
				    fw.close();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
		else
			sender.sendMessage("§cError usage: /delhome <home name>");
		return true;
	}

}
