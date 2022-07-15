package tech.techsmp.core.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;


public class ArmorStandListener implements Listener{
	ArrayList<String> booleanPhysicsLore = new ArrayList<String>();
	ArrayList<String> booleanBasePlateLore = new ArrayList<String>();
	ArrayList<String> booleanSmallLore = new ArrayList<String>();
	ArrayList<String> booleanArmsLore = new ArrayList<String>();
	ArrayList<String> infoBookLore = new ArrayList<String>();
	ArrayList<String> categoryHeadLore = new ArrayList<String>();
	ArrayList<String> categoryBodyLore = new ArrayList<String>();
	ArrayList<String> categoryLeftArmLore = new ArrayList<String>();
	ArrayList<String> categoryRightArmLore = new ArrayList<String>();
	ArrayList<String> categoryLeftLegLore = new ArrayList<String>();
	ArrayList<String> categoryRightLegLore = new ArrayList<String>();
	ArrayList<String> headPitchLore = new ArrayList<String>();
	ArrayList<String> headYawLore = new ArrayList<String>();
	ArrayList<String> headTiltLore = new ArrayList<String>();
	ArrayList<String> bodyPitchLore = new ArrayList<String>();
	ArrayList<String> bodyYawLore = new ArrayList<String>();
	ArrayList<String> bodyTiltLore = new ArrayList<String>();
	ArrayList<String> leftArmPitchLore = new ArrayList<String>();
	ArrayList<String> leftArmYawLore = new ArrayList<String>();
	ArrayList<String> leftArmTiltLore = new ArrayList<String>();
	ArrayList<String> rightArmPitchLore = new ArrayList<String>();
	ArrayList<String> rightArmYawLore = new ArrayList<String>();
	ArrayList<String> leftLegPitchLore = new ArrayList<String>();
	ArrayList<String> rightArmTiltLore = new ArrayList<String>();
	ArrayList<String> leftLegYawLore = new ArrayList<String>();
	ArrayList<String> leftLegTiltLore = new ArrayList<String>();
	ArrayList<String> rightLegPitchLore = new ArrayList<String>();
	ArrayList<String> rightLegYawLore = new ArrayList<String>();
	ArrayList<String> rightLegTiltLore = new ArrayList<String>();
	
	static Map<Player, ArmorStand> armorStandEditor = new HashMap<Player, ArmorStand>(10);
	

