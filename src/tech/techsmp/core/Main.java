package tech.techsmp.core;
import org.bukkit.Location;
import tech.techsmp.core.Packet.SpecPacketBlocker;
import tech.techsmp.core.cosmetic.*;
import tech.techsmp.core.commands.*;
import tech.techsmp.core.Listeners.*;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;



import tech.techsmp.core.Join.*;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import utils.ConfigMessage;
import utils.Teleporter;
import utils.TimeController;


public class Main extends JavaPlugin implements Listener{
    static ProtocolManager pm;
    public static Location spawnLoc = new Location(Bukkit.getWorld("world"), -95, 85,0);

        static Main plugin;
        SpecPacketBlocker spb = new SpecPacketBlocker();

	public void onEnable(){
                pm = ProtocolLibrary.getProtocolManager();

                plugin = this;
                TimeController.timerTask();
        ConfigMessage.loadMessages();
        Bukkit.getConsoleSender().sendMessage("TechSMP By James Jones");
        Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPostJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SleepingPercent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpecTP(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MineAlerts(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityHurtListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AFKCheck(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinAndLeaveMessage(), this);
         Bukkit.getServer().getPluginManager().registerEvents(new Teleporter(), this);
         Bukkit.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CreeperExplosion(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PetDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpawnProtection(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PetToggleSit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new TabCompleter(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new TimeController(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpleefListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EnderManGreif(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PhantomSpawn(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ParkourListener(), this);


        getCommand("spleef").setExecutor(new Spleef());
        getCommand("discord").setExecutor(new Discord());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("devfeature").setExecutor(new Devfeature());
        getCommand("home").setExecutor(new Homes());
        getCommand("back").setExecutor(new Back());
        getCommand("sethome").setExecutor(new Sethome());
        getCommand("delhome").setExecutor(new Delhome());
        getCommand("fullbright").setExecutor(new Fullbright());
        getCommand("mute").setExecutor(new Mute());
         getCommand("Unmute").setExecutor(new Unmute());
         getCommand("invsee").setExecutor(new Invsee());
        getCommand("roundup").setExecutor(new Roundup());
        getCommand("bedtp").setExecutor(new BedTP());
        getCommand("verify").setExecutor(new Verify());
        getCommand("wl").setExecutor(new Whitelist());
         getCommand("tc").setExecutor(new Trustedchat());
        getCommand("tpa").setExecutor(new Tpa());
        getCommand("spawn").setExecutor(new Spawn());
        getCommand("tpaccept").setExecutor(new Tpaccept());
        getCommand("tpdeny").setExecutor(new Tpdeny());
        getCommand("kb").setExecutor(new Killboard());
        getCommand("spec").setExecutor(new Spec());
        getCommand("inspect").setExecutor(new Inspect(this));
        getCommand("tban").setExecutor(new Tban());
         getCommand("unban").setExecutor(new Unban());
        getCommand("parkour").setExecutor(new Parkour());
         getCommand("rank").setExecutor(new Rank(this));

         //Packet Listeners
        spb.onSpecPacket();

        ItemStack membrane = new ItemStack(Material.PHANTOM_MEMBRANE);
        ShapedRecipe membraneRecipe = new ShapedRecipe(membrane);
        membraneRecipe.shape("FC","SN");
        membraneRecipe.setIngredient('F', Material.ROTTEN_FLESH);
        membraneRecipe.setIngredient('C', Material.CHORUS_FRUIT);
        membraneRecipe.setIngredient('S', Material.STRING);
        getServer().addRecipe(membraneRecipe);
        
    }
    public void onDisable(){
        spb.stopBlockingPackets();
    }
    public static Main getInstance(){
        return plugin;
     }
    public static ProtocolManager getProtocolManager(){
        return pm;
    }

}
