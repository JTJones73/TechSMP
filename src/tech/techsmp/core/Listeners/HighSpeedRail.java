package tech.techsmp.core.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import tech.techsmp.core.Main;

public class HighSpeedRail implements Listener {

    @EventHandler
    public void cartRightClickEvent(PlayerInteractEntityEvent e){
        if(e.getRightClicked() instanceof Minecart){

            if(e.getPlayer().getItemInHand().getType().equals(Material.GOLDEN_HOE)){
                Minecart m = (Minecart) e.getRightClicked();
                if(!m.hasMetadata("highspeed"))
                    m.setMetadata("highspeed", new FixedMetadataValue(Main.getInstance(), true));
                else
                    m.removeMetadata("highspeed", Main.getInstance());
            }
        }

    }

    @EventHandler
    public void cartMove(VehicleMoveEvent e){
        if(e.getVehicle() instanceof  Minecart){
            Minecart m = (Minecart) e.getVehicle();
            if(m.hasMetadata("highspeed")){
                m.setMaxSpeed(1.0D);
            }
            else if(e.getVehicle().getPassengers().size() > 0 && e.getVehicle().getPassengers().get(0) instanceof Player){
                Material i = ((Player)m.getPassengers().get(0)).getItemInHand().getType();
                if(i.equals(Material.WOODEN_HOE) || i.equals(Material.STONE_HOE) || i.equals(Material.IRON_HOE) || i.equals(Material.GOLDEN_HOE) || i.equals(Material.DIAMOND_HOE)){
                    m.setMaxSpeed(1.0D);
                }
                else
                    m.setMaxSpeed(0.4D);
            }
            else
                m.setMaxSpeed(0.4D);
        }
    }

}
