package tech.techsmp.core.Join;
import tech.techsmp.core.Main;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


public class PlayerPostJoin implements Listener{

    @EventHandler
	public void postJoinEvent(PlayerLoginEvent e){
        Player p = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            
            @Override
                public void run() {

                //redacted for privacy
                if(!p.isWhitelisted()){
                    p.sendMessage("§eWelcome! It looks like you are not verified but that's okay!\n§ePlease Join out discord to get verified at §ldiscord.ttumc.tech\n§r§aRules: §7be civil, no greifing, no cheating");
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
