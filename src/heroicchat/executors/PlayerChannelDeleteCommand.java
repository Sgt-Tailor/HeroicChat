package heroicchat.executors;

import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
					if(cm.getChannel(arg[1]).getOwner() == null || cm.getChannel(arg[1]).getOwner().equals(sender.getName())) {
						if(arg[1].equals("default")) {
							sender.sendMessage(ChatColor.RED + "You can not delete the default channel");
							return true;
						}
						else {
							cm.deleteChannel(arg[1]);
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}
