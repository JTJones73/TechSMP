package tech.techsmp.core.Listeners;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class MineAlerts implements Listener{
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        boolean foundBlock = false;
    	boolean unexposed = true;
        Block block = event.getBlock();

        if (block.getLocation().getBlockY() <= 14 && (block.getType() == Material.STONE || block.getType() == Material.DEEPSLATE)) {
            for (Block adjacentBlock : getAdjacentBlocks(block)) {
                if ((adjacentBlock.getType() == Material.DIAMOND_ORE || adjacentBlock.getType() == Material.DEEPSLATE_DIAMOND_ORE)) {
                	foundBlock = true;
                    for (Block airBlock : getAdjacentBlocks(adjacentBlock)) {

                    	if (airBlock.getType() == Material.AIR && airBlock.getLocation() != block.getLocation()){
                    		unexposed = false;
                    		return;
                    	}
                    }
                    }
                }
            if(unexposed && foundBlock) {
        	for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("rank.trusted")) {
                    player.sendMessage("§a" + event.getPlayer().getName() + " §7=> §e§lUnexposed §bDiamond Ore §7[" + block.getLocation().getBlockX()
                    		+ ", " +  block.getLocation().getBlockY() + ", " +  block.getLocation().getBlockZ() + "]");
                }
            }
        	Logger.getLogger("Minecraft").info("§a" + event.getPlayer().getName() + " §7=> §e§lUnexposed §bDiamond Ore §7[" + block.getLocation().getBlockX()
            		+ ", " +  block.getLocation().getBlockY() + ", " +  block.getLocation().getBlockZ() + "]");
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
