package heroicchat.executors;

import heroicchat.events.channel.ChannelDeleteEvent;
import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelDeleteCommand {
	private HeroicChat plugin;
	public PlayerChannelDeleteCommand(HeroicChat instance){
		plugin = instance;
	}
	public boolean PlayerChannelDelete(CommandSender sender, Command cmd, String label, String[] arg){
		ChannelManager cm = new ChannelManager(plugin);
		if(arg.length == 2) {
			if(arg[0].equals("delete")) {
				if(cm.channelExists(arg[1])) {
					Channel c = cm.getChannel(arg[1]);
					if(c.getOwner().equals(sender.getName())) {
						if(!sender.hasPermission("heroicchat.channel.delete.own")) {
							
							return true;
						}
					}
					else if(!sender.hasPermission("heroicchat.channel.delete.others")){
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this");
						return true;
					}
					if(cm.getChannel(arg[1]).getOwner() == null || cm.getChannel(arg[1]).getOwner().equals(sender.getName())) {
						if(arg[1].equals("default")) {
							sender.sendMessage(ChatColor.RED + "You can not delete the default channel");
							return true;
						}
						else {
							ChannelDeleteEvent event = new ChannelDeleteEvent((Player) sender, arg[1]);
							Bukkit.getPluginManager().callEvent(event);
							if(!event.isCancelled()) {
								cm.deleteChannel(arg[1]);
								sender.sendMessage(ChatColor.GREEN + "Channel deleted");
								return true;
							}
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}
