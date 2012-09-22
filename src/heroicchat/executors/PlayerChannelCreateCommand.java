package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelCreateCommand {
	public HeroicChat plugin;
	public PlayerChannelCreateCommand(HeroicChat instance){
		plugin=instance;
	}
	public boolean PlayerChannelCreate(CommandSender sender, Command cmd, String label, String[] arg){
		ChannelManager cm = new ChannelManager(plugin);
		///heroicchat createpermanent <name> <password>
		// /heroicchat createpermanent <name>
		///heroicchat create <name>
		///heroicchat create <name> <password>
		if(arg.length == 2) {
			if(arg[0].equalsIgnoreCase("create")) {
				String name = arg[1];
				if(cm.channelExists(name)) {
					sender.sendMessage(ChatColor.RED + "That channel does already exist");
					return true;
				}
				else {
					cm.createNewChannel(name, "["+name+"]", false, null, false);
					sender.sendMessage(ChatColor.GREEN + "Channel switched");
					Channel current = cm.getChannel(plugin.players.get(sender.getName()));
					cm.playerSwitchChannel((Player) sender, cm.getChannel(name));
					current.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
					return true;
				}
			}
		}
		return false;
	}

}
