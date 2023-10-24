package tech.techsmp.core.Listeners;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import tech.techsmp.core.Main;
import utils.Teleporter;


public class SpleefListener implements Listener{
    public static Map<Location, Player> locToWhoBrokeSnow = new HashMap<>();        //used to figure out who spleefed who
    public static Map<Player, ItemStack[]> spleefInv = new HashMap<>();
    public static Map<Player, ItemStack[]> spleefArmor = new HashMap<>();

    public static Map<Player, Double> spleefHealth = new HashMap<>();
    public static Map<Player, Integer> spleefHunger = new HashMap<>();
    public static Map<Player, Float> spleefSaturation = new HashMap<>();
    public static Map<Player, Float> spleefExp = new HashMap<>();
    public static LinkedList<Player> spleefers = new LinkedList<>();
    private LinkedList<Player> fallListener = new LinkedList<>();

    int spleefLayer = 100;
    public static Location spleefOffLocation = new Location(Bukkit.getWorld("world"), -106, 103.5, 29.5);
    public static boolean isSpleefEnabled = true;
    public static LinkedList<Player> removeSpleeferDebounce = new LinkedList<>();


    public void putPlayerInSpleef(Player p){

        if(!isSpleefEnabled){
            Teleporter.teleport(p,spleefOffLocation);
            p.sendMessage(ChatColor.AQUA + "Sorry but spleef has been disabled if your believe this is an error please ask an admin to enable it");
            return;
        }

        //save the spleefers data
        if(!spleefInv.containsKey(p) && !spleefers.contains(p)) {             //should always be true unless something has gone seriously wrong

            spleefInv.put(p, p.getInventory().getContents());
            spleefHealth.put(p, p.getHealth());
            spleefHunger.put(p, p.getFoodLevel());
            spleefSaturation.put(p, p.getSaturation());
            spleefExp.put(p, p.getExp());
            spleefArmor.put(p, p.getInventory().getArmorContents());

            spleefers.add(p);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            ItemStack shovel = new ItemStack(Material.DIAMOND_SHOVEL);
            ItemMeta itemMeta = shovel.getItemMeta();
            itemMeta.setUnbreakable(true);
            itemMeta.setDisplayName("§6Spleef");

            List<String> lore = new ArrayList<>();
            lore.add("§3Spleef your opponents by breaking");
            lore.add("§3the snow blocks underneath them!");
            itemMeta.setLore(lore);
            shovel.setItemMeta(itemMeta);

            p.getInventory().setItem(0, shovel);
            p.getInventory().setHeldItemSlot(0);

            new BukkitRunnable() {                              //every 10 ticks make sure player didnt glitch out
                @Override
                public void run() {
                    if(!isEventInSpleefArena(p.getLocation()) && spleefers.contains(p)){
                        removePlayerFromSpleef(p);
                        cancel();
                    } else if (!spleefers.contains(p)) {
                        cancel();
                    }

                }
            }.runTaskTimer(Main.getInstance(), 0, 10);
        }
        else{
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error: SpleefListener in putPlayerInSpleef spleefInv already exists for player " + p.getName());
        }

    }
    public static void removePlayerFromSpleef(Player p){
            try {                                               //for some reason if they are out of the arena this will through null pointer exception
                if(isEventInSpleefArena(p.getLocation())) {
                    Teleporter.teleport(p, spleefOffLocation);
                }
            }catch (Exception e){



            new BukkitRunnable() {
                @Override
                public void run() {
                    if(removeSpleeferDebounce.contains(p)) removeSpleeferDebounce.remove(p);
                    p.getInventory().setContents(spleefInv.get(p));

                    p.getInventory().setArmorContents(spleefArmor.get(p));
                    p.setHealth(spleefHealth.get(p));
                    p.setFoodLevel(spleefHunger.get(p));
                    p.setSaturation(spleefSaturation.get(p));
                    p.setExp(spleefExp.get(p));
                    spleefers.remove(p);

                }
            }.runTaskLater(Main.getInstance(), 3);
;

            //remove all values for player
            spleefInv.remove(p);
            spleefSaturation.remove(p);
            spleefHunger.remove(p);
            spleefExp.remove(p);
            spleefHealth.remove(p);

        }
    }
    public static boolean isEventInSpleefArena(Location loc){
        if(loc.getBlockX() <= -109 && loc.getBlockX() >= -143){
            if(loc.getBlockY() <= 102 && loc.getBlockY() >= 97){
                if(loc.getBlockZ() <= 46 && loc.getBlockZ() >= 12){
                    return true;
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {       //Prevent them from teleporting out with cmds or epearls
        if(spleefers.contains(e.getPlayer())) removePlayerFromSpleef(e.getPlayer());
    }
    @EventHandler
    public void onSnowBreak(BlockBreakEvent event) {
        if(event.getBlock().getType().equals(Material.SNOW_BLOCK)){
            if(isEventInSpleefArena(event.getBlock().getLocation())){
                event.setDropItems(false);
                locToWhoBrokeSnow.put(event.getBlock().getLocation(), event.getPlayer());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            //replace the block after 5 seconds
                            event.getBlock().setType(Material.SNOW_BLOCK);
                            locToWhoBrokeSnow.remove(event.getPlayer());
                        }
                    }.runTaskLater(Main.getInstance(), 80);

            }
        } else if (isEventInSpleefArena(event.getBlock().getLocation())) {
                event.setCancelled(true);
        }
        else{
            if(spleefers.contains(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onSpleefPvp(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player) {
            if (isEventHappeningInSpleef((Player) e.getDamager(), e.getDamager().getLocation())) {
                e.setCancelled(true);
            }
        }
        if(e.getEntity() instanceof Player) {
            if (isEventHappeningInSpleef((Player) e.getEntity(), e.getEntity().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onSpleefDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof  Player){
            if(isEventHappeningInSpleef((Player) e.getEntity(), e.getEntity().getLocation())){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onSpleeferQuit(PlayerQuitEvent e){
        if(spleefers.contains(e.getPlayer())){
            removePlayerFromSpleef(e.getPlayer());
        }
    }
    @EventHandler
    public void onSpleefMobSpawn(EntitySpawnEvent e){
        if(isEventInSpleefArena(e.getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onSpleefHunger(FoodLevelChangeEvent e){
        if(isEventHappeningInSpleef((Player) e.getEntity(), e.getEntity().getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        double playerY = p.getLocation().getY();
        if(!fallListener.contains(p)) {

            fallListener.add(p);
            new BukkitRunnable() {
                @Override
                public void run() {

                    if (playerY > p.getLocation().getY()) {
                        playerFallEvent(p);
                    }
                    fallListener.remove(p);
                }
            }.runTaskLater(Main.getInstance(), 3);
        }
    }
    @EventHandler
    public void onSpleeferDropItem(PlayerDropItemEvent e){
        if(isEventInSpleefArena(e.getPlayer().getLocation())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onSpleeferPickupItem(PlayerPickupItemEvent e){
        if(isEventInSpleefArena(e.getPlayer().getLocation())){
            e.setCancelled(true);
        }
    }
    public static boolean isEventHappeningInSpleef(Player p, Location loc){
        if(spleefers.contains(p) || isEventInSpleefArena(loc))
            return true;
        return  false;
    }

    //Triggered when a player falls
    public void playerFallEvent(Player p){
        if(isEventInSpleefArena(p.getLocation())){
            if(!spleefers.contains(p)){     //if falling into spleef
                if(p.getLocation().getY() > 101.1 && p.getGameMode().equals(GameMode.SURVIVAL)){
                    putPlayerInSpleef(p);
                }
            }
            else{       //if falling out of spleef taking the L
                if(p.getLocation().getY() < 100.9 && p.getGameMode().equals(GameMode.SURVIVAL) && !removeSpleeferDebounce.contains(p)){
                    if(locToWhoBrokeSnow.containsKey(new Location(p.getLocation().getWorld(), p.getLocation().getBlockX(), 100, p.getLocation().getBlockZ()))){
                        Player spleeferP = locToWhoBrokeSnow.get(new Location(p.getLocation().getWorld(), p.getLocation().getBlockX(), 100, p.getLocation().getBlockZ()));
                        if(spleeferP.getName().equals(p.getName())){
                            spleeferP.sendMessage(ChatColor.AQUA + "You spleefed yourself Derp!");
                        }
                        else {
                            spleeferP.sendMessage(ChatColor.AQUA + "You spleefed " + p.getName());
                            spleeferP.playSound(spleeferP.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            p.sendMessage(ChatColor.AQUA + "You got spleefed by " + spleeferP.getName());
                        }
                    }
                    removeSpleeferDebounce.add(p);
                    //wait 1 ticks so it isnt annoying
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            removePlayerFromSpleef(p);
                        }
                    }.runTaskLater(Main.getInstance(), 1);

                }
            }
        }
    }

    public static void disableSpleef(){
        for(Player p: spleefers){
            removePlayerFromSpleef(p);
        }
        isSpleefEnabled = false;
    }
    public static void enableSpleef(){
        isSpleefEnabled = true;
    }

}
