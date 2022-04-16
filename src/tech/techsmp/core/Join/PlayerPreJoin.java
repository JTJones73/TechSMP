package tech.techsmp.core.Join;
import tech.techsmp.core.commands.Whitelist;


import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;



public class PlayerPreJoin implements Listener{
         
    @EventHandler
	public void preJoinEvent(AsyncPlayerPreLoginEvent e){
        OfflinePlayer p = Bukkit.getOfflinePlayer(e.getUniqueId());
        if(!p.isWhitelisted()){
            if(p.isBanned()){
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "§cYou are banned from this server. \n §ePlease message an admin on our discord to be given an unban date\n§Discord: discord.ttumc.net");
            }
            if(e.getAddress().toString().startsWith("/private")){
                e.allow();
                p.setWhitelisted(false);
                return;
            }
            Whitelist wl = new Whitelist();
            if(wl.isPlayerWhitelisted(e.getName().toString().toLowerCase())){
                e.allow();
                return;
            }
            else{
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "§cSorry! This is a whitelisted server please join our discord to request a whitelist\n §eIf you have a friend that is a member have them do /wl " + p.getName().toString() + " \n §7Note: If you join on the universities internet you are whitelisted\n§Discord: discord.ttumc.net");
            }

        }
    }
    
}
