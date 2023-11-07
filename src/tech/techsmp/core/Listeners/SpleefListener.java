/*
*   Author:         James Jones
*   Description:    The spleef arena is located near spawn and this listens for player to jump into the spleef arena when they do
*                   it saves their inventory, health, food level and gives them a diamond shovel while filling their health
*                   this permits them to break the snow which regenerates 4 seconds after breaking. When a player falls into the snow
*                   or gets "spleefed" it teleports them out of the arena, restores their inventory and health, and gives points to the one
*                   who spleefed them.
* */

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
import tech.techsmp.core.commands.Killboard;
import tech.techsmp.core.commands.Spleef;
import utils.ConfigMessage;
import utils.Teleporter;


public class SpleefListener implements Listener{
    public static Map<Location, Player> locToWhoBrokeSnow = new HashMap<>();        //used to figure out who spleefed who
    public static Map<Player, ItemStack[]> spleefInv = new HashMap<>();             //stores player's inventory before they are put in spleef mode
    public static Map<Player, ItemStack[]> spleefArmor = new HashMap<>();           //without nasty workarounds armor is stored seperately

    public static Map<Player, Double> spleefHealth = new HashMap<>();
    public static Map<Player, Integer> spleefHunger = new HashMap<>();
    public static Map<Player, Float> spleefSaturation = new HashMap<>();
    public static Map<Player, Integer> spleefStreak = new HashMap<>();

    public static Map<Player, Float> spleefExp = new HashMap<>();
    public static LinkedList<Player> spleefers = new LinkedList<>();

    //the on teleport event handler will detect the spleef arena teleporting them out at which it re teleports them out causing and infinite loop
    //this linked list fixes this by storing the player being teleported so it will not reteleport them
    public static LinkedList<Player> alreadyTeleporting = new LinkedList<>();           //the on teleport event handler will detect the spleef

    private LinkedList<Player> fallListener = new LinkedList<>();

    int spleefLayer = 100;
    public static Location spleefOffLocation = new Location(Bukkit.getWorld("world"), -106, 103.5, 29.5);
    public static boolean isSpleefEnabled = true;
    public static boolean broadcastSpleefs = false;
    public static LinkedList<Player> removeSpleeferDebounce = new LinkedList<>();



    /*
    *   Author:         James Jones
    *   Description:    Puts a player in spleef mode (use this function when a player jumps in the spleef arena)
    *   params:         Player: the player to put in spleef mode
    * */
    public void putPlayerInSpleef(Player p){

        if(!isSpleefEnabled){
            Teleporter.teleport(p,spleefOffLocation);
            p.sendMessage(ConfigMessage.getMessage("SPLEEF_SPLEEF_DISABLED", new String[]{" "}));
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
    /*
     *   Author:         James Jones
     *   Description:    Removes a player in spleef mode (use this function when a player fails or leaves the arena)
     *   params:         Player: the player to remove from spleef mode
     * */
    public static void removePlayerFromSpleef(Player p){
            try {                                               //for some reason if they are out of the arena this will through null pointer exception
                if(!alreadyTeleporting.contains(p)) {       //put them in the already teleporting if they are not so there is not an infinite loop
                    alreadyTeleporting.add(p);
                }

                //Wait 3 ticks and teleport them out while removing them from maps
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        spleefers.remove(p);
                        if(spleefStreak.containsKey(p))
                            spleefStreak.remove(p);
                        if(isEventInSpleefArena(p.getLocation())) {
                            Teleporter.teleport(p, spleefOffLocation);
                        }

                    }
                }.runTaskLater(Main.getInstance(), 3);

            }catch (Exception e){}


            //wait 3 more ticks (6 ticks after event) then restore the player's inventory, health, hunger, xp

            new BukkitRunnable() {
                @Override
                public void run() {
                    if(alreadyTeleporting.contains(p))
                        alreadyTeleporting.remove(p);
                    if(removeSpleeferDebounce.contains(p)) removeSpleeferDebounce.remove(p);
                    p.getInventory().setContents(spleefInv.get(p));

                    p.getInventory().setArmorContents(spleefArmor.get(p));
                    p.setHealth(spleefHealth.get(p));
                    p.setFoodLevel(spleefHunger.get(p));
                    p.setSaturation(spleefSaturation.get(p));
                    p.setExp(spleefExp.get(p));
                    spleefers.remove(p);

                    //remove all values for player
                    spleefInv.remove(p);
                    spleefSaturation.remove(p);
                    spleefHunger.remove(p);
                    spleefExp.remove(p);
                    spleefHealth.remove(p);

                }
            }.runTaskLater(Main.getInstance(), 6);
;


        }


