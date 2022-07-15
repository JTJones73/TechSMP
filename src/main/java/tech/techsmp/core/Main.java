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
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPostJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SleepingPercent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpecTP(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinAndLeaveMessage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CreeperExplosion(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PetDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PetToggleSit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new TabCompleter(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);


        getCommand("discord").setExecutor(new Discord());
        getCommand("devfeature").setExecutor(new Devfeature());
        getCommand("home").setExecutor(new Homes());
        getCommand("back").setExecutor(new Back());
        getCommand("sethome").setExecutor(new Sethome());
        getCommand("delhome").setExecutor(new Delhome());
        getCommand("fullbright").setExecutor(new Fullbright());
        getCommand("mute").setExecutor(new Mute());
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("roundup").setExecutor(new Roundup());
        getCommand("bedtp").setExecutor(new BedTP());
        getCommand("verify").setExecutor(new Verify());
        getCommand("wl").setExecutor(new Whitelist());
        getCommand("tpa").setExecutor(new Tpa());
        getCommand("tpaccept").setExecutor(new Tpaccept());
        getCommand("tpdeny").setExecutor(new Tpdeny());
        getCommand("spec").setExecutor(new Spec());
        getCommand("inspect").setExecutor(new Inspect(this));
        getCommand("tban").setExecutor(new Tban());
        getCommand("rank").setExecutor(new Rank(this));
        
    }
    public static Main getInstance(){
        return plugin;
     }
    
}
