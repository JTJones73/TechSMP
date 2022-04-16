package tech.techsmp.core;
import tech.techsmp.core.commands.Homes;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.server.TabCompleteEvent;

public class TabCompleter implements Listener{
	
	
	@SuppressWarnings("unchecked")
	@EventHandler                       
    public void onPlayerTeleport(TabCompleteEvent event){
		CommandSender s = event.getSender();
		String command = event.getBuffer();
        List<String> tabCompletions = new ArrayList<String>();
        if(!s.isOp()) {
			if(command.startsWith("/tp ")) {
				for(Player p : Bukkit.getOnlinePlayers()){
					tabCompletions.add(p.getName());
				}
				event.setCompletions(tabCompletions);
			}
		}
        if(command.startsWith("/spec ")) {
        	if((!command.contains("off") && !command.toLowerCase().contains("on"))) {
        		tabCompletions.clear();
        		tabCompletions.add("on");
        		tabCompletions.add("off");
        		event.setCompletions(tabCompletions);
        	}
        }
        else if(command.startsWith("/home ")) {
        	Player p = (Player) s;
        	Homes home = new Homes();
        	event.setCompletions(home.getPlayerHomes(p));
        	
        }
        
	}

}
