package tech.techsmp.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.techsmp.core.Main;
import utils.ConfigMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homes implements CommandExecutor {
	File homes = new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "homes.yml");
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home")){
			String homeList = "";
			Player p = (Player) sender;
			int x = -1;
			int y = 106;
			int z = 1;
			String world = "";
			String homeName = "";
			int numHomes = 0;
			boolean noArgs = false;
			boolean foundHome = false;
			
			if(args.length == 0)
				noArgs = true;
			if(args.length > 1)
				sender.sendMessage(ConfigMessage.getMessage("HOME_ERROR_USAGE", new String[]{" "}));
			try {
				Scanner scanner = new Scanner(homes);
				while (scanner.hasNextLine()) {
					String Line = scanner.nextLine();
					if(Line.startsWith(p.getUniqueId().toString())) {
						numHomes++;
						String key = "";


						int i = p.getUniqueId().toString().length()+1;
						for(; Line.charAt(i) != '|'; i++) {
							key = key + Line.charAt(i);
						}
						homeList = homeList + key + " ";
						i++;
						homeName = key;
						key = "";
						for(; Line.charAt(i) != '|'; i++) {
							key = key + Line.charAt(i);
						}
						i++;
						world = key;
						key = "";
						for(; Line.charAt(i) != '|'; i++) {
							key = key + Line.charAt(i);
						}
						x = Integer.parseInt(key);
						key = "";
						i++;
						for(; Line.charAt(i) != '|'; i++) {
							key = key + Line.charAt(i);
						}
						y = Integer.parseInt(key);
						i++;
						key = "";
						for(; Line.charAt(i) != '|'; i++) {
							key = key + Line.charAt(i);

						}
						z = Integer.parseInt(key);
						if(!noArgs && args[0].equalsIgnoreCase(homeName)) {
							foundHome = true;
							break;
						}
					}
					//homesList = homesList + scanner.nextLine() + "\n";

				}
				if(numHomes >= 1 && args.length == 0) {
					p.sendMessage(ConfigMessage.getMessage("HOME_TELEPORTED", new String[]{homeName}));
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 255));
					p.teleport(new Location(Bukkit.getWorld(world), x, y , z));
				}
				else if(args.length == 1 && foundHome) {
					p.sendMessage("§aTeleporting...");
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 255));
					p.teleport(new Location(Bukkit.getWorld(world), x, y , z));	
				}
				else if(numHomes == 0)
					p.sendMessage(ConfigMessage.getMessage("HOME_TELEPORTED", new String[]{homeName}));
				else
					p.sendMessage(ConfigMessage.getMessage("HOME_COULD_NOT_FIND_HOME", new String[]{homeList}));
				scanner.close();
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			}


            
		}
		return true;
	}
	
	public List getPlayerHomes(Player p) {
        List<String> homesList = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(homes);
			while (scanner.hasNextLine()) {
				String Line = scanner.nextLine();
				if(Line.startsWith(p.getUniqueId().toString())) {
			        String key = "";


					int i = p.getUniqueId().toString().length()+1;
					for(; Line.charAt(i) != '|'; i++) {
						key = key + Line.charAt(i);
					}
					homesList.add(key);
				}
				//homesList = homesList + scanner.nextLine() + "\n";

			}
			scanner.close();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		return homesList;
	}

}
