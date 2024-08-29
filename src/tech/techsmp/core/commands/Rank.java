package tech.techsmp.core.commands;
import org.bukkit.GameMode;
import tech.techsmp.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.*;

import java.util.concurrent.ExecutionException;


public class Rank implements CommandExecutor {


    private static void giveCoreProtectPerms(Player p){
        PermissionAttachment attachment = p.addAttachment(Main.getInstance());

        attachment.setPermission("coreprotect.inspect", true);
        attachment.setPermission("coreprotect.rollback", true);
        attachment.setPermission("coreprotect.lookup", true);
        attachment.setPermission("coreprotect.teleport", true);

    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("Rank")) {
            if(sender.hasPermission("rank.admin")) {
                try {

                    if (args.length == 2 && (args[1].equalsIgnoreCase("trusted") || args[1].equalsIgnoreCase("admin") || args[1].equalsIgnoreCase("owner"))) {
                        givePlayerRank(Bukkit.getPlayer(args[0]), args[1]);
                    } else {
                        sender.sendMessage("§cError usage: /rank <player> <admin|trusted|owner>");
                    }
                }
                catch (Exception e){
                    sender.sendMessage("§cError player \"" + args[0] + "\" not found.");
                }
            }

        }
        return true;
    }
    public static void givePlayerRank(Player p, String rankName){
        if((rankName.equalsIgnoreCase("trusted") || rankName.equalsIgnoreCase("admin") || rankName.equalsIgnoreCase("owner"))){
            try{
                PermissionAttachment attachment = p.addAttachment(Main.getInstance());
                if(rankName.equalsIgnoreCase("trusted")){
                    attachment.setPermission("rank.admin", false);
                    attachment.setPermission("rank.owner", false);
                    attachment.setPermission("rank.trusted", true);
                    attachment.setPermission("hns.save", true);
                    attachment.setPermission("hns.reset", true);
                    attachment.setPermission("hns.reload", true);
                    attachment.setPermission("hns.game.create", true);
                    attachment.setPermission("hns.game.edit", true);
                    attachment.setPermission("hns.game.start", true);
                    attachment.setPermission("hns.game.stop", true);
                    giveCoreProtectPerms(p);
                }
                if(rankName.equalsIgnoreCase("admin")){
                    attachment.setPermission("rank.admin", true);
                    attachment.setPermission("rank.owner", false);
                    attachment.setPermission("rank.trusted", true);
                    attachment.setPermission("hns.save", true);
                    attachment.setPermission("hns.reset", true);
                    attachment.setPermission("hns.reload", true);
                    attachment.setPermission("hns.game.create", true);
                    attachment.setPermission("hns.game.edit", true);
                    attachment.setPermission("hns.game.start", true);
                    attachment.setPermission("hns.game.stop", true);
                    giveCoreProtectPerms(p);

                }
                if(rankName.equalsIgnoreCase("owner")){
                    attachment.setPermission("rank.admin", true);
                    attachment.setPermission("rank.owner", true);
                    attachment.setPermission("rank.trusted", true);
                    attachment.setPermission("hns.save", true);
                    attachment.setPermission("hns.reset", true);
                    attachment.setPermission("hns.reload", true);
                    attachment.setPermission("hns.game.create", true);
                    attachment.setPermission("hns.game.edit", true);
                    attachment.setPermission("hns.game.start", true);
                    attachment.setPermission("hns.game.stop", true);
                    giveCoreProtectPerms(p);

                }
            }
            catch(Exception e){
            }
        }
    }
}
