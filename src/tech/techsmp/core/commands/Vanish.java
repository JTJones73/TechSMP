package tech.techsmp.core.commands;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitRunnable;
import tech.techsmp.core.Join.PlayerPreJoin;
import tech.techsmp.core.Main;
import tech.techsmp.core.cosmetic.Chat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;

import java.util.LinkedList;



public class Vanish implements CommandExecutor {
	
	static LinkedList<Player> inVanish = new LinkedList<Player>();
	static LinkedList<Player> needLogonMsg = new LinkedList<Player>();


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(p.hasPermission("rank.trusted")) {
			if(isPlayerVanished(p)) {
				unVanishPlayer(p);
			}
			else {
				vanishPlayer(p);
			}
		}
		return true;

	}
	public void vanishPlayer(Player p) {
	    for(Player target : Bukkit.getOnlinePlayers()){
	        if(!target.hasPermission("rank.admin")) {
	        	target.hidePlayer(p);
	        }
	    }
	    inVanish.add(p);
	    p.setCollidable(false);

		new BukkitRunnable() {
			@Override
			public void run() {
				if(isPlayerVanished(p)){
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ConfigMessage.getMessage("VANISH_ACTION_BAR", new String[]{" "})));
				}
				else{
					cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 5);

	    p.sendMessage("§aYou are now in vanish.");
		Bukkit.getConsoleSender().sendMessage("§aPlayer " + p.getName() + " is now in vanish.");

	}
	public void unVanishPlayer(Player p) {
		if(isPlayerVanished(p)) {
		    for(Player target : Bukkit.getOnlinePlayers()){
		        	target.showPlayer(p);
		    }
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
		    p.setCollidable(true);
		    p.sendMessage(ConfigMessage.getMessage("VANISH_YOU_ARE_NOT_VANISHED", new String[]{" "}));
			Bukkit.getConsoleSender().sendMessage("§cPlayer " + p.getName() + " is no longer in vanish.");
	        if(needLogonMsg.contains(p)) {
	    	    for(Player lPlayer : Bukkit.getOnlinePlayers()){
	    	    	lPlayer.sendMessage("§7(§a+§7) §a" + p.getName());
	    	    }
		        needLogonMsg.remove(p);

	        }
	        inVanish.remove(p);
		}
	}
	public void handleVisibility(Player p) {
		if(p.hasPermission("rank.admin")) {
		    for(Player target : Bukkit.getOnlinePlayers()){
	        	target.showPlayer(p);
	    }
		}
	    else {
			    for(Player target : Bukkit.getOnlinePlayers()){
			    	if(isPlayerVanished(target)) {
		        	target.hidePlayer(p);
			    	}
			    	else {
			        	target.showPlayer(p);

			    	}
		    }
	    }
	}
	//returns true if log out messages are needed	set removeFromSpec to false to put admin in vanish on join
	public boolean handleAdmin(Player p, boolean removeFromSpec, boolean isLeaving) {
		if(removeFromSpec && p.hasPermission("rank.admin") && inVanish.contains(p) && needLogonMsg.contains(p)) {
			unVanishPlayer(p);
			return true;
		}
		if(isLeaving && !needLogonMsg.contains(p)) {
			return true;
		}
		if(!removeFromSpec && isLeaving == false) {
			needLogonMsg.add(p);
			vanishPlayer(p);
			return false;
		}
		return false;
	}
	public boolean isPlayerVanished(Player p) {
		return inVanish.contains(p);
	}
}
