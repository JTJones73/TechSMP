package tech.techsmp.core.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class SleepingPercent implements Listener{
	static int numSleeping = 0;
	static int numSpecs = 0;
    @EventHandler
	public void bedEnter(PlayerBedEnterEvent e){
    	Player p = e.getPlayer();
		World w = Bukkit.getWorld("World");
    	if(p.isSleeping()) {
    		numSleeping = numSleeping + 1;
    		if(Bukkit.getOnlinePlayers().size() - ((Bukkit.getOnlinePlayers().size() - numSpecs) / 2) <= numSleeping) {
    			w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, -1);
    		}
    		else
    			w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 50);
    	}
    }
    @EventHandler
	public void bedLeave(PlayerBedLeaveEvent e){
    	Player p = e.getPlayer();
    	if(!p.isSleeping()) {
    		numSleeping = numSleeping - 1;
    	}
    }
    @EventHandler
	public void enterSpec(PlayerGameModeChangeEvent e){
    	Player p = e.getPlayer();
    	if(p.getGameMode().equals(GameMode.SURVIVAL) && (!e.getNewGameMode().equals(GameMode.SURVIVAL))){
    		World w = Bukkit.getWorld("World");
    		numSpecs++;
    		if(Bukkit.getOnlinePlayers().size() - ((Bukkit.getOnlinePlayers().size() - numSpecs) / 2) <= numSleeping) {
    			w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, -1);
    		}
    		else
    			w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 50);
    	}
    	if(!p.getGameMode().equals(GameMode.SURVIVAL) && (e.getNewGameMode().equals(GameMode.SURVIVAL))){
    		numSpecs--;
    		
    	}
    }

}