    @EventHandler
	public void onHoeClick(PlayerInteractAtEntityEvent event) {
    	Bukkit.getLogger().info("interact");
    	if(event.getRightClicked() instanceof ArmorStand) {

    		Player p = event.getPlayer();
    		if(p.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)) {

    			armorStandEditor.put(p, (ArmorStand) event.getRightClicked());
    			GuiListener.inGui.add(p);
    			
                Inventory inv = Bukkit.getServer().createInventory(null, 45, "ArmorStand Editor");
                
                booleanPhysicsLore.add(0, "§7Toggles armor stand physics");
                booleanPhysicsLore.add(1, "§7When disabled the armor stand will not be effected by gravity or collision");
                booleanBasePlateLore.add(0, "§7Toggles base plate visibility");
                booleanBasePlateLore.add(1, "§7When disabled the armor stand will appear to be free standing");
                booleanSmallLore.add(0, "§7Toggles the size of the armor stand");
                booleanSmallLore.add(1, "§7Small is about half the size of a regular armor stand");
                booleanArmsLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                booleanArmsLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                infoBookLore.add(0, "§7Completely resets the position of the armor stand");
                infoBookLore.add(1, "§cWarning: you might not want to do this.");
                
                ItemStack booleanPhysics = new ItemStack(Material.FEATHER);
                ItemStack booleanBasePlate = new ItemStack(Material.SMOOTH_STONE_SLAB);
                ItemStack booleanSmall = new ItemStack(Material.REPEATER);
                ItemStack booleanArms = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack infoBook = new ItemStack(Material.BOOK);
                

            	ItemMeta booleanPhysicsMeta = booleanPhysics.getItemMeta();
            	ItemMeta booleanBasePlateMeta = booleanBasePlate.getItemMeta();
            	ItemMeta booleanArmsMeta = booleanArms.getItemMeta();
            	ItemMeta booleanSmallMeta = booleanSmall.getItemMeta();
            	ItemMeta infoBookMeta = infoBook.getItemMeta();
            	
                booleanPhysicsMeta.setDisplayName("§aPhysicis (Off/On)");
                booleanPhysicsMeta.setLore(booleanPhysicsLore);
                booleanBasePlateMeta.setDisplayName("§8Base Plate (Visisble/Hidden)");
                booleanBasePlateMeta.setLore(booleanBasePlateLore);
                booleanArmsMeta.setDisplayName("§bArms (Enabled/Disabled)");
                booleanArmsMeta.setLore(booleanArmsLore);
                booleanSmallMeta.setDisplayName("§cSize (Small/Large)");
                booleanSmallMeta.setLore(booleanSmallLore);
                infoBookMeta.setDisplayName("§c§lReset");
                infoBookMeta.setLore(infoBookLore);
            	
                booleanPhysics.setItemMeta(booleanPhysicsMeta);
                booleanBasePlate.setItemMeta(booleanBasePlateMeta);
                booleanSmall.setItemMeta(booleanSmallMeta);
                booleanArms.setItemMeta(booleanArmsMeta);
                infoBook.setItemMeta(infoBookMeta);
                
                categoryHeadLore.add(0, "§7Toggles armor stand physics");
                categoryHeadLore.add(1, "§7When disabled the armor stand will not be effected by gravity or collision");
                categoryBodyLore.add(0, "§7Toggles base plate visibility");
                categoryBodyLore.add(1, "§7When disabled the armor stand will appear to be free standing");
                categoryLeftArmLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                categoryLeftArmLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                categoryRightArmLore.add(0, "§7Completely resets the position of the armor stand");
                categoryRightArmLore.add(1, "§cWarning: you might not want to do this.");
                categoryRightLegLore.add(0, "§7Completely resets the position of the armor stand");
                categoryRightLegLore.add(1, "§cWarning: you might not want to do this.");
                categoryLeftLegLore.add(0, "§7Completely resets the position of the armor stand");
                categoryLeftLegLore.add(1, "§cWarning: you might not want to do this.");
                
                ItemStack categoryHead = new ItemStack(Material.SKELETON_SKULL);
                ItemStack categoryBody = new ItemStack(Material.LEATHER_CHESTPLATE);
                ItemStack categoryLeftArm = new ItemStack(Material.WOODEN_SWORD);
                ItemStack categoryRightArm = new ItemStack(Material.DIAMOND_SWORD);
                ItemStack categoryLeftLeg = new ItemStack(Material.LEATHER_LEGGINGS);
                ItemStack categoryRightLeg = new ItemStack(Material.DIAMOND_LEGGINGS);
                
            	ItemMeta categoryHeadMeta = categoryHead.getItemMeta();
            	ItemMeta categoryBodyMeta = categoryBody.getItemMeta();
            	ItemMeta categoryLeftArmMeta = categoryLeftArm.getItemMeta();
            	ItemMeta categoryRightArmMeta = categoryRightArm.getItemMeta();
            	ItemMeta categoryLeftLegMeta = categoryLeftLeg.getItemMeta();
            	ItemMeta categoryRightLegMeta = categoryRightLeg.getItemMeta();
                
                categoryHeadLore.add(0, "§7Toggles armor stand physics");
                categoryHeadLore.add(1, "§7When disabled the armor stand will not be effected by gravity or collision");
                categoryBodyLore.add(0, "§7Toggles base plate visibility");
                categoryBodyLore.add(1, "§7When disabled the armor stand will appear to be free standing");
                categoryLeftArmLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                categoryLeftArmLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                categoryRightArmLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                categoryRightArmLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                categoryLeftLegLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                categoryLeftLegLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                categoryRightLegLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                categoryRightLegLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                categoryHeadMeta.setDisplayName("§9Edit Head");
                categoryHeadMeta.setLore(booleanPhysicsLore);
                categoryBodyMeta.setDisplayName("§dEdit Body");
                categoryBodyMeta.setLore(booleanBasePlateLore);
                categoryLeftArmMeta.setDisplayName("§6Edit Left Arm");
                categoryLeftArmMeta.setLore(booleanArmsLore);
                categoryRightArmMeta.setDisplayName("§9Edit Right Arm");
                categoryRightArmMeta.setLore(booleanSmallLore);
                categoryLeftLegMeta.setDisplayName("§6Edit Left Leg");
                categoryLeftLegMeta.setLore(infoBookLore);
                categoryRightLegMeta.setDisplayName("§9Edit Right Leg");
                categoryRightLegMeta.setLore(infoBookLore);
                
                categoryHead.setItemMeta(categoryHeadMeta);
                categoryBody.setItemMeta(categoryBodyMeta);
                categoryLeftArm.setItemMeta(categoryLeftArmMeta);
                categoryRightArm.setItemMeta(categoryRightArmMeta);
                categoryLeftLeg.setItemMeta(categoryLeftLegMeta);
                categoryRightLeg.setItemMeta(categoryRightLegMeta);

                ItemStack headPitch = new ItemStack(Material.RED_WOOL);
                ItemStack headYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack headTilt = new ItemStack(Material.GREEN_WOOL);
             
             
            	ItemMeta headPitchMeta = headPitch.getItemMeta();
            	ItemMeta headYawMeta = headYaw.getItemMeta();
            	ItemMeta headTiltMeta = headTilt.getItemMeta();


                headPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                headPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                headYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                headYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                headTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                headTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                headPitchMeta.setDisplayName("§aHead Pitch (Forward/Backward)");
                headPitchMeta.setLore(booleanPhysicsLore);
                headYawMeta.setDisplayName("§eHead Yaw (Clockwise/Anti-Clockwise)");
                headYawMeta.setLore(booleanBasePlateLore);
                headTiltMeta.setDisplayName("§cHead Tilt (Left/Right)");
                headTiltMeta.setLore(booleanArmsLore);
                
                headPitch.setItemMeta(headPitchMeta);
                headYaw.setItemMeta(headYawMeta);
                headTilt.setItemMeta(headTiltMeta);
                
                ItemStack bodyPitch = new ItemStack(Material.RED_WOOL);
                ItemStack bodyYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack bodyTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta bodyPitchMeta = bodyPitch.getItemMeta();
            	ItemMeta bodyYawMeta = headYaw.getItemMeta();
            	ItemMeta bodyTiltMeta = headTilt.getItemMeta();
                
                bodyPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                bodyPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                bodyYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                bodyYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                bodyTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                bodyTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                
                bodyPitchMeta.setDisplayName("§aBody Pitch (Forward/Backward)");
                bodyPitchMeta.setLore(booleanPhysicsLore);
                bodyYawMeta.setDisplayName("§eBody Yaw (Clockwise/Anti-Clockwise)");
                bodyYawMeta.setLore(booleanBasePlateLore);
                bodyTiltMeta.setDisplayName("§cBody Tilt (Left/Right)");
                bodyTiltMeta.setLore(booleanArmsLore);
              
                bodyPitch.setItemMeta(bodyPitchMeta);
                bodyYaw.setItemMeta(bodyYawMeta);
                bodyTilt.setItemMeta(bodyTiltMeta);
                
                ItemStack leftArmPitch = new ItemStack(Material.RED_WOOL);
                ItemStack leftArmYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack leftArmTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta leftArmPitchMeta = leftArmPitch.getItemMeta();
            	ItemMeta leftArmYawMeta = leftArmYaw.getItemMeta();
            	ItemMeta leftArmTiltMeta = leftArmTilt.getItemMeta();
               
                leftArmPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftArmPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                leftArmYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftArmYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                leftArmTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftArmTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                leftArmPitchMeta.setDisplayName("§aLeft Arm Pitch (Forward/Backward)");
                leftArmPitchMeta.setLore(booleanPhysicsLore);
                leftArmYawMeta.setDisplayName("§eLeft Arm Yaw (Clockwise/Anti-Clockwise)");
                leftArmYawMeta.setLore(booleanBasePlateLore);
                leftArmTiltMeta.setDisplayName("§cLeft Arm Tilt (Left/Right)");
                leftArmTiltMeta.setLore(booleanArmsLore);
                
                leftArmPitch.setItemMeta(leftArmPitchMeta);
                leftArmYaw.setItemMeta(leftArmYawMeta);
                leftArmTilt.setItemMeta(leftArmTiltMeta);
                
                ItemStack rightArmPitch = new ItemStack(Material.RED_WOOL);
                ItemStack rightArmYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack rightArmTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta rightArmPitchMeta = rightArmPitch.getItemMeta();
            	ItemMeta rightArmYawMeta = rightArmYaw.getItemMeta();
            	ItemMeta rightArmTiltMeta = rightArmTilt.getItemMeta();
          
                rightArmPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightArmPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                rightArmYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightArmYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                rightArmTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightArmTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                rightArmPitchMeta.setDisplayName("§aRight Arm Pitch (Forward/Backward)");
                rightArmPitchMeta.setLore(booleanPhysicsLore);
                rightArmYawMeta.setDisplayName("§eRight Arm Yaw (Clockwise/Anti-Clockwise)");
                rightArmYawMeta.setLore(booleanBasePlateLore);
                rightArmTiltMeta.setDisplayName("§cRight Arm Tilt (Left/Right)");
                rightArmTiltMeta.setLore(booleanArmsLore);
                
                rightArmPitch.setItemMeta(rightArmPitchMeta);
                rightArmYaw.setItemMeta(rightArmYawMeta);
                rightArmTilt.setItemMeta(rightArmTiltMeta);
                
                
                ItemStack leftLegPitch = new ItemStack(Material.RED_WOOL);
                ItemStack leftLegYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack leftLegTilt = new ItemStack(Material.GREEN_WOOL);
                
                
            	ItemMeta leftLegPitchMeta = leftLegPitch.getItemMeta();
            	ItemMeta leftLegYawMeta = leftLegYaw.getItemMeta();
            	ItemMeta leftLegTiltMeta = leftLegTilt.getItemMeta();
                
                leftLegPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftLegPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                leftLegYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftLegYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                leftLegTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                leftLegTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                leftLegPitchMeta.setDisplayName("§aLeft Leg Pitch (Forward/Backward)");
                leftLegPitchMeta.setLore(booleanPhysicsLore);
                leftLegYawMeta.setDisplayName("§eLeft Leg Yaw (Clockwise/Anti-Clockwise)");
                leftLegYawMeta.setLore(booleanBasePlateLore);
                leftLegTiltMeta.setDisplayName("§cLeft Leg Tilt (Left/Right)");
                leftLegTiltMeta.setLore(booleanArmsLore);
                
                leftLegPitch.setItemMeta(leftLegPitchMeta);
                leftLegYaw.setItemMeta(leftLegYawMeta);
                leftLegTilt.setItemMeta(leftLegTiltMeta);
                
                ItemStack rightLegPitch = new ItemStack(Material.RED_WOOL);
                ItemStack rightLegYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack rightLegTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta rightLegPitchMeta = rightLegPitch.getItemMeta();
            	ItemMeta rightLegYawMeta = rightLegYaw.getItemMeta();
            	ItemMeta rightLegTiltMeta = rightLegTilt.getItemMeta();
                
                rightLegPitchLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightLegPitchLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                rightLegYawLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightLegYawLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                rightLegTiltLore.add(0, "§7Toggles whether or not the armor stand has arms.");
                rightLegTiltLore.add(1, "§7If enabled the armor stand will be able to hold items.");
                
                rightLegPitchMeta.setDisplayName("§aRight Leg Pitch (Forward/Backward)");
                rightLegPitchMeta.setLore(booleanPhysicsLore);
                rightLegYawMeta.setDisplayName("§eRight Leg Yaw (Clockwise/Anti-Clockwise)");
                rightLegYawMeta.setLore(booleanBasePlateLore);
                rightLegTiltMeta.setDisplayName("§cRight Leg Tilt (Left/Right)");
                rightLegTiltMeta.setLore(booleanArmsLore);
                
                rightLegPitch.setItemMeta(rightLegPitchMeta);
                rightLegYaw.setItemMeta(rightLegYawMeta);
                rightLegTilt.setItemMeta(rightLegTiltMeta);
                
                inv.setItem(1, booleanPhysics);
                inv.setItem(3, booleanBasePlate);
                inv.setItem(5, booleanSmall);
                inv.setItem(7, booleanArms);
                inv.setItem(9, categoryHead);
                inv.setItem(10, categoryBody);
                inv.setItem(12, categoryLeftArm);
                inv.setItem(13, categoryRightArm);
                inv.setItem(15, categoryLeftLeg);
                inv.setItem(16, categoryRightLeg);
                inv.setItem(17, infoBook);
                inv.setItem(18, headPitch);
                inv.setItem(19, bodyPitch);
                inv.setItem(21, leftArmPitch);
                inv.setItem(22, rightArmPitch);
                inv.setItem(24, leftLegPitch);
                inv.setItem(25, rightLegPitch);
                inv.setItem(27, headYaw);
                inv.setItem(28, bodyYaw);
                inv.setItem(30, leftArmYaw);
                inv.setItem(31, rightArmYaw);
                inv.setItem(33, leftLegYaw);
                inv.setItem(34, rightLegYaw);
                inv.setItem(36, headTilt);
                inv.setItem(37, bodyTilt);
                inv.setItem(39, leftArmTilt);
                inv.setItem(40, rightArmTilt);
                inv.setItem(42, leftLegTilt);
                inv.setItem(43, rightLegTilt);
                event.getPlayer().openInventory(inv);

                }
    		}
    	}
    
    @EventHandler
	public void onInvClick(InventoryClickEvent event) {
    	Player p = (Player) event.getWhoClicked();
    	if(armorStandEditor.containsKey(p) && GuiListener.inGui.contains(p)) {
    		ArmorStand as = armorStandEditor.get(p);
    		ItemStack is = event.getCurrentItem();

    		if(is.getItemMeta().getDisplayName().equals("§aHead Pitch (Forward/Backward)")) {
				Double headPitch = Math.round(Math.abs(as.getHeadPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			Bukkit.getLogger().info("right");
    				if(headPitch == 0 || headPitch == 0.3925 || headPitch == 0.785 || headPitch == 1.1775 || headPitch == 1.57 || headPitch == 1.9625
    					|| headPitch == 2.355 || headPitch == 2.7475 || as.getHeadPose().getX() == 3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX() - 0.3925, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    				else {
            			Bukkit.getLogger().info("" + headPitch);
    					as.setHeadPose(new EulerAngle(0, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    			}
    			else {
    				if(headPitch == 0 || headPitch == 0.3925 || headPitch == 0.785 || headPitch == 1.1775 || headPitch == 1.57 || headPitch == 1.9625
        					|| headPitch == 2.355 || headPitch == 2.7475 || as.getHeadPose().getX() == -3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX() + 0.3925, as.getHeadPose().getY(), as.getHeadPose().getZ()));

    				}
    				else {
    					as.setHeadPose(new EulerAngle(0, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    			}
    		}
    	}
    	
    
    }

}