package tech.techsmp.core.commands;
import tech.techsmp.core.Join.PlayerPreJoin;
import tech.techsmp.core.cosmetic.Chat;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.teleport(new Location(Bukkit.getWorld("world"), -95, 85, 0));
		p.sendMessage("§aTeleporting you to spawn...");
		return true;

	}
}
