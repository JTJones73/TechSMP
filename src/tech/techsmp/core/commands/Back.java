package tech.techsmp.core.commands;
import tech.techsmp.core.Listeners.PlayerTeleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.ConfigMessage;
import utils.Teleporter;

public class Back implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("back")){
			Player p = (Player) sender;
			if(PlayerTeleport.playerLastTpByUUID.containsKey(p.getUniqueId().toString())) {
				p.sendMessage(ConfigMessage.getMessage("BACK_TELEPORT", new String[]{" "}));
				Teleporter.teleport(p, PlayerTeleport.playerLastTpByUUID.get(p.getUniqueId().toString()));
				//p.teleport(PlayerTeleport.playerLastTpByUUID.get(p.getUniqueId().toString()));
			}
			else {
				p.sendMessage(ConfigMessage.getMessage("BACK_FAILED", new String[]{" "}));
			}
        }  
        return true;  
    }
}