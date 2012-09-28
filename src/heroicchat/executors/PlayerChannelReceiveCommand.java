package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerChannelReceiveCommand {
	private HeroicChat plugin;
	public PlayerChannelReceiveCommand(HeroicChat instance){
		plugin=instance;
	}
	public boolean playerRecieveCommand(Player p, String[] arg) {
		//hc receive <name>
				ChannelManager cm = new ChannelManager(plugin);
				if(arg[0].equalsIgnoreCase("receive")) {
					if(arg.length == 2) {//hc receive <name>
					
						String channel = arg[1];
						if(cm.channelExists(channel)) {
							Channel newer = cm.getChannel(channel);
							if(newer.isLocked()) {
								if(!p.hasPermission("heroicchat.channel.bypasslock")) {
									p.sendMessage(ChatColor.RED + "That channel is locked, instead to /hc receive <name> <password>");
									return true;
								}
							}
								newer.addReceiver(p.getName());
								plugin.receivelist.get(p.getName()).add(channel);
								p.sendMessage(ChatColor.GREEN + "You are now listening to this channel. You can not send messages directly");
								return true;
							
							
							
						}
						else {
							p.sendMessage(ChatColor.RED + "That channel does not exist");
						}
						return true;
					}
					
					
				
					if(arg.length == 3) {//hc receive <name> <password>
						String channel = arg[1];
						if(cm.channelExists(channel)) {
							Channel c = cm.getChannel(channel);
							String cpass = c.getPassword();
							String pass = arg[2];
							if(cpass.equals(pass)){
								plugin.receivelist.get(p.getName()).add(channel);
								p.sendMessage(ChatColor.GREEN + "You are now listening to this channel. You can not send messages directly");
								
							}
							else {
								p.sendMessage(ChatColor.RED + "Password incorrect");
								return true;
							}
							
							
						}
						else {
							p.sendMessage(ChatColor.RED + "That channel does not exist");
						}
						return true;
						
					}
				}
				if(arg.length > 0) {
					if(arg[0].equalsIgnoreCase("stopreceive")){ //hc stopreceive (<name>)
						if(arg.length == 2) {
							String cname = arg[1];
							if(cm.channelExists(cname)) {
								Channel c = cm.getChannel(cname);
								if(c.getReceivers().contains(p.getName())) {
									c.removeReceiver(p.getName());
									p.sendMessage(ChatColor.GREEN + "You are now longer listening to that channel");
								}
								
								else {
									p.sendMessage(ChatColor.RED + "You are not listening to that channel");
									return true;
								}
								
							}
							else {
								p.sendMessage(ChatColor.RED + "That channel does not exist");
								return true;
							}
							
						}
						if(arg.length ==1) {
							if(arg[0].equalsIgnoreCase("stopreceive")) {
								for(int i=0; i<plugin.receivelist.get(p.getName()).size(); i++) {
									String cname = plugin.receivelist.get(p.getName()).get(i);
									Channel c = cm.getChannel(cname);
									c.removeReceiver(p.getName());
								}
								p.sendMessage(ChatColor.GREEN + "You are  now not listening to any channels anymore");
								return true;
							}
						}
					}
				}
				
			
		
		
		return false;
	}
}