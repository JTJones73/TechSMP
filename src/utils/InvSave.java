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
    public static boolean saveInv(Player p){
        try {
            Bukkit.broadcastMessage(invSaveFolder.getPath());
            FileConfiguration invYaml = YamlConfiguration.loadConfiguration(invSaveFolder);
            int numInvsSavedForPlayers = 0;
            while (true){
                if(invYaml.contains(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers)) {
                    invYaml.get(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers);

                }else{
                    break;
                }
                numInvsSavedForPlayers++;
            }
            invYaml.set(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers, p.getInventory().getContents());
            invYaml.set(p.getUniqueId() + ".inventory.armour." + numInvsSavedForPlayers, Arrays.stream(p.getInventory().getArmorContents()).toArray());
            invYaml.set(p.getUniqueId() + ".inventory.health." + numInvsSavedForPlayers, p.getHealth());
            invYaml.set(p.getUniqueId() + ".inventory.hunger." + numInvsSavedForPlayers, p.getFoodLevel());
            invYaml.set(p.getUniqueId() + ".inventory.saturation." + numInvsSavedForPlayers, p.getSaturation());
            invYaml.set(p.getUniqueId() + ".inventory.exp." + numInvsSavedForPlayers, p.getExp());
            invYaml.set(p.getUniqueId() + ".inventory.exp." + numInvsSavedForPlayers, p.getExp());
            invYaml.set(p.getUniqueId() + ".inventory.effects." + numInvsSavedForPlayers, p.getActivePotionEffects());

            invYaml.save(invSaveFolder);
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
                if(invYaml.contains(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers)) {
                    invYaml.get(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers);

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
            ItemStack[] invContents =  ((List<ItemStack>)invYaml.get(p.getUniqueId() + ".inventory.content." + numInvsSavedForPlayers)).toArray(new ItemStack[0]);
            ArrayList<ItemStack> armourContents =  ((ArrayList<ItemStack>)invYaml.get(p.getUniqueId() + ".inventory.armour." + numInvsSavedForPlayers));
            Collection<PotionEffect> potionEffects = ((Collection<PotionEffect>)invYaml.get(p.getUniqueId() + ".inventory.effects." + numInvsSavedForPlayers));

            //fix armour
            ItemStack[] armourStack = new ItemStack[4];
            for(int i = 0; i < 4; i++){
                armourStack[i] = armourContents.get(i);
            }

            int playerFoodLevel =  ((int)invYaml.get(p.getUniqueId() + ".inventory.hunger." + numInvsSavedForPlayers));
            float playerSaturationLevel =  ((Double)invYaml.get(p.getUniqueId() + ".inventory.saturation." + numInvsSavedForPlayers)).floatValue();
            float playerExpLevel =  ((Double)invYaml.get(p.getUniqueId() + ".inventory.exp." + numInvsSavedForPlayers)).floatValue();
            Double playerHealthLevel =  ((double)invYaml.get(p.getUniqueId() + ".inventory.health." + numInvsSavedForPlayers));

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
}
