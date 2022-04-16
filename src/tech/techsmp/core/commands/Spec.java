package tech.techsmp.core.commands;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spec implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("rank.trusted")){
            sender.sendMessage("§cSorry! you do not have permission to use this command");
            return true;
        }
        if(args.length == 1){
            if(sender instanceof Player){
                if(args[0].equalsIgnoreCase("on")){
                    Player p = (Player) sender;
                    p.setGameMode(GameMode.SPECTATOR);
                    p.setPlayerListName(p.getDisplayName());
                }
                if(args[0].equalsIgnoreCase("off")){
                    Player p = (Player) sender;
                    p.setGameMode(GameMode.SURVIVAL);
                    p.setPlayerListName("§f§r" + p.getName());
                }
            }
            else{
                sender.sendMessage("§cError usage: /spec <on|off> player");
            }
        }
        else if(args.length == 2){
            try{
                Player p =  Bukkit.getServer().getPlayer(args[1]);
                if(args[0].equalsIgnoreCase("on")){
                    p.setGameMode(GameMode.SPECTATOR);
                    p.setPlayerListName(p.getDisplayName());
                    p.sendMessage("§7You are now in spectator mode");
                    sender.sendMessage("§aSuccessfully put " + p.getName() + " into spectator mode");
                }
                if(args[0].equalsIgnoreCase("off")){
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage("§7 You are no longer in spectator mode");
                    sender.sendMessage("§aSuccessfully taken " + p.getName() + " out of spectator mode");
                }
            }
            catch(Exception e){
                sender.sendMessage("§cError: Player not found");
            }
        }
        else{
            sender.sendMessage("§cError usage: /spec <on|off> player");
        }
    
        return true;
    }
}
