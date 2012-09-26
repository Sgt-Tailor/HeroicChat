package heroicchat.managers;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChannelManager{
	public HeroicChat plugin;
	public ChannelManager(HeroicChat instance){
		plugin=instance;
	}
	
	public void createNewChannel(String name, String prefix, boolean locked, String password, boolean permanent) {
		if(!plugin.cnames.contains(name)) {
			Channel c = new Channel(prefix, name, password, locked, permanent);
			plugin.channels.put(name, c);
			plugin.cnames.add(name);
			return;
		}
		
	}
	
	public boolean channelExists(String name) {
		if(plugin.channels.containsKey(name)) {
			return true;
		}
		else {
			return false;
		}
	}
	public Channel getChannel(String name) {
		if(plugin.channels.containsKey(name)) {
			return plugin.channels.get(name);
		}
		return null;
		
	}
	public Channel getChannel(Player p) {
		String cname = plugin.players.get(p.getName());
		Channel c = getChannel(cname);
		return c;		
	}
	public void playerSwitchChannel(Player p, Channel newer) {
		Channel old = getChannel(plugin.players.get(p.getName()));
		old.removeReceiver(p.getName());
		if(old.getReceivers().size() == 0) {
			if(!old.isPermanent()) {
			deleteChannel(old.getName());
			}
		}
		plugin.players.put(p.getName(), newer.getName());
		newer.addReceiver(p.getName());
	}
	public void deleteChannel(String name) {
		Channel c = getChannel(name);
		c.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA +"This channel has been deleted, you have been moved to the default channel");
		ArrayList<String> players = c.getReceivers();
		int i=0;
		while(i<players.size()) {
			Channel defaultc = getChannel("default");
			String player = players.get(i);
			plugin.players.put(player, "default");
			defaultc.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA +player+" has joined your channed your channel");
			defaultc.addReceiver(players.get(i));
			
			i++;
		}
		if(name.equals("default")) {
			return;
		}
		plugin.cnames.remove(name);
		plugin.channels.remove(name);
		
		
	}
	

}
