package utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkyblockUtil {

    public static void generateSkyblock(Player p){
        int lowerBoundX, lowerBoundY;
        int x = loadSbVal("LAST_X");
        int z = loadSbVal("LAST_Y");
        int dX = loadSbVal("LAST_dX");
        int dz = loadSbVal("LAST_dZ");

        //do spiral pattern

        x = x * 1000 - 500;
        z = z * 1000 - 500;

        //TODO: make this synchronous and maybe faster
        for(int i = x + 425; i < x + 10075; i++){
            for(int j = z + 425; j < z + 10075; j++){
                for(int k = 62; k < 75; k++){
                    Bukkit.getWorld("island").getBlockAt(i - x, j - z, k).setType(Bukkit.getWorld("island").getBlockAt(i, j, k).getType());
                }
            }
        }
    }
}
