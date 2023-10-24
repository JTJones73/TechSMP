package tech.techsmp.core.commands;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;
import utils.Teleporter;


public class Spec implements CommandExecutor {
	Vanish v = new Vanish();
	public static Map<Player, Location> specOnLocation = new HashMap<Player, Location>(10);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("rank.trusted")){
            sender.sendMessage(ConfigMessage.getMessage("NO_PERMS", new String[]{" "}));
            return true;
        }
        if(args.length == 1){

            if(sender instanceof Player){
                if(args[0].equalsIgnoreCase("on")){
                    Player p = (Player) sender;
                	if(p.getGameMode().equals(GameMode.SURVIVAL)) {
                		specOnLocation.put(p,p.getLocation());
                	}
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(ConfigMessage.getMessage("SPEC_YOU_ARE_SPEC", new String[]{" "}));
                    if(p.hasPermission("rank.trusted"))
                        p.performCommand("co i dontdothis");
                }
                if(args[0].equalsIgnoreCase("off")){
                    Player p = (Player) sender;
                    if(p.hasPermission("rank.admin")) {
                    	v.handleAdmin(p, true, false);
                    }
                    try {
                        if(isSafeLocation(specOnLocation.get(p))) {

                            Teleporter.teleport(p, specOnLocation.get(p));
                        }
                        else {
                            Teleporter.teleport(p, Bukkit.getWorld("world").getSpawnLocation());
                        }
                        //p.teleport(specOnLocation.get(p));
                        specOnLocation.remove(p);
                    }
                    catch(Exception ex) {

                        Teleporter.teleport(p, Bukkit.getWorld("world").getSpawnLocation());}
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(ConfigMessage.getMessage("SPEC_YOU_ARE_NOT_SPEC", new String[]{" "}));
                    if(p.hasPermission("rank.trusted"))
                        p.performCommand("co i dontdothis");
                }
            }
            else{
                sender.sendMessage(ConfigMessage.getMessage("SPEC_ERROR_USAGE", new String[]{" "}));
            }
        }
        else if(args.length == 2){
            try{
                Player p =  Bukkit.getServer().getPlayer(args[1]);
                if(args[0].equalsIgnoreCase("on")){
                	if(p.getGameMode().equals(GameMode.SURVIVAL) && !specOnLocation.containsKey(p)) {
                		specOnLocation.put(p,p.getLocation());
                	}
                    p.setGameMode(GameMode.SPECTATOR);
                    p.setPlayerListName(p.getDisplayName());
                    p.sendMessage(ConfigMessage.getMessage("SPEC_WELCOME", new String[]{" "}));
                    if(p.hasPermission("rank.trusted"))
                        p.performCommand("co i dontdothis");
                    sender.sendMessage(ConfigMessage.getMessage("SPEC_PUT_OTHER_IN_SPEC", new String[]{p.getName()}));
                }
                if(args[0].equalsIgnoreCase("off")){
                	try {

                        if(isSafeLocation(specOnLocation.get(p))) {

                            Teleporter.teleport(p, specOnLocation.get(p));
                        }
                        else {
                            Teleporter.teleport(p, Bukkit.getWorld("world").getSpawnLocation());
                        }
                		//p.teleport(specOnLocation.get(p));
                		specOnLocation.remove(p);
                	}
                	catch(Exception ex) {

                        Teleporter.teleport(p, Bukkit.getWorld("world").getSpawnLocation());}
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(ConfigMessage.getMessage("SPEC_YOU_ARE_NOT_SPEC", new String[]{" "}));
                    if(p.hasPermission("rank.trusted"))
                        p.performCommand("co i dontdothis");
                    sender.sendMessage(ConfigMessage.getMessage("SPEC_PUT_OTHER_OUT_SPEC", new String[]{p.getName()}));
                }
            }
            catch(Exception e){
                sender.sendMessage(ConfigMessage.getMessage("SPEC_ERROR_NO_PLAYER", new String[]{" "}));
            }
        }
        else{
            sender.sendMessage(ConfigMessage.getMessage("SPEC_ERROR_USAGE", new String[]{" "}));
        }
    
        return true;
    }
    public static boolean isSafeLocation(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return false;
        }

        int chunkX = location.getBlockX() >> 4;
        int chunkZ = location.getBlockZ() >> 4;
        if (!world.isChunkLoaded(chunkX, chunkZ)) {
            world.loadChunk(chunkX, chunkZ);
        }
        Block blockAtLocation = location.getBlock();
        Material blockType = blockAtLocation.getType();

        Material[] harmfulBlocks = {
                Material.LAVA,
                Material.FIRE,
        };

        for (Material harmfulBlock : harmfulBlocks) {
            if (blockType == harmfulBlock) {
                return false;
            }
        }

        Location belowLocation = location.clone().subtract(0, 1, 0);
        Block blockBelow = belowLocation.getBlock();
        Material blockBelowType = blockBelow.getType();

        if (blockBelowType == Material.AIR) {
            return false;
        }

        Location aboveLocation = location.clone().add(0, 1, 0);
        Block blockAbove = aboveLocation.getBlock();
        Material blockAboveType = blockAbove.getType();

        if (blockAboveType.isSolid()) {
            return false;
        }
        return true;
    }
}
