package tech.techsmp.core.Join;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitRunnable;
import tech.techsmp.core.Main;
import tech.techsmp.core.Join.PlayerPreJoin;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import tech.techsmp.core.commands.Vanish;
import utils.ConfigMessage;


public class PlayerPostJoin implements Listener{

    @EventHandler
	public void postJoinEvent(PlayerLoginEvent e){
        Player p = e.getPlayer();

		new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.hasPermission("rank.admin")){
					Vanish v = new Vanish();
					for(Player vanishedPlayer: Bukkit.getOnlinePlayers()){
						if(v.isPlayerVanished(vanishedPlayer)){
							p.hidePlayer(vanishedPlayer);
						}
					}
				}
			}
		}.runTaskLater(Main.getInstance(),5);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

            @Override
                public void run() {
            	if(p.isOnline() && PlayerPreJoin.bedrockWhitelist.contains(p.getName())) {
            		p.setWhitelisted(true);
            		String wlList = "";
        			try {
        		    	File bedrockWl = new File("/home/container/plugins/TechSMP/bedrock_whitelist.yml");

        				Scanner scanner = new Scanner(bedrockWl);
        				while (scanner.hasNextLine()) {
        					String Line = scanner.nextLine();
        					if(!Line.toLowerCase().startsWith(p.getName())) {
        						wlList = wlList + Line + "\n";
        					}
        				}
        				scanner.close();
        			}
        			catch (FileNotFoundException exception) {
        				exception.printStackTrace();
        			}
        				try {
        				    FileWriter fw = new FileWriter("/home/container/plugins/TechSMP/bedrock_whitelist.yml", false); //the true will append the new data
        				    fw.write(wlList);//appends the string to the file
        				    fw.close();
        				}
        				catch(IOException ioe)
        				{
        					ioe.printStackTrace();
        				}
        			
            	}
		    	File ranks = new File("/home/container/plugins/TechSMP/ranks.yml");

            	try {
    				Scanner scanner = new Scanner(ranks);
    				while (scanner.hasNextLine()) {
    					String Line = scanner.nextLine();
    					if(Line.startsWith(p.getUniqueId().toString() + "|")) {
    						String rankName = "";
    						for(int i = p.getUniqueId().toString().length() + 1; i < Line.length(); i++) {
    							rankName = rankName + Line.charAt(i);
    						}
    	                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rank " + p.getName() +  " " + rankName);

    					}
    				}
    				scanner.close();


    			} catch (FileNotFoundException exception) {
    				exception.printStackTrace();
    			}


                if(!p.isWhitelisted()){
                    p.sendMessage(ConfigMessage.getMessage("JOIN_NONVERIFIED_JOIN", new String[]{" "}));
                }
                }
            }, 10L);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

                @Override
                    public void run() {
                        p.setPlayerListHeader("§eWelcome to the §5§lTN Tech §7Minecraft server");
                        p.setPlayerListFooter("§6Griefing is bannable (we have block logging)\n§eJoin our discord: discord.ttumc.tech\n§aCommands: §7§o/help /tpa /wl /bedtp");
                        
                    }
                }, 10L);

    }
    
}
