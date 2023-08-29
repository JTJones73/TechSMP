package tech.techsmp.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKCheck implements Listener {
    public static Map<UUID, Long> lastActivity = new HashMap<>();
    static long afkThreshold = 5 * 60 * 1000; // 5 minutes in milliseconds
    public static boolean isPlayerAFK(Player player) {
        Long lastAction = lastActivity.get(player.getUniqueId());
        if (lastAction == null) {
            return true; // Player has no recorded activity, so consider them AFK
        }

        long currentTime = System.currentTimeMillis();
        return currentTime - lastAction > afkThreshold;
    }






    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
    }




}