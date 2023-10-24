package tech.techsmp.core.commands;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import utils.ConfigMessage;

public class Fullbright implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2160000, 1));
				p.sendMessage(ConfigMessage.getMessage("FULLBRIGHT_ON", new String[]{" "}));
			}
			else if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
				p.sendMessage(ConfigMessage.getMessage("FULLBRIGHT_OFF", new String[]{" "}));
			}
	
		}
		return true;
	}

}
