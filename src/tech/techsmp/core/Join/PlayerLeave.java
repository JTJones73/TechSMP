package tech.techsmp.core.Join;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import utils.OnlineTime;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player p = event.getPlayer();
        OnlineTime.setActiveMs(p, (int) (PlayerPostJoin.totalActiveMs.get(p) + (System.currentTimeMillis() - PlayerPostJoin.logInMs.get(p))));
    }
}
