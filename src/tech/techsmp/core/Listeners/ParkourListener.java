package tech.techsmp.core.Listeners;


import java.util.*;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import tech.techsmp.core.Main;
import utils.ConfigMessage;
import utils.Teleporter;


public class ParkourListener implements Listener{
    public static LinkedList<Player> restrictList = new LinkedList<>();
    public static boolean inEvent = false;
    public static Map<Player, Integer> playerParkourTime = new HashMap<>();
    public static Map<String, Integer> completeTimes = new HashMap<>();

    LinkedList<Player> endDebounce = new LinkedList<>();
    boolean isTimerRunning = false;

    static Location parkourLocation = new Location(Bukkit.getWorld("world"), -86, 96, -102);
    Location startPlateLoc = new Location(Bukkit.getWorld("world"), -84, 96, -104);
    Location endPlateLoc = new Location(Bukkit.getWorld("world"), -65, 101, -106);
    static ArmorStand leaderBoard = null;

    @EventHandler
    public void onPlateCross(PlayerInteractEvent e){
        if(e.getClickedBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
            Bukkit.broadcastMessage(ChatColor.AQUA +  "" + e.getClickedBlock().getX() + "|" + e.getClickedBlock().getY() + "|" + e.getClickedBlock().getZ());
        }
        if(e.getClickedBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) && e.getClickedBlock().getLocation().equals(startPlateLoc)){
            Player p = (Player) e.getPlayer();
            Bukkit.broadcastMessage("starting");

            startTimer(p);
        }
        else if(e.getClickedBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) && e.getClickedBlock().getLocation().equals(endPlateLoc) && playerParkourTime.containsKey((Player) e.getPlayer())){
            Player p = (Player) e.getPlayer();
            if(!endDebounce.contains(p)) {
                endDebounce.add(e.getPlayer());
                addLeaderboardValue(p, playerParkourTime.get(p));
                Bukkit.broadcastMessage(ConfigMessage.getMessage("PARKOUR_SUCCESS", new String[]{p.getName(), playerParkourTime.get(p) / 1200 + "", ((playerParkourTime.get(p) / 20) % 60) / 10 + "" + ((playerParkourTime.get(p) / 20) % 60) % 10 + ""}));
                gameOverPlayer(p);
            }
        }
    }
    @EventHandler
    public void joinEvent(PlayerJoinEvent e){
        if(leaderBoard == null) {
            for (Entity en : Bukkit.getWorld("world").getEntities()) {
                if (en instanceof ArmorStand) {
                    if (en.getLocation().distance(startPlateLoc) < 5) {
                        initLeaderBoard(en);
                    }
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if(inEvent){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec on " + e.getPlayer().getName());
                    Teleporter.teleport(e.getPlayer(), parkourLocation);
                }
        }
        }.runTaskLater(Main.getInstance(), 3);

    }
    @EventHandler
    public void leaveEndPlate(PlayerMoveEvent e){
        if(endDebounce.contains(e.getPlayer()) && e.getPlayer().getLocation().distance(endPlateLoc) > 2) {
            endDebounce.remove(e.getPlayer());
        }
    }
    @EventHandler
    public void quitEvent(PlayerQuitEvent e){
        if(playerParkourTime.containsKey(e.getPlayer())){
            //Teleporter.teleport(e.getPlayer(), Bukkit.getWorld("world").getSpawnLocation());
            stopTimer(e.getPlayer());
        }
    }
    public static void initEvent(){
        inEvent = true;
        for(Player p: Bukkit.getOnlinePlayers()){

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spec on " + p.getName());
            Teleporter.teleport(p, parkourLocation);
        }
    }
    public void startTimer(Player p){
        if(playerParkourTime.containsKey(p)){
            playerParkourTime.remove(p);
        }
        playerParkourTime.put(p, 0);
        if(!isTimerRunning){
            isTimerRunning = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(playerParkourTime.isEmpty()){
                        isTimerRunning = false;
                        cancel();
                    }
                    for(Player timerPlayer: playerParkourTime.keySet()){
                        playerParkourTime.replace(timerPlayer, playerParkourTime.get(timerPlayer) + 1);
                        timerPlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ConfigMessage.getMessage("PARKOUR_TIMER", new String[]{playerParkourTime.get(timerPlayer) / 1200 + "",((playerParkourTime.get(p) / 20) % 60) / 10 + "" + ((playerParkourTime.get(p) / 20) % 60) % 10 + ""})));
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, 1);
        }
    }
    public static void initPlayer(Player p){
        p.teleport(parkourLocation);
        p.setGameMode(GameMode.SURVIVAL);
        //p.sendMessage(ConfigMessage.getMessage("PARKOUR_WELCOME", new String[]{" "}));
    }
    public void gameOverPlayer(Player p){
        if(inEvent){
            p.setGameMode(GameMode.SPECTATOR);
        }
        playerParkourTime.remove(p);
        p.sendMessage(ConfigMessage.getMessage("PARKOUR_GAMEOVER", new String[]{" "}));
    }

    public void stopTimer(Player p){
        playerParkourTime.remove(p);
    }
    public void initLeaderBoard(Entity en){
        leaderBoard = (ArmorStand) en;
        leaderBoard.setVisible(false);
        leaderBoard.setCustomNameVisible(true);
        leaderBoard.setGravity(false);
        leaderBoard.setCustomName(ConfigMessage.getMessage("PARKOUR_LEADERBOARD_HEADER", new String[]{" "}) + "\n" + ConfigMessage.getMessage("PARKOUR_LEADERBOARD_HEADER", new String[]{" "}));
    }
    public void addLeaderboardValue(Player p, int ticks){
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        completeTimes.put(p.getName(), ticks);
        for (Map.Entry<String, Integer> entry : completeTimes.entrySet()) {
            list.add(entry.getValue());
        }
        String leaderBoardText = ConfigMessage.getMessage("PARKOUR_LEADERBOARD_HEADER", new String[]{" "}) + "\n";
        Collections.sort(list);
        for (int num : list) {
            int numEntries = 0;
            for (Map.Entry<String, Integer> entry : completeTimes.entrySet()) {
                if (entry.getValue().equals(num)) {
                    if(numEntries == 10){
                        break;
                    }
                    String nameAndSpace = entry.getKey();
                    for(int i =0 ; i < 24 - entry.getKey().length(); i++){
                        nameAndSpace = nameAndSpace + " ";
                    }
                    leaderBoardText = leaderBoardText + nameAndSpace + num / 1200 + ":"+ ((num / 20) % 60 / 10) + "" + ((num / 20) % 60);
                    sortedMap.put(entry.getKey(), num);
                }
            }
            leaderBoard.setCustomName(leaderBoardText);
        }
    }

}
