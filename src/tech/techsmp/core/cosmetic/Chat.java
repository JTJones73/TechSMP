package tech.techsmp.core.cosmetic;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import utils.ConfigMessage;


public class Chat implements Listener{
	public static ArrayList<String> Muted= new ArrayList<String>();

        
    @EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {

        String message = e.getMessage().replaceAll("%", "%%");
        Player p = e.getPlayer();
    	if(Muted.contains(p.getName().toLowerCase())){
    		p.sendMessage(ConfigMessage.getMessage("CHAT_ERROR_MUTED", new String[]{" "}));
            Bukkit.getConsoleSender().sendMessage("[Muted] " + p.getName() +": " + e.getMessage());
            e.setCancelled(true);
            return;
    	}
        if(p.hasPermission("rank.owner") && p.hasPermission("rank.owner")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(ConfigMessage.getMessage("CHAT_OWNER_CHAT", new String[]{p.getName(), message}));
            }
            Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("CHAT_OWNER_CHAT", new String[]{p.getName(), message}));

        }
        else if(p.hasPermission("rank.admin")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(ConfigMessage.getMessage("CHAT_ADMIN_CHAT", new String[]{p.getName(), message}));
            }
            Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("CHAT_ADMIN_CHAT", new String[]{p.getName(), message}));

        }
        else if(p.hasPermission("rank.trusted")){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(ConfigMessage.getMessage("CHAT_TRUSTED_CHAT", new String[]{p.getName(), message}));
            }
            Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("CHAT_TRUSTED_CHAT", new String[]{p.getName(), message}));

        }
        else if(p.isWhitelisted()){
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(ConfigMessage.getMessage("CHAT_MEMBER_CHAT", new String[]{p.getName(), message}));
            }
            Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("CHAT_MEMBER_CHAT", new String[]{p.getName(), message}));

        }
        else{
        	e.setCancelled(true);
        	for(Player iPlayer : Bukkit.getOnlinePlayers()){
            	iPlayer.sendMessage(ConfigMessage.getMessage("CHAT_GUEST_CHAT", new String[]{p.getName(), message}));
            }
            Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("CHAT_GUEST_CHAT", new String[]{p.getName(), message}));


        }
    }
}