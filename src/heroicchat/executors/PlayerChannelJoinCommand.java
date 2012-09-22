package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelJoinCommand {
	private HeroicChat plugin;
	public PlayerChannelJoinCommand(HeroicChat instance){
		plugin = instance;
	}
public boolean PlayerChannelJoin(CommandSender sender, Command cmd, String label, String[] arg){
		//hc join <name>
		ChannelManager cm = new ChannelManager(plugin);
		if(arg.length == 2) {
			if(arg[0].equalsIgnoreCase("join")) {
				String channel = arg[1];
				if(cm.channelExists(channel)) {
					
					Channel current = cm.getChannel(plugin.players.get(sender.getName()));
					if(current.equals(cm.getChannel(channel))) {
						sender.sendMessage(ChatColor.RED + "You are already in that channel");
						return true;
					}
					cm.getChannel(channel).broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
					
					cm.playerSwitchChannel((Player) sender, cm.getChannel(channel));
					
					current.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
					
					sender.sendMessage(ChatColor.GREEN + "Channel switched");
					
				}
				else {
					sender.sendMessage(ChatColor.RED + "That channel does not exist");
				}
				return true;
			}
		}
		return false;
	}
}
