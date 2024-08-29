/*
 * 	Author: James Jones
 * 	Description:	When right clicking an armor stand with a golden hoe it opens a customization GUI that allows
 * 					users to edit the pose of the armor stand, whether its big or small, whether it has physics, whether it has arms,
 * 					or whether its baseplate is visible.
 * */

//TODO: Make this work on bedrock: bedrock guis are different as inventory click event is only executed when the item is dragged off and
// bedrock does not work well with armor stand poses there may be a workaround
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

	//Empty List for all item lore
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

	static boolean firstRun = false;		//if no one has used the armorstand editor yet the variables need to be declared can be replaced
											//by setting the variables above but this feature is not used very often so memory saving ig
	
	static Map<Player, ArmorStand> armorStandEditor = new HashMap<Player, ArmorStand>(10);




    @EventHandler
	public void onHoeClick(PlayerInteractAtEntityEvent event) {
    	if(event.getRightClicked() instanceof ArmorStand) {

    		Player p = event.getPlayer();
    		if(p.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)) {
    			event.setCancelled(true);

				//It might be better to do this with a dedicated initializer but this works well
    			if(!firstRun) {							//time to set a bunch of variables
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

					categoryHeadLore.add(0, "§7Change the position of the head");
					categoryHeadLore.add(1, "§7Right click and left click the options to edit the pose");
					categoryBodyLore.add(0, "§7Change the postion of the body");
					categoryBodyLore.add(1, "§7Right click and left click the options to edit the pose");
					categoryLeftArmLore.add(0, "§7Change the position of the left arm");
					categoryLeftArmLore.add(1, "§7Right click and left click the options to edit the pose");
					categoryRightArmLore.add(0, "§7Change the position of the left arm");
					categoryRightArmLore.add(1, "§7Right click and left click the options to edit the pose");
					categoryRightLegLore.add(0, "§7Change the position of the right leg");
					categoryRightLegLore.add(1, "§7Right click and left click the options to edit the pose");
					categoryLeftLegLore.add(0, "§7Change the position of the left leg");
					categoryLeftLegLore.add(1, "§7Right click and left click the options to edit the pose");

                    /*categoryHeadLore.add(0, "§7Toggles armor stand physics");
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
                    categoryRightLegLore.add(1, "§7If enabled the armor stand will be able to hold items.");*/


					headPitchLore.add(0, "§7Change head front to back position");
					headPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					headYawLore.add(0, "§7Change head turn position");
					headYawLore.add(1, "§7Left click to turn clockwise right click to move counterclockwise");
					headTiltLore.add(0, "§7Change head left to right position");
					headTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");


					bodyPitchLore.add(0, "§7Change body front to back position");
					bodyPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					bodyYawLore.add(0, "Change body turn position");
					bodyYawLore.add(1, "§7Left click to turn clockwise right click to move counterclockwise");
					bodyTiltLore.add(0, "§7Change body left to right position");
					bodyTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");

					leftArmPitchLore.add(0, "§7Change left arm front to back position");
					leftArmPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					leftArmYawLore.add(0, "§7Change left arm turn position");
					leftArmYawLore.add(1,"§7Left click to turn clockwise right click to move counterclockwise");
					leftArmTiltLore.add(0, "§7Change left arm left to right position");
					leftArmTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");

					leftLegPitchLore.add(0, "§7Change left leg front to back position");
					leftLegPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					leftLegYawLore.add(0, "§7Change left leg turn position");
					leftLegYawLore.add(1,"§7Left click to turn clockwise right click to move counterclockwise");
					leftLegTiltLore.add(0, "§7Change left leg left to right position");
					leftLegTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");

					rightArmPitchLore.add(0, "§7Change right arm front to back position");
					rightArmPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					rightArmYawLore.add(0, "§7Change right arm turn position");
					rightArmYawLore.add(1, "§7Left click to turn clockwise right click to move counterclockwise");
					rightArmTiltLore.add(0, "§7Change right arm left to right position");
					rightArmTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");

					rightLegPitchLore.add(0, "§7Change right leg front to back position");
					rightLegPitchLore.add(1, "§7Left click to pitch forward, right click to pitch backwards");
					rightLegYawLore.add(0, "§7Change right leg turn position");
					rightLegYawLore.add(1, "§7Left click to turn clockwise right click to move counterclockwise");
					rightLegTiltLore.add(0, "§7Change right leg left to right position");
					rightLegTiltLore.add(1, "§7Left click to tilt to the left, right click to tilt to the right");
                    
    			}
    			firstRun = true;			//do not do this again


				//put player and target armor stand in hashmap so the armorstand can be found and edited
    			armorStandEditor.put(p, (ArmorStand) event.getRightClicked());
    			GuiListener.inGui.add(p);//use the GUI listener Util to prevent players from abusing the gui


				///Make the inventory with display names
                Inventory inv = Bukkit.getServer().createInventory(null, 45, "ArmorStand Editor");

				//Itemstacks for each parameter
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


            	//Give parameters displayNames
                booleanPhysicsMeta.setDisplayName("§aPhysics (Off/On)");
                booleanPhysicsMeta.setLore(booleanPhysicsLore);
                booleanBasePlateMeta.setDisplayName("§8Base Plate (Visible/Hidden)");
                booleanBasePlateMeta.setLore(booleanBasePlateLore);
                booleanArmsMeta.setDisplayName("§bArms (Enabled/Disabled)");
                booleanArmsMeta.setLore(booleanArmsLore);
                booleanSmallMeta.setDisplayName("§cSize (Small/Large)");
                booleanSmallMeta.setLore(booleanSmallLore);
                infoBookMeta.setDisplayName("§c§lReset");
                infoBookMeta.setLore(infoBookLore);

				//Apply displayNames
                booleanPhysics.setItemMeta(booleanPhysicsMeta);
                booleanBasePlate.setItemMeta(booleanBasePlateMeta);
                booleanSmall.setItemMeta(booleanSmallMeta);
                booleanArms.setItemMeta(booleanArmsMeta);
                infoBook.setItemMeta(infoBookMeta);

				//Itemstacks for categories (displayed at top so the user knows what part they are editing)
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
                
                //Give title and lore to categories
                categoryHeadMeta.setDisplayName("§9Edit Head");
                categoryHeadMeta.setLore(categoryHeadLore);
                categoryBodyMeta.setDisplayName("§dEdit Body");
                categoryBodyMeta.setLore(categoryBodyLore);
                categoryLeftArmMeta.setDisplayName("§6Edit Left Arm");
                categoryLeftArmMeta.setLore(categoryLeftArmLore);
                categoryRightArmMeta.setDisplayName("§9Edit Right Arm");
                categoryRightArmMeta.setLore(categoryRightArmLore);
                categoryLeftLegMeta.setDisplayName("§6Edit Left Leg");
                categoryLeftLegMeta.setLore(categoryLeftLegLore);
                categoryRightLegMeta.setDisplayName("§9Edit Right Leg");
                categoryRightLegMeta.setLore(categoryRightLegLore);

				//Set tem meta
                categoryHead.setItemMeta(categoryHeadMeta);
                categoryBody.setItemMeta(categoryBodyMeta);
                categoryLeftArm.setItemMeta(categoryLeftArmMeta);
                categoryRightArm.setItemMeta(categoryRightArmMeta);
                categoryLeftLeg.setItemMeta(categoryLeftLegMeta);
                categoryRightLeg.setItemMeta(categoryRightLegMeta);

				//Head yaw pitch and tilt Items
                ItemStack headPitch = new ItemStack(Material.RED_WOOL);
                ItemStack headYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack headTilt = new ItemStack(Material.GREEN_WOOL);


            	ItemMeta headPitchMeta = headPitch.getItemMeta();
            	ItemMeta headYawMeta = headYaw.getItemMeta();
            	ItemMeta headTiltMeta = headTilt.getItemMeta();

				//Head yaw pitch and tilt Display and Lore
                headPitchMeta.setDisplayName("§aHead Pitch (Forward/Backward)");
                headPitchMeta.setLore(headPitchLore);
                headYawMeta.setDisplayName("§eHead Yaw (Clockwise/Anti-Clockwise)");
                headYawMeta.setLore(headYawLore);
                headTiltMeta.setDisplayName("§cHead Tilt (Left/Right)");
                headTiltMeta.setLore(headPitchLore);

				//Head yaw pitch and tilt set meta
                headPitch.setItemMeta(headPitchMeta);
                headYaw.setItemMeta(headYawMeta);
                headTilt.setItemMeta(headTiltMeta);
                
                ItemStack bodyPitch = new ItemStack(Material.RED_WOOL);
                ItemStack bodyYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack bodyTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta bodyPitchMeta = bodyPitch.getItemMeta();
            	ItemMeta bodyYawMeta = headYaw.getItemMeta();
            	ItemMeta bodyTiltMeta = headTilt.getItemMeta();
                
                
                bodyPitchMeta.setDisplayName("§aBody Pitch (Forward/Backward)");
                bodyPitchMeta.setLore(bodyPitchLore);
                bodyYawMeta.setDisplayName("§eBody Yaw (Clockwise/Anti-Clockwise)");
                bodyYawMeta.setLore(bodyYawLore);
                bodyTiltMeta.setDisplayName("§cBody Tilt (Left/Right)");
                bodyTiltMeta.setLore(bodyTiltLore);
              
                bodyPitch.setItemMeta(bodyPitchMeta);
                bodyYaw.setItemMeta(bodyYawMeta);
                bodyTilt.setItemMeta(bodyPitchMeta);
                
                ItemStack leftArmPitch = new ItemStack(Material.RED_WOOL);
                ItemStack leftArmYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack leftArmTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta leftArmPitchMeta = leftArmPitch.getItemMeta();
            	ItemMeta leftArmYawMeta = leftArmYaw.getItemMeta();
            	ItemMeta leftArmTiltMeta = leftArmTilt.getItemMeta();
                
                leftArmPitchMeta.setDisplayName("§aLeft Arm Pitch (Forward/Backward)");
                leftArmPitchMeta.setLore(leftArmPitchLore);
                leftArmYawMeta.setDisplayName("§eLeft Arm Yaw (Clockwise/Anti-Clockwise)");
                leftArmYawMeta.setLore(leftArmYawLore);
                leftArmTiltMeta.setDisplayName("§cLeft Arm Tilt (Left/Right)");
                leftArmTiltMeta.setLore(leftArmTiltLore);
                
                leftArmPitch.setItemMeta(leftArmPitchMeta);
                leftArmYaw.setItemMeta(leftArmYawMeta);
                leftArmTilt.setItemMeta(leftArmTiltMeta);
                
                ItemStack rightArmPitch = new ItemStack(Material.RED_WOOL);
                ItemStack rightArmYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack rightArmTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta rightArmPitchMeta = rightArmPitch.getItemMeta();
            	ItemMeta rightArmYawMeta = rightArmYaw.getItemMeta();
            	ItemMeta rightArmTiltMeta = rightArmTilt.getItemMeta();

                
                rightArmPitchMeta.setDisplayName("§aRight Arm Pitch (Forward/Backward)");
                rightArmPitchMeta.setLore(rightArmPitchLore);
                rightArmYawMeta.setDisplayName("§eRight Arm Yaw (Clockwise/Anti-Clockwise)");
                rightArmYawMeta.setLore(rightArmYawLore);
                rightArmTiltMeta.setDisplayName("§cRight Arm Tilt (Left/Right)");
                rightArmTiltMeta.setLore(rightArmTiltLore);
                
                rightArmPitch.setItemMeta(rightArmPitchMeta);
                rightArmYaw.setItemMeta(rightArmYawMeta);
                rightArmTilt.setItemMeta(rightArmTiltMeta);
                
                
                ItemStack leftLegPitch = new ItemStack(Material.RED_WOOL);
                ItemStack leftLegYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack leftLegTilt = new ItemStack(Material.GREEN_WOOL);
                
                
            	ItemMeta leftLegPitchMeta = leftLegPitch.getItemMeta();
            	ItemMeta leftLegYawMeta = leftLegYaw.getItemMeta();
            	ItemMeta leftLegTiltMeta = leftLegTilt.getItemMeta();
                

                leftLegPitchMeta.setDisplayName("§aLeft Leg Pitch (Forward/Backward)");
                leftLegPitchMeta.setLore(leftLegPitchLore);
                leftLegYawMeta.setDisplayName("§eLeft Leg Yaw (Clockwise/Anti-Clockwise)");
                leftLegYawMeta.setLore(leftLegYawLore);
                leftLegTiltMeta.setDisplayName("§cLeft Leg Tilt (Left/Right)");
                leftLegTiltMeta.setLore(leftLegTiltLore);
                
                leftLegPitch.setItemMeta(leftLegPitchMeta);
                leftLegYaw.setItemMeta(leftLegYawMeta);
                leftLegTilt.setItemMeta(leftLegTiltMeta);
                
                ItemStack rightLegPitch = new ItemStack(Material.RED_WOOL);
                ItemStack rightLegYaw = new ItemStack(Material.YELLOW_WOOL);
                ItemStack rightLegTilt = new ItemStack(Material.GREEN_WOOL);
                
            	ItemMeta rightLegPitchMeta = rightLegPitch.getItemMeta();
            	ItemMeta rightLegYawMeta = rightLegYaw.getItemMeta();
            	ItemMeta rightLegTiltMeta = rightLegTilt.getItemMeta();
                
                rightLegPitchMeta.setDisplayName("§aRight Leg Pitch (Forward/Backward)");
                rightLegPitchMeta.setLore(rightLegPitchLore);
                rightLegYawMeta.setDisplayName("§eRight Leg Yaw (Clockwise/Anti-Clockwise)");
                rightLegYawMeta.setLore(rightLegYawLore);
                rightLegTiltMeta.setDisplayName("§cRight Leg Tilt (Left/Right)");
                rightLegTiltMeta.setLore(rightLegTiltLore);
                
                rightLegPitch.setItemMeta(rightLegPitchMeta);
                rightLegYaw.setItemMeta(rightLegYawMeta);
                rightLegTilt.setItemMeta(rightLegTiltMeta);


				//put the items in the inventory
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
    /*
    * 	Author:			James Jones
    * 	Description:	When clicking on an editor gui this listener edits the target armorstand
    * */
    @EventHandler
	public void onInvClick(InventoryClickEvent event) {
    	Player p = (Player) event.getWhoClicked();
    	if(armorStandEditor.containsKey(p) && GuiListener.inGui.contains(p)) {
    		ArmorStand as = armorStandEditor.get(p);
    		ItemStack is = event.getCurrentItem();
    		try {
    		if(is.getItemMeta().getDisplayName().equals("§aPhysicis (Off/On)")) {
    			if(as.hasGravity()) {
    			as.setGravity(false);
    			as.setCollidable(false);
    			}
    			else {
        			as.setGravity(true);
        			as.setCollidable(true);
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§8Base Plate (Visisble/Hidden)")) {
    			if(as.hasBasePlate()) {
    			as.setBasePlate(false);
    			}
    			else {
        			as.setBasePlate(true);
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§bArms (Enabled/Disabled)")) {
    			if(as.hasArms()) {
    			as.setArms(false);
    			}
    			else {
        			as.setArms(true);
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cSize (Small/Large)")) {
    			if(as.isSmall()) {
    			as.setSmall(false);
    			}
    			else {
    	 			as.setSmall(true);
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§c§lReset")) {
    			as.setSmall(false);
    			as.setArms(false);
    			as.setBasePlate(true);
    			as.setCollidable(true);
    			as.setGravity(true);
				as.setHeadPose(new EulerAngle(0,0,0));
				as.setLeftArmPose(new EulerAngle(0,0,0));
				as.setRightArmPose(new EulerAngle(0,0,0));
				as.setLeftLegPose(new EulerAngle(0,0,0));
				as.setRightLegPose(new EulerAngle(0,0,0));
				as.setBodyPose(new EulerAngle(0,0,0));

    		}

    		if(is.getItemMeta().getDisplayName().equals("§aHead Pitch (Forward/Backward)")) {
				Double pitch = Math.round(Math.abs(as.getHeadPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {			//if right clicking decrement pitch by pi/8 or snap back to 0
        			//Bukkit.getLogger().info("right");
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
    					|| pitch == 2.355 || pitch == 2.7475 || as.getHeadPose().getX() == 3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX() - 0.3925, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    				else {
						//Bukkit.getConsoleSender().sendMessage("" + pitch);
    					as.setHeadPose(new EulerAngle(0, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    			}
    			else {
					//if left clicking increment by pi/8
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 || pitch == 2.7475 || as.getHeadPose().getX() == -3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX() + 0.3925, as.getHeadPose().getY(), as.getHeadPose().getZ()));

    				}
    				else {
    					as.setHeadPose(new EulerAngle(0, as.getHeadPose().getY(), as.getHeadPose().getZ()));
    				}
    			}
    		}


    		if(is.getItemMeta().getDisplayName().equals("§eHead Yaw (Clockwise/Anti-Clockwise)")) {
				Double yaw = Math.round(Math.abs(as.getHeadPose().getY()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
    					|| yaw == 2.355 || yaw == 2.7475 || as.getHeadPose().getX() == 3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY() - 0.3925, as.getHeadPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + yaw);
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), 0, as.getHeadPose().getZ()));
    				}
    			}
    			else {
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getHeadPose().getX() == -3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY() + 0.3925, as.getHeadPose().getZ()));

    				}
    				else {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), 0, as.getHeadPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cHead Tilt (Left/Right)")) {
				Double tilt = Math.round(Math.abs(as.getHeadPose().getZ()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getHeadPose().getX() == -3.14) {

    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY(), as.getHeadPose().getZ() + 0.3925));
    				}
    				else {
            			//Bukkit.getLogger().info("" + tilt);
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY(), 0));
    				}
    			}
    			else {
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getHeadPose().getX() == 3.14) {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY(), as.getHeadPose().getZ() - 0.3925));

    				}
    				else {
    					as.setHeadPose(new EulerAngle(as.getHeadPose().getX(), as.getHeadPose().getY(), 0));
    				}
    			}
    		}
    		
    		if(is.getItemMeta().getDisplayName().equals("§aBody Pitch (Forward/Backward)")) {
				Double pitch = Math.round(Math.abs(as.getBodyPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
    					|| pitch == 2.355 || pitch == 2.7475 || as.getBodyPose().getX() == 3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX() - 0.3925, as.getBodyPose().getY(), as.getBodyPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + pitch);
    					as.setBodyPose(new EulerAngle(0, as.getBodyPose().getY(), as.getBodyPose().getZ()));
    				}
    			}
    			else {
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 || pitch == 2.7475 || as.getBodyPose().getX() == -3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX() + 0.3925, as.getBodyPose().getY(), as.getBodyPose().getZ()));

    				}
    				else {
    					as.setBodyPose(new EulerAngle(0, as.getBodyPose().getY(), as.getBodyPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§eBody Yaw (Clockwise/Anti-Clockwise)")) {
				Double yaw = Math.round(Math.abs(as.getBodyPose().getY()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
    					|| yaw == 2.355 || yaw == 2.7475 || as.getBodyPose().getX() == 3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY() - 0.3925, as.getBodyPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + yaw);
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), 0, as.getBodyPose().getZ()));
    				}
    			}
    			else {
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getBodyPose().getX() == -3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY() + 0.3925, as.getBodyPose().getZ()));

    				}
    				else {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), 0, as.getBodyPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cBody Tilt (Left/Right)")) {
				Double tilt = Math.round(Math.abs(as.getBodyPose().getZ()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
    					|| tilt == 2.355 || tilt == 2.7475 || as.getBodyPose().getX() == -3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY(), as.getBodyPose().getZ() + 0.3925));
    				}
    				else {
            			//Bukkit.getLogger().info("" + tilt);
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY(), 0));
    				}
    			}
    			else {
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getBodyPose().getX() == 3.14) {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY(), as.getBodyPose().getZ() - 0.3925));

    				}
    				else {
    					as.setBodyPose(new EulerAngle(as.getBodyPose().getX(), as.getBodyPose().getY(), 0));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§aLeft Arm Pitch (Forward/Backward)")) {
				Double pitch = Math.round(Math.abs(as.getLeftArmPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
    					|| pitch == 2.355 || pitch == 2.7475 || as.getLeftArmPose().getX() == 3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX() - 0.3925, as.getLeftArmPose().getY(), as.getLeftArmPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + pitch);
    					as.setLeftArmPose(new EulerAngle(0, as.getLeftArmPose().getY(), as.getLeftArmPose().getZ()));
    				}
    			}
    			else {
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 ||pitch == 2.7475 || as.getLeftArmPose().getX() == -3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX() + 0.3925, as.getLeftArmPose().getY(), as.getLeftArmPose().getZ()));

    				}
    				else {
    					as.setLeftArmPose(new EulerAngle(0, as.getLeftArmPose().getY(), as.getLeftArmPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§eLeft Arm Yaw (Clockwise/Anti-Clockwise)")) {
				Double yaw = Math.round(Math.abs(as.getLeftArmPose().getY()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
    					|| yaw == 2.355 || yaw == 2.7475 || as.getLeftArmPose().getX() == 3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY() - 0.3925, as.getLeftArmPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + yaw);
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), 0, as.getLeftArmPose().getZ()));
    				}
    			}
    			else {
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getLeftArmPose().getX() == -3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY() + 0.3925, as.getLeftArmPose().getZ()));

    				}
    				else {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), 0, as.getLeftArmPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cLeft Arm Tilt (Left/Right)")) {
				Double tilt = Math.round(Math.abs(as.getLeftArmPose().getZ()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
    					|| tilt == 2.355 || tilt == 2.7475 || as.getLeftArmPose().getX() == -3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY(), as.getLeftArmPose().getZ() + 0.3925));
    				}
    				else {
            			//Bukkit.getLogger().info("" + tilt);
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY(), 0));
    				}
    			}
    			else {
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getLeftArmPose().getX() == 3.14) {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY(), as.getLeftArmPose().getZ() - 0.3925));

    				}
    				else {
    					as.setLeftArmPose(new EulerAngle(as.getLeftArmPose().getX(), as.getLeftArmPose().getY(), 0));
    				}
    			}
    		}
    		
    		if(is.getItemMeta().getDisplayName().equals("§aRight Arm Pitch (Forward/Backward)")) {
				Double pitch = Math.round(Math.abs(as.getRightArmPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
    					|| pitch == 2.355 || pitch == 2.7475 || as.getRightArmPose().getX() == 3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX() - 0.3925, as.getRightArmPose().getY(), as.getRightArmPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + pitch);
    					as.setRightArmPose(new EulerAngle(0, as.getRightArmPose().getY(), as.getRightArmPose().getZ()));
    				}
    			}
    			else {
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 || pitch == 2.7475 || as.getRightArmPose().getX() == -3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX() + 0.3925, as.getRightArmPose().getY(), as.getRightArmPose().getZ()));

    				}
    				else {
    					as.setRightArmPose(new EulerAngle(0, as.getRightArmPose().getY(), as.getRightArmPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§eRight Arm Yaw (Clockwise/Anti-Clockwise)")) {
				Double yaw = Math.round(Math.abs(as.getRightArmPose().getY()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 ||yaw == 1.57 || yaw == 1.9625
    					|| yaw == 2.355 || yaw == 2.7475 || as.getRightArmPose().getX() == 3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY() - 0.3925, as.getRightArmPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + yaw);
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), 0, as.getRightArmPose().getZ()));
    				}
    			}
    			else {
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getRightArmPose().getX() == -3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY() + 0.3925, as.getRightArmPose().getZ()));

    				}
    				else {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), 0, as.getRightArmPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cRight Arm Tilt (Left/Right)")) {
				Double tilt = Math.round(Math.abs(as.getRightArmPose().getZ()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
    					|| tilt == 2.355 || tilt == 2.7475 || as.getRightArmPose().getX() == -3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY(), as.getRightArmPose().getZ() + 0.3925));
    				}
    				else {
            			//Bukkit.getLogger().info("" + tilt);
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY(), 0));
    				}
    			}
    			else {
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getRightArmPose().getX() == 3.14) {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY(), as.getRightArmPose().getZ() - 0.3925));

    				}
    				else {
    					as.setRightArmPose(new EulerAngle(as.getRightArmPose().getX(), as.getRightArmPose().getY(), 0));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§aLeft Leg Pitch (Forward/Backward)")) {
				Double pitch = Math.round(Math.abs(as.getLeftLegPose().getX()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
    					|| pitch == 2.355 || pitch == 2.7475 || as.getLeftLegPose().getX() == 3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX() - 0.3925, as.getLeftLegPose().getY(), as.getLeftLegPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + pitch);
    					as.setLeftLegPose(new EulerAngle(0, as.getLeftLegPose().getY(), as.getLeftLegPose().getZ()));
    				}
    			}
    			else {
    				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 || pitch == 2.7475 || as.getLeftLegPose().getX() == -3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX() + 0.3925, as.getLeftLegPose().getY(), as.getLeftLegPose().getZ()));

    				}
    				else {
    					as.setLeftLegPose(new EulerAngle(0, as.getLeftLegPose().getY(), as.getLeftLegPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§eLeft Leg Yaw (Clockwise/Anti-Clockwise)")) {
				Double yaw = Math.round(Math.abs(as.getLeftLegPose().getY()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
    					|| yaw == 2.355 || yaw == 2.7475 || as.getLeftLegPose().getX() == 3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY() - 0.3925, as.getLeftLegPose().getZ()));
    				}
    				else {
            			//Bukkit.getLogger().info("" + yaw);
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), 0, as.getLeftLegPose().getZ()));
    				}
    			}
    			else {
    				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getLeftLegPose().getX() == -3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY() + 0.3925, as.getLeftLegPose().getZ()));

    				}
    				else {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), 0, as.getLeftLegPose().getZ()));
    				}
    			}
    		}
    		if(is.getItemMeta().getDisplayName().equals("§cLeft Leg Tilt (Left/Right)")) {
				Double tilt = Math.round(Math.abs(as.getLeftLegPose().getZ()) * 10000.0) / 10000.0;
    			if(event.getClick().equals(ClickType.RIGHT)) {
        			//Bukkit.getLogger().info("right");
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
    					|| tilt == 2.355 || tilt == 2.7475 || as.getLeftLegPose().getX() == -3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY(), as.getLeftLegPose().getZ() + 0.3925));
    				}
    				else {
            			//Bukkit.getLogger().info("" + tilt);
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY(), 0));
    				}
    			}
    			else {
    				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getLeftLegPose().getX() == 3.14) {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY(), as.getLeftLegPose().getZ() - 0.3925));

    				}
    				else {
    					as.setLeftLegPose(new EulerAngle(as.getLeftLegPose().getX(), as.getLeftLegPose().getY(), 0));
    				}
    			}
    		}

        		if(is.getItemMeta().getDisplayName().equals("§aRight Leg Pitch (Forward/Backward)")) {
    				Double pitch = Math.round(Math.abs(as.getRightLegPose().getX()) * 10000.0) / 10000.0;
        			if(event.getClick().equals(ClickType.RIGHT)) {
            			//Bukkit.getLogger().info("right");
        				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
        					|| pitch == 2.355 || pitch == 2.7475 || as.getRightLegPose().getX() == 3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX() - 0.3925, as.getRightLegPose().getY(), as.getRightLegPose().getZ()));
        				}
        				else {
                			//Bukkit.getLogger().info("" + pitch);
        					as.setRightLegPose(new EulerAngle(0, as.getRightLegPose().getY(), as.getRightLegPose().getZ()));
        				}
        			}
        			else {
        				if(pitch == 0 || pitch == 0.3925 || pitch == 0.785 || pitch == 1.1775 || pitch == 1.57 || pitch == 1.9625
            					|| pitch == 2.355 || pitch == 2.7475 || as.getRightLegPose().getX() == -3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX() + 0.3925, as.getRightLegPose().getY(), as.getRightLegPose().getZ()));

        				}
        				else {
        					as.setRightLegPose(new EulerAngle(0, as.getRightLegPose().getY(), as.getRightLegPose().getZ()));
        				}
        			}
        		}
        		if(is.getItemMeta().getDisplayName().equals("§eRight Leg Yaw (Clockwise/Anti-Clockwise)")) {
    				Double yaw = Math.round(Math.abs(as.getRightLegPose().getY()) * 10000.0) / 10000.0;
        			if(event.getClick().equals(ClickType.RIGHT)) {
            			//Bukkit.getLogger().info("right");
        				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
        					|| yaw == 2.355 || yaw == 2.7475 || as.getRightLegPose().getX() == 3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY() - 0.3925, as.getRightLegPose().getZ()));
        				}
        				else {
                			//Bukkit.getLogger().info("" + yaw);
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), 0, as.getRightLegPose().getZ()));
        				}
        			}
        			else {
        				if(yaw == 0 || yaw == 0.3925 || yaw == 0.785 || yaw == 1.1775 || yaw == 1.57 || yaw == 1.9625
            					|| yaw == 2.355 || yaw == 2.7475 || as.getRightLegPose().getX() == -3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY() + 0.3925, as.getRightLegPose().getZ()));

        				}
        				else {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), 0, as.getRightLegPose().getZ()));
        				}
        			}
        		}
        		if(is.getItemMeta().getDisplayName().equals("§cRight Leg Tilt (Left/Right)")) {
    				Double tilt = Math.round(Math.abs(as.getRightLegPose().getZ()) * 10000.0) / 10000.0;
        			if(event.getClick().equals(ClickType.RIGHT)) {
            			//Bukkit.getLogger().info("right");
        				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
        					|| tilt == 2.355 || tilt == 2.7475 || as.getRightLegPose().getX() == -3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY(), as.getRightLegPose().getZ() + 0.3925));
        				}
        				else {
                			//Bukkit.getLogger().info("" + tilt);
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY(), 0));
        				}
        			}
        			else {
        				if(tilt == 0 || tilt == 0.3925 || tilt == 0.785 || tilt == 1.1775 || tilt == 1.57 || tilt == 1.9625
            					|| tilt == 2.355 || tilt == 2.7475 || as.getRightLegPose().getX() == 3.14) {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY(), as.getRightLegPose().getZ() - 0.3925));

        				}
        				else {
        					as.setRightLegPose(new EulerAngle(as.getRightLegPose().getX(), as.getRightLegPose().getY(), 0));
        				}
        			}
        		}
    	}
        		catch(Exception e) {
        			
        		}
  
    	}
    	
    }
    }
