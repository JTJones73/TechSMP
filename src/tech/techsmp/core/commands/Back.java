package tech.techsmp.core.commands;
import tech.techsmp.core.Listeners.PlayerTeleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("back")){
			Player p = (Player) sender;
			if(PlayerTeleport.playerLastTpByUUID.containsKey(p.getUniqueId().toString())) {
				p.sendMessage("§aTeleporting...");
				p.teleport(PlayerTeleport.playerLastTpByUUID.get(p.getUniqueId().toString()));
			}
			else {
				p.sendMessage("§cSorry we do not remember where you were. The question is do you?");
			}
        }  
        return true;  
    }
}