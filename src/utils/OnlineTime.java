package utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tech.techsmp.core.Join.PlayerPostJoin;
import tech.techsmp.core.Main;

import java.io.File;
import java.io.IOException;

public class OnlineTime {
    private final static File filePath = new File(Main.getInstance().getDataFolder().getAbsoluteFile(), "OnlineTime.yml");
    static FileConfiguration onlineYaml = YamlConfiguration.loadConfiguration(filePath);
    public static int getActiveMs(OfflinePlayer p){
        if(onlineYaml.contains(p.getUniqueId().toString() + ".active"))
            return (int) onlineYaml.get(p.getUniqueId().toString() + ".active");
            return 0;


    }
    public static int getAfkMs(OfflinePlayer p){
        if(onlineYaml.contains(p.getUniqueId().toString() + ".afk"))
            return (int) onlineYaml.get(p.getUniqueId().toString() + ".afk");
        return 0;
    }
    public static void setActiveMs(Player p, int millis){
        onlineYaml.set(p.getUniqueId().toString() + ".active", millis);
        try {
            onlineYaml.save(filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void setAfkMs(Player p, int millis){
        onlineYaml.set(p.getUniqueId().toString() + ".afk", millis);
}
}