    /*
     *   Author:         James Jones
     *   Description:    Returns whether or not a location is in the spleef arena
     *   params:         Location: The location to check
     *   return:         boolean: true if location is in spleef arena false if it is not
     * */
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
        if(spleefers.contains(e.getPlayer()) && !alreadyTeleporting.contains(e.getPlayer()))       //check if player is a spleefer and make sure they are not already teleporting (infinite loop fix)
            removePlayerFromSpleef(e.getPlayer());
    }

    @EventHandler
    public void onSnowBreak(BlockBreakEvent event) {
        /*
        * If spleefer breaks a snow block let them and regenerate after 4 seconds
        * */
        if(event.getBlock().getType().equals(Material.SNOW_BLOCK)){
            if(isEventInSpleefArena(event.getBlock().getLocation())){
                event.setDropItems(false);
                locToWhoBrokeSnow.put(event.getBlock().getLocation(), event.getPlayer());       //add the block and player so they can get kill credit

                // wait 4 seconds (80 ticks) then replace block
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            //replace the block after 5 seconds
                            event.getBlock().setType(Material.SNOW_BLOCK);
                            locToWhoBrokeSnow.remove(event.getPlayer());        //remove them from map so they do not get kill credit after its been replaced
                        }
                    }.runTaskLater(Main.getInstance(), 80);

            }
        }
        //if its a block besides snow or the player is not a spleefer and its in the spleef arena cancel it
        else if (isEventInSpleefArena(event.getBlock().getLocation())) {
                event.setCancelled(true);
        }
        else{       //do not allow spleefers to greif
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

    /*
    *   Author:         James Jones
    *   Description:    Bukkit does not have a player fall event so I created one by listening to the player move event
    *                   and check 1 tick later if they are lower than before which then calls playerFallEvent
    * */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        double playerY = p.getLocation().getY();
        if(!fallListener.contains(p)) {     //prevent this from getting overloaded

            fallListener.add(p);

            //wait 3 ticks and see if they are lower than before
            new BukkitRunnable() {
                @Override
                public void run() {

                    if (playerY > p.getLocation().getY()) {
                        playerFallEvent(p);     //if so call playerFallEvent
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
    /*
     *   Author:         James Jones
     *   Description:    Tell whether event is in spleef by seeing if location is in spleef or if player is a spleefer
     *   params:         Player: The player doing the event, Location: the location of event
     *   Return:         boolean: True if event is in spleef flase if it is not
     * */
    public static boolean isEventHappeningInSpleef(Player p, Location loc){
        if(spleefers.contains(p) || isEventInSpleefArena(loc))
            return true;
        return  false;
    }

    /*
    *   Author:         James Jones
    *   Description:    triggered when player is falling which puts them into spleef mode if they are falling into spleef
    *                   or if they are falling because they failed spleef it will remove them from spleef
    *
    * */
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
                            spleeferP.sendMessage();
                        }
                        else {
                            spleeferP.sendMessage(ConfigMessage.getMessage("SPLEEF_YOU_SPLEEFED", new String[]{p.getName()}));
                            spleeferP.playSound(spleeferP.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            p.sendMessage(ConfigMessage.getMessage("SPLEEF_YOU_GOT_SPLEEFED", new String[]{spleeferP.getName()}));
                            if(spleefStreak.containsKey(spleeferP)){
                                spleefStreak.replace(spleeferP, spleefStreak.get(spleeferP) + 1);
                            }
                            else{
                                spleefStreak.put(spleeferP, 1);
                            }
                        }
                        if(broadcastSpleefs) {
                            if (spleefStreak.containsKey(p)) {
                                Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_SPLEEF_BROADCAST", new String[]{p.getName(), spleeferP.getName(), spleefStreak.get(p) + ""}));
                                spleefStreak.remove(p);
                            } else
                                Bukkit.broadcastMessage(ConfigMessage.getMessage("SPLEEF_SPLEEF_BROADCAST", new String[]{p.getName(), spleeferP.getName(), 0 + ""}));
                        }
                        if(Spleef.spleefEvent.getEventRunning()){
                            Killboard.setScore(spleeferP, Killboard.getScore(spleeferP.getName()) + 1);
                        }
                    }
                    removeSpleeferDebounce.add(p);
                    //wait 1 ticks so it isnt annoying - players where complaining about it teleporting them out as soon as they fell 0.1 block so let them take in the failure first
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


    //It disables spleef
    public static void disableSpleef(){
        for(Player p: spleefers){
            removePlayerFromSpleef(p);
        }
        isSpleefEnabled = false;
    }

    //It enables spleef
    public static void enableSpleef(){
        isSpleefEnabled = true;
    }

}
