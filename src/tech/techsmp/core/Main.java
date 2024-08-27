/*
*   Credits:        JT Jones, Drew Phelps, Metype
*   Description:    This is the main class that handles all initialization that allows the TechSMP Core to run.
*                   To add a command initialize the command in this file, add the command to plugin.yml, and then add a class for the command registration
*                   To add an event handler register the event handler in this file and add the event handler class
*                   If you are manipulating packets well then you understand what this file does.
*                   To reference this class use Main.getInstance(); To get Protocol Manager use Main.getProtocolManager()
* */


package tech.techsmp.core;
import org.bukkit.Location;
import tech.techsmp.core.Packet.SpecPacketBlocker;
import tech.techsmp.core.cosmetic.*;
import tech.techsmp.core.commands.*;
import tech.techsmp.core.Listeners.*;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;


import tech.techsmp.core.Join.*;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import utils.*;


public class Main extends JavaPlugin implements Listener{
    static ProtocolManager pm;
    public static Location spawnLoc = new Location(Bukkit.getWorld("world"), -95, 85,0);

        static Main plugin;
        SpecPacketBlocker spb = new SpecPacketBlocker();

	public void onEnable(){
                pm = ProtocolLibrary.getProtocolManager();

                plugin = this;

        //Start runnables
        TimeController.timerTask();

        //Load configuration values
        ConfigMessage.loadMessages();
        ConfigHandler.loadConfig();

        spawnLoc = new Location(Bukkit.getWorld(ConfigHandler.getString("spawn_world")),ConfigHandler.getInt("spawn_x"),
                ConfigHandler.getInt("spawn_y"),ConfigHandler.getInt("spawn_z"));

        Bukkit.getConsoleSender().sendMessage("TechSMP By James Jones");

        /*
        *   Event Registration - Register event listeners here
        * */
        Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPostJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SleepingPercent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PreCmdListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MineAlerts(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityHurtListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AFKCheck(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinAndLeaveMessage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Teleporter(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GriefListener(), this);
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
        Bukkit.getServer().getPluginManager().registerEvents(new EventModeListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new HighSpeedRail(), this);



        /*
        *   Command Registration - Register all commands here
        * */
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
        getCommand("event").setExecutor(new Event());


        /*
        *   Initialize packet listeners here
        *   Only one exists for now.
        * */
        spb.onSpecPacket();

        //Crafting recipe for phantom alternative - This may need to be removed
        ItemStack membrane = new ItemStack(Material.PHANTOM_MEMBRANE);
        ShapedRecipe membraneRecipe = new ShapedRecipe(membrane);
        membraneRecipe.shape("FC","SN");
        membraneRecipe.setIngredient('F', Material.ROTTEN_FLESH);
        membraneRecipe.setIngredient('C', Material.CHORUS_FRUIT);
        membraneRecipe.setIngredient('S', Material.STRING);
        getServer().addRecipe(membraneRecipe);
        
    }
    public void onDisable(){

        /*
        *   Kill packet listeners
        * */
        spb.stopBlockingPackets();
    }
    public static Main getInstance(){
        return plugin;
     }

    public static ProtocolManager getProtocolManager(){
        return pm;
    }

}
