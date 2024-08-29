package utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.techsmp.core.Main;

import java.io.File;
import java.util.Map;

public class ConfigHandler {
    private static Map<String, Object> keyToConfigVal;
    public static void loadConfig(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading Config");
        try {
            keyToConfigVal.clear();
        }catch (Exception e){}

        File msgSaveFile= new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "config.yml");
        FileConfiguration msgYaml = YamlConfiguration.loadConfiguration(msgSaveFile);
        keyToConfigVal = msgYaml.getConfigurationSection("Options").getValues(false);
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + keyToConfigVal.toString());
    }
    public static int getInt(String key){
        //Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Getting int");

        return (Integer) keyToConfigVal.get(key);
    }
    public static boolean getBool(String key){
        if((boolean) keyToConfigVal.get(key)){
            return true;
        }
        else{
            return false;
        }

    }
    public static String getString(String key){
        //Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Getting String");

        return (String)keyToConfigVal.get(key);
    }

}
