package tech.techsmp.core.Packet;

import com.comphenix.net.bytebuddy.asm.ModifierAdjustment;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import tech.techsmp.core.Main;
import tech.techsmp.core.commands.Spec;
import tech.techsmp.core.commands.Vanish;
import utils.Teleporter;
import com.google.common.collect.ImmutableList;
import java.util.*;

public class SpecPacketBlocker {
    private static PacketListener specListener1ID;
    private static PacketListener specListener2ID;

    public void onSpecPacket(){
        Main.getProtocolManager().addPacketListener(new PacketAdapter(
                Main.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.PLAYER_INFO
        ) {

            @Override
            public void onPacketSending(PacketEvent event) {
                specListener1ID = this;
                Player p = event.getPlayer();
                PacketContainer pc = new PacketContainer(event.getPacketType());
                if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {


                    // multiple events share same packet object
                    event.setPacket(event.getPacket().shallowClone());
                    String gameMode = event.getPacket().getStrings().read(0);
                    Bukkit.broadcastMessage(gameMode);

                    PacketContainer packet = event.getPacket();
                    EnumSet<EnumWrappers.PlayerInfoAction> pInfoList = (EnumSet<EnumWrappers.PlayerInfoAction>) (packet.getModifier().getValues().get(0));
                    Iterator<EnumWrappers.PlayerInfoAction> actionIterator = pInfoList.iterator();
                    if (pInfoList.toArray()[0].toString() == EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE.toString()){


                        if(!event.getPlayer().hasPermission("rank.trusted")){
                            event.setCancelled(true);
                        }
                    }
                }
            }
        });
        Main.getProtocolManager().addPacketListener(new PacketAdapter(
                Main.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ARM_ANIMATION
        ) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                specListener2ID = this;
                Player p = event.getPlayer();
                if(p.getGameMode().equals(GameMode.SPECTATOR)){
                    try {
                        if(Spec.specOnLocation.containsKey(p)){
                            Block interactBlock = p.getTargetBlock(null, 5);
                            Bukkit.getServer().getScheduler().runTask(Main.getInstance(), () -> {
                                PlayerInteractEvent fakeEvent = new PlayerInteractEvent(p, Action.LEFT_CLICK_BLOCK, null, interactBlock, null);
                                Bukkit.getPluginManager().callEvent(fakeEvent);
                            });
                        }
                    }catch (Exception e){

                    }
                    /*Event e = new PlayerInteractEvent("HI");
                    Bukkit.getPluginManager().callEvent(PlayerInteractEvent);
                    List<Player> eligiblePlayers = new ArrayList<>();

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        Vanish v = new Vanish();
                        if (onlinePlayer.getGameMode() != GameMode.SPECTATOR && !v.isPlayerVanished(onlinePlayer)) {
                            eligiblePlayers.add(onlinePlayer);
                        }
                    }

                    if (eligiblePlayers.isEmpty()) {
                        p.sendMessage(ChatColor.RED + "No players to teleport to");
                        return;
                    }
                    Random random = new Random();
                    int randomIndex = random.nextInt(eligiblePlayers.size());
                    Player randomPlayer = eligiblePlayers.get(randomIndex);
                    p.sendMessage("§aTeleporting you to " + randomPlayer.getName());
                    Teleporter.teleport(p, randomPlayer.getLocation());*/

                }
            }
        });
    }
    public void stopBlockingPackets(){
        Main.getProtocolManager().removePacketListener(specListener1ID);
        Main.getProtocolManager().removePacketListener(specListener2ID);

    }
   /* public static void addPlayer(){
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoActions().write(0, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
        packet.getPlayerInfoDataLists().write(1, Collections.singletonList(new PlayerInfoData(
                new WrappedGameProfile("c08dc1a3-315b-433a-afaf-ab6c49cdeb6c", "Keelando"),
                100,
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText("Keelando")
        )));
    }*/
  /*  private PacketContainer getPacketPlayerInfo(Player player, EnumWrappers.PlayerInfoAction action) {
        List<PlayerInfoData> datas = new ArrayList<>();
        datas.add(getPlayerInfoData(player));
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, action);
        packet.getPlayerInfoDataLists().write(0, datas);
        return packet;
    }

    private PlayerInfoData getPlayerInfoData(Player player) {
        return new PlayerInfoData(WrappedGameProfile.fromPlayer(player),
                player.getPing(),
                EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                WrappedChatComponent.fromJson(player.getName() == null
                        ? player.getName()
                        : GsonComponentSerializer.gson().serialize(player.playerListName())));
    }*/
}
