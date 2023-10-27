package tech.techsmp.core.commands;

import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.OfflinePlayer;
import utils.ConfigMessage;

public class Verify implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Verify")) {
            if(sender.hasPermission("rank.trusted")){
                if(args.length == 1){
                	if(args[0].charAt(0) != '.') {
                		
                    try {
                        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(args[0]);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + p.getName());
                        if(p.isWhitelisted())
                        	sender.sendMessage(ConfigMessage.getMessage("VERIFY_PLAYER_VERIFIED", new String[]{p.getName()}));
                        else
                            sender.sendMessage(ConfigMessage.getMessage("VANISH_YOU_ARE_NOT_VANISHED", new String[]{" "}));

                    }
                    catch(Exception e){
                        sender.sendMessage(ConfigMessage.getMessage("VERIFY_ERROR_NO_PLAYER", new String[]{" "}));
                    }
                }
                	else{
                		try
    					{
    					    FileWriter fw = new FileWriter("/home/container/plugins/TechSMP/bedrock_whitelist.yml",true); //the true will append the new data
    					    fw.write(args[0] + "\n");//appends the string to the file
    					    sender.sendMessage(ConfigMessage.getMessage("VERIFY_BEDROCK_VERIFIED", new String[]{args[0]}));
    					    fw.close();
    					}
    					catch(IOException ioe)
    					{
    						ioe.printStackTrace();
    					}
                	}
                }
            }
            else
            	sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{}));

        }
        else{
            sender.sendMessage(ConfigMessage.getMessage("VERIFY_ERROR_USAGE", new String[]{" "}));
        }
        return true;
    }
}
