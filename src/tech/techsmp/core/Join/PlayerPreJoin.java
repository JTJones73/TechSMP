package tech.techsmp.core.Join;
import tech.techsmp.core.commands.Whitelist;


import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.EventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerPreJoin implements Listener{
	public static ArrayList<String> bedrockWhitelist= new ArrayList<String>();
	public static Map<String, String> banList = new HashMap<String, String>(10);

    @EventHandler
	public void preJoinEvent(AsyncPlayerPreLoginEvent e){
        Whitelist wl = new Whitelist();
    	File bedrockWl = new File("/home/container/plugins/TechSMP/bedrock_whitelist.yml");
    	if(banList.containsKey(e.getName())) {
    		e.disallow(Result.KICK_BANNED, "§cYou are banned from this server. \n §7Reason: " + PlayerPreJoin.banList.get(e.getName()) + "\n §ePlease message an admin on our discord to be given an unban date\n§7Discord: discord.ttumc.net");
    		return;
    	}
   

    	if(e.getName().charAt(0) == '.') {
    		try {
    			OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(e.getUniqueId());
    			if(p.isWhitelisted()) {
    				e.allow();
    				return;
    			}
    		}
    		catch(Exception exception) {
    			
    		}
    		boolean allowedJoin = false;
            if(wl.isPlayerWhitelisted(e.getName().toString().toLowerCase())){
                e.allow();
                return;
            }

            else {
    			try {
    				Scanner scanner = new Scanner(bedrockWl);
    				while (scanner.hasNextLine()) {
    					String Line = scanner.nextLine();
    					if(Line.startsWith(e.getName())) {
    							e.allow();
    							bedrockWhitelist.add(e.getName());
    							allowedJoin = true;
    							return;
    					}
    				}
					scanner.close();


				} catch (FileNotFoundException exception) {
    				exception.printStackTrace();
    			}

    		if(!allowedJoin) {
                if(e.getAddress().toString().startsWith("/149.149.2.")){
                    e.allow();
                    return;
                }
                else
                	e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "§cSorry! This is a whitelisted server please join our discord to request a whitelist\n §eIf you have a friend that is a member have them do /wl " + e.getName().toString() + " \n §7Note: If you join on the universities internet you are whitelisted\n§7Discord: discord.ttumc.net");
    		}

            }

    	}
        OfflinePlayer p = Bukkit.getOfflinePlayer(e.getUniqueId());
        if(!p.isWhitelisted()){
            if(p.isBanned()){
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "§cYou are banned from this server. \n §ePlease message an admin on our discord to be given an unban date\n§7Discord: discord.ttumc.net");
            }
            if(e.getAddress().toString().startsWith("/149.149.2.")){
                e.allow();
                //p.setWhitelisted(false);
                return;
            }
            if(wl.isPlayerWhitelisted(e.getName().toString().toLowerCase())){
                e.allow();
                return;
            }
            else{
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "§cSorry! This is a whitelisted server please join our discord to request a whitelist\n §eIf you have a friend that is a member have them do /wl " + e.getName() + " \n §7Note: If you join on the universities internet you are whitelisted\n§7Discord: discord.ttumc.net");
            }

        }
    }
    
}
