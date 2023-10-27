package tech.techsmp.core.Listeners;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import utils.ConfigMessage;
import utils.DiscordWebhook;


public class MineAlerts implements Listener{
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        boolean foundBlock = false;
    	boolean unexposed = true;
        Block block = event.getBlock();

        if (block.getLocation().getBlockY() <= 14 && (block.getType() == Material.STONE || block.getType() == Material.DEEPSLATE ||
                block.getType() == Material.TUFF || block.getType() == Material.ANDESITE || block.getType() == Material.DIORITE || block.getType() == Material.ANDESITE)) {
            for (Block adjacentBlock : getAdjacentBlocks(block)) {
                if ((adjacentBlock.getType() == Material.DIAMOND_ORE || adjacentBlock.getType() == Material.DEEPSLATE_DIAMOND_ORE)) {
                	foundBlock = true;
                    for (Block airBlock : getAdjacentBlocks(adjacentBlock)) {

                    	if (airBlock.getType() == Material.AIR && airBlock.getLocation() != block.getLocation()){
                    		unexposed = false;
                    		return;
                    	} else if (airBlock.getType() == Material.DEEPSLATE_DIAMOND_ORE || airBlock.getType() == Material.DIAMOND_ORE) {
                            for (Block  duplicateCheckBlock : getAdjacentBlocks(airBlock)) {

                                if (duplicateCheckBlock.getType() == Material.AIR && duplicateCheckBlock.getLocation() != block.getLocation()) {
                                    unexposed = false;
                                    return;
                                }
                            }
                        }
                    }
                    }
                }
            if(unexposed && foundBlock) {
        	for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("rank.trusted")) {
                    player.sendMessage(ConfigMessage.getMessage("MINEALERT_UNEXPOSED_ORE", new String[]{event.getPlayer().getName(),event.getBlock().getX() + "",event.getBlock().getY() + "",event.getBlock().getZ() + ""}));
                }
            }
                DiscordWebhook.sendDiscordMsg("", "Cheat", event.getPlayer().getName() + " Unexposed Ore", "Unexposed Diamond Ore [" + block.getLocation().getBlockX()
                        + ", " +  block.getLocation().getBlockY() + ", " +  block.getLocation().getBlockZ() + "]");
                Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("MINEALERT_UNEXPOSED_ORE", new String[]{event.getPlayer().getName(),event.getBlock().getX() + "",event.getBlock().getY() + "",event.getBlock().getZ() + ""}));
            }
        }
    }
    
    private Block[] getAdjacentBlocks(Block block) {
        return new Block[] {
            block.getRelative(0, 0, 1),
            block.getRelative(0, 0, -1),
            block.getRelative(0, 1, 0),
            block.getRelative(0, -1, 0),
            block.getRelative(1, 0, 0),
            block.getRelative(-1, 0, 0)
        };
    }
}
