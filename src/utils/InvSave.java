package utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import tech.techsmp.core.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class InvSave {
    private final static File invSaveFolder= new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "Inventories.yml");
    private final static File keyedInvSave= new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "KeyedInventories.yml");


    public static boolean pushInv(Player p){
        try {
            Bukkit.broadcastMessage(invSaveFolder.getPath());
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(invSaveFolder);
            int numInvsSavedForPlayers = 0;
            while (true){
                if(invYaml.contains(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content")) {
                    invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content");

                }else{
                    break;
                }
                numInvsSavedForPlayers++;
            }
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content", p.getInventory().getContents());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.echest", p.getEnderChest().getContents());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.armour", Arrays.stream(p.getInventory().getArmorContents()).toArray());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.health", p.getHealth());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.hunger", p.getFoodLevel());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.saturation", p.getSaturation());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.exp", p.getExp());
            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.effects", p.getActivePotionEffects());

            invYaml.save(invSaveFolder);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean saveInv(Player p, String key){
        try {
            Bukkit.broadcastMessage(keyedInvSave.getPath());
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(keyedInvSave);

            invYaml.set(key + ".inventory.content", p.getInventory().getContents());
            invYaml.set(key + ".inventory.armour", Arrays.stream(p.getInventory().getArmorContents()).toArray());
            invYaml.set(key + ".inventory.echest", p.getEnderChest().getContents());
            invYaml.set(key + ".inventory.hunger", p.getFoodLevel());
            invYaml.set(key + ".inventory.health", p.getHealth());

            invYaml.set(key + ".inventory.saturation", p.getSaturation());
            invYaml.set(key + ".inventory.exp", p.getExp());
            invYaml.set(key + ".inventory.effects", p.getActivePotionEffects());

            invYaml.save(keyedInvSave);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean popAndSetPlayerInv(Player p){
        try {
            int numInvsSavedForPlayers = 0;
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(invSaveFolder);


            while (true){
                if(invYaml.contains(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content")) {
                    invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content");

                }else{
                    break;
                }
                numInvsSavedForPlayers++;
            }
            if(numInvsSavedForPlayers == 0){
                Bukkit.broadcastMessage("Error no inv for player");
                return false;
            }
            --numInvsSavedForPlayers;
            ItemStack[] invContents =  ((List<ItemStack>)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.content")).toArray(new ItemStack[0]);
            ArrayList<ItemStack> armourContents =  ((ArrayList<ItemStack>)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.armour"));
            Collection<PotionEffect> potionEffects = ((Collection<PotionEffect>)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.effects"));

            //fix armour
            ItemStack[] armourStack = new ItemStack[4];
            for(int i = 0; i < 4; i++){
                armourStack[i] = armourContents.get(i);
            }

            int playerFoodLevel =  ((int)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.hunger"));
            float playerSaturationLevel =  ((Double)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.saturation")).floatValue();
            float playerExpLevel =  ((Double)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.ex")).floatValue();
            Double playerHealthLevel =  ((double)invYaml.get(p.getUniqueId() + "." + numInvsSavedForPlayers + ".inventory.health"));


            invYaml.set(p.getUniqueId() + "." + numInvsSavedForPlayers, null);


            p.getInventory().setContents(invContents);
            p.getInventory().setArmorContents(armourStack);
            p.setHealth(playerHealthLevel);
            p.setFoodLevel(playerFoodLevel);
            p.setExp(playerExpLevel);
            p.setSaturation(playerSaturationLevel);

            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }

            p.addPotionEffects(potionEffects);

            //delete yaml storage


            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
     return false;
    }
    public static boolean loadInv(Player p, String key){
        try {
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(keyedInvSave);

            ItemStack[] invContents =  ((List<ItemStack>)invYaml.get(key + ".inventory.content")).toArray(new ItemStack[0]);
            ArrayList<ItemStack> armourContents =  ((ArrayList<ItemStack>)invYaml.get(key + ".inventory.armour"));
            Collection<PotionEffect> potionEffects = ((Collection<PotionEffect>)invYaml.get(key + ".inventory.effects"));

            //fix armour
            ItemStack[] armourStack = new ItemStack[4];
            for(int i = 0; i < 4; i++){
                armourStack[i] = armourContents.get(i);
            }

            int playerFoodLevel =  ((int)invYaml.get(key+ ".inventory.hunger"));
            float playerSaturationLevel =  ((Double)invYaml.get(key + ".inventory.saturation")).floatValue();
            float playerExpLevel =  ((Double)invYaml.get(key+ ".inventory.exp")).floatValue();
            Double playerHealthLevel =  ((double)invYaml.get(key + ".inventory.health"));

            p.getInventory().setContents(invContents);
            p.getInventory().setArmorContents(armourStack);
            p.setHealth(playerHealthLevel);
            p.setFoodLevel(playerFoodLevel);
            p.setExp(playerExpLevel);
            p.setSaturation(playerSaturationLevel);

            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }

            p.addPotionEffects(potionEffects);


            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void delInvKey(String key){
        try {
            Bukkit.broadcastMessage(keyedInvSave.getPath());
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(keyedInvSave);

            invYaml.set(key, null);
            invYaml.save(keyedInvSave);
        }
        catch (Exception e){}
    }
}
