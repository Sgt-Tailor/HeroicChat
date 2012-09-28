package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerChannelSayCommand {
	private HeroicChat plugin;
	public PlayerChannelSayCommand(HeroicChat instance){
		plugin=instance;
	}
	public boolean playerChannelSayCommand(Player p, String[] arg) {
		if(arg.length > 3) { //hc say <name> <message>
			if(arg[0].equalsIgnoreCase("say")) {
				String cname = arg[1];
				ChannelManager cm = new ChannelManager(plugin);
				if(cm.channelExists(cname)) {
					if(p.hasPermission("hc.broadcast.channel")) {
						String message = "";
						for(int i = 2; i<arg.length; i++) {
							if(i==2) {
								message = arg[i];
							}
							else {
								message = message + " " + arg[i];
							}
						}
							Channel c = cm.getChannel(cname);
							c.broadcast("<"+p.getDisplayName()+"> " + message );	
							return true;
						
					}
				}
				else {
					p.sendMessage(ChatColor.RED + "That channel does not exist");
					return true;
				}
			}
		}
		return false;
	}
}
