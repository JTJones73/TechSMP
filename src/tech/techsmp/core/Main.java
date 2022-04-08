package tech.techsmp.core;
import tech.techsmp.core.cosmetic.*;
import tech.techsmp.core.commands.*;
import tech.techsmp.core.Listeners.*;



import tech.techsmp.core.Join.*;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;




public class Main extends JavaPlugin{
        static Main plugin;
	public void onEnable(){
                plugin = this;
        Logger.getLogger("Minecraft").info("TechSMP By James Jones");
        Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPostJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpecTP(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinAndLeaveMessage(), this);

        getCommand("discord").setExecutor(new Discord());
        getCommand("bedtp").setExecutor(new BedTP());
        getCommand("verify").setExecutor(new Verify());
        getCommand("wl").setExecutor(new Whitelist());
        getCommand("tpa").setExecutor(new Tpa());
        getCommand("tpaccept").setExecutor(new Tpaccept());
        getCommand("tpdeny").setExecutor(new Tpdeny());
        getCommand("spec").setExecutor(new Spec());
        getCommand("rank").setExecutor(new Rank(this));
        
    }
    public static Main getInstance(){
        return plugin;
        }
    
}
