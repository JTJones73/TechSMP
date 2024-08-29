/*
* Author:       James Jones
* Description:  X-Rayers really hate this because it alerts staff when they mine diamonds that are not exposed to air.
*               Granted they could look at this code and figure out how to bypass it but if someone is cheating in a
*               block game they probably do not have any marketable skills.
* */

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


        //If the player broke a stone (or similar) block
        if (block.getLocation().getBlockY() <= 14 && (block.getType() == Material.STONE || block.getType() == Material.DEEPSLATE ||
                block.getType() == Material.TUFF || block.getType() == Material.ANDESITE || block.getType() == Material.DIORITE || block.getType() == Material.ANDESITE)) {
            //Scan all adjacent blocks around the broken stone
            for (Block adjacentBlock : getAdjacentBlocks(block)) {
                //If one of the adjacent blocks is diamond
                if ((adjacentBlock.getType() == Material.DIAMOND_ORE || adjacentBlock.getType() == Material.DEEPSLATE_DIAMOND_ORE)) {
                	foundBlock = true;
                    //Scan all blocks adjacent to the diamond block
                    for (Block airBlock : getAdjacentBlocks(adjacentBlock)) {
                        //If the block is air then the diamond was exposed. If this check does not pass through all iterations then it is an unexposed ore
                    	if (airBlock.getType() == Material.AIR && airBlock.getLocation() != block.getLocation()){
                    		unexposed = false;
                    		return;
                    	}
                        //This code removes duplicate mining alerts (when someone would mine unexposed ores it would send two alerts)
                        //The better way to do this is to have a list of all mined diamonds and see if the vein has already been reported but this
                        //works better on a large world and does not need to be changed.
                        else if (airBlock.getType() == Material.DEEPSLATE_DIAMOND_ORE || airBlock.getType() == Material.DIAMOND_ORE) {
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
            //If the block is unexposed alert staff
            if(unexposed && foundBlock) {
        	for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("rank.trusted")) {
                    player.sendMessage(ConfigMessage.getMessage("MINEALERT_UNEXPOSED_ORE", new String[]{event.getPlayer().getName(),event.getBlock().getX() + "",event.getBlock().getY() + "",event.getBlock().getZ() + ""}));
                }
            }
            //Also send a discord webhook message
                DiscordWebhook.sendDiscordMsg("", "Cheat", event.getPlayer().getName() + " Unexposed Ore", "Unexposed Diamond Ore [" + block.getLocation().getBlockX()
                        + ", " +  block.getLocation().getBlockY() + ", " +  block.getLocation().getBlockZ() + "]");
                Bukkit.getConsoleSender().sendMessage(ConfigMessage.getMessage("MINEALERT_UNEXPOSED_ORE", new String[]{event.getPlayer().getName(),event.getBlock().getX() + "",event.getBlock().getY() + "",event.getBlock().getZ() + ""}));
            }
        }
    }
    /*
    * @Params:      block
    * @Returns:     block array - an array of all adjacent blocks
    * Description:  This returns an array of all adjacent blocks.
    * */
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
