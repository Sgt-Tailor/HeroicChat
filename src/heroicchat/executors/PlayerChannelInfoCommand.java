package heroicchat.executors;

import java.util.ArrayList;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelInfoCommand implements CommandExecutor{
	private HeroicChat plugin;
	public PlayerChannelInfoCommand(HeroicChat instance){
		plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		ChannelManager cm = new ChannelManager(plugin);
		if(sender instanceof Player) {
			if(arg[0].equalsIgnoreCase("info")) {
				if(arg.length ==1) {
					Player p = (Player) sender;
					Channel c = cm.getChannel(p);
					String name = c.getName();
					String locked = Boolean.toString(c.isLocked());
					String permanent = Boolean.toString(c.isPermanent());
					String members = Integer.toString(c.getMembers().size());
					String memberstring = ArrayListToMessage(c.getMembers());
					String owner = c.getOwner();
					p.sendMessage(ChatColor.GOLD + "----Info-About-Channel-"+name+"----");
					p.sendMessage(ChatColor.AQUA + "Owner: " + ChatColor.GRAY + owner);
					p.sendMessage(ChatColor.AQUA + "Permanent: " + ChatColor.GRAY + permanent);
					p.sendMessage(ChatColor.AQUA + "Locked: " + ChatColor.GRAY + locked);
					p.sendMessage(ChatColor.AQUA + "Members ("+members+"): " + ChatColor.GRAY + memberstring);
					return true;
				}
				if(arg.length ==2) {
					String channel = arg[1];
					if(cm.channelExists(channel)) {
						Player p = (Player) sender;
						Channel c = cm.getChannel(channel);
						String name = c.getName();
						String locked = Boolean.toString(c.isLocked());
						String permanent = Boolean.toString(c.isPermanent());
						String members = Integer.toString(c.getMembers().size());
						String memberstring = ArrayListToMessage(c.getMembers());
						String owner = c.getOwner();
						p.sendMessage(ChatColor.GOLD + "----Info-About-Channel-"+name+"----");
						p.sendMessage(ChatColor.AQUA + "Owner: " + ChatColor.GRAY + owner);
						p.sendMessage(ChatColor.AQUA + "Permanent: " + ChatColor.GRAY + permanent);
						p.sendMessage(ChatColor.AQUA + "Locked: " + ChatColor.GRAY + locked);
						p.sendMessage(ChatColor.AQUA + "Members ("+members+"): " + ChatColor.GRAY + memberstring);
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + "That channel does not exist");
						return true;
					}
				}
				
			}
		}
		return false;
	}
	public String ArrayListToMessage(ArrayList<String> list) {
		String message = "";
		for(int i=0; i<list.size(); i++) {
			if(i==0) {
				message = list.get(i);
			}
			else {
				message = message + ", " + list.get(i);
			}
		}
		
		return message;
	}

}
