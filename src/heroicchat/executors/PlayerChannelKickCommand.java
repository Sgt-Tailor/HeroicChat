package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelKickCommand {
	private HeroicChat plugin;
	public PlayerChannelKickCommand(HeroicChat instance){
		plugin=instance;
	}
	public boolean PlayerChannelKick(CommandSender sender, Command cmd, String label, String[] arg){
		ChannelManager cm = new ChannelManager(plugin);
		if(arg[0].equalsIgnoreCase("kick")) {
			String player = arg[1];
			Player p = getPlayerFromString(player);
			Channel oldc = cm.getChannel(p);
			if(oldc.getOwner().equalsIgnoreCase(sender.getName())) {
				if(!sender.hasPermission("heroicchat.channel.kick.own")) {
					sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
					return true;
				}
				
			}
			else if(!sender.hasPermission("heroicchat.channel.kick.others")) {
				sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
				return true;
			}
			if(sender.hasPermission("heroicchat.player.kick")) {
				if(arg.length ==2) {//hc kick <player>
					
					if(playerIsOnline(player)) {
						
						Channel defaultc = cm.getChannel("default");
						
						
						defaultc.broadcast(ChatColor.GREEN + "[HeroicChat] " +ChatColor.AQUA + p.getName() + " has joined your channel");
						p.sendMessage(ChatColor.RED + "You have been kick from the channel");
						cm.playerSwitchChannel(p, defaultc);
						oldc.broadcast(ChatColor.GREEN + "[HeroicChat] " + ChatColor.AQUA + p.getName() + " has been kickd from the channel");
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "That player is not online");
						return true;
					}
					
				}
				
			}
		}
		
		return false;
		
	}
	public boolean playerIsOnline(String player) {
		
			Player[] op = Bukkit.getOnlinePlayers();
			for(int k=0; k<op.length; k++) {//for every online player
				if(op[k].getName().equals(player)) {
					return true;
				}
			}
			return false;
		
	}
	public Player getPlayerFromString(String p) {
		Player[] op = Bukkit.getOnlinePlayers();
		for(int k=0; k<op.length; k++) {//for every online player
			if(op[k].getName().equals(p)) {
				return op[k];
			}
		}
		
		return null;
	}

}
