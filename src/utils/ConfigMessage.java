package utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.techsmp.core.Main;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigMessage {
    private static Map<String, Object> keyToMessageBuilder;
    public static void loadMessages(){
        try {
            keyToMessageBuilder.clear();
        }catch (Exception e){}

        File msgSaveFile= new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "Messages.yml");
        FileConfiguration msgYaml = YamlConfiguration.loadConfiguration(msgSaveFile);
        keyToMessageBuilder = msgYaml.getConfigurationSection("Messages").getValues(false);
        Bukkit.broadcastMessage(keyToMessageBuilder.toString());
        Bukkit.broadcastMessage(ConfigMessage.getMessage("DISCORD", new String[]{" "}));
        Bukkit.broadcastMessage(ConfigMessage.getMessage("CHAT_OFFICER_CHAT", new String[]{"theencomputers", "Keep yourself safe :)"}));
    }
    public static String getMessage(String textKey, String[] variables){
        if(keyToMessageBuilder.containsKey(textKey)){
            return replaceMessage((String)keyToMessageBuilder.get(textKey), variables);
        }
        return null;
    }
    public static String replaceMessage(String textKey, String[] variables){
        boolean inAnd = false;
        boolean inPercent = false;
        boolean inHash = false;
        String result = "";
        if(variables == null )
            return textKey;
        for(int i = 0; i < textKey.length(); i++){
            if(textKey.charAt(i) == '&'){

                inAnd = true;
            }
            else if(inAnd && textKey.charAt(i) == '%' && !inPercent){

                inPercent = true;
            }
            else if(inAnd && textKey.charAt(i) == '%' && inPercent){;

                inPercent = false;
                inAnd = false;
                inHash = false;
            }
            else if(inAnd && inPercent && textKey.charAt(i) == '#' ){
                result = result + "§x";
                inHash = true;
            }
            else if(inAnd && inPercent && inHash){
                result = result + '§' + textKey.charAt(i);
            }
            else if(inAnd && !inPercent){

                result = result + '§' + textKey.charAt(i);
                inAnd = false;
            }
            else{

                result = result + textKey.charAt(i);
            }
        }
        for(int i = 0; i < variables.length; i++){
            result = result.replaceAll("%" + i + "%", variables[i]);
        }
        //Bukkit.broadcastMessage(result);
        return result;
    }
}
