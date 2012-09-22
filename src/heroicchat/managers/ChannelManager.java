package heroicchat.managers;

import org.bukkit.entity.Player;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;

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
	public void playerSwitchChannel(Player p, Channel newer) {
		Channel old = getChannel(plugin.players.get(p.getName()));
		old.removeReceiver(p.getName());
		if(old.getReceivers().size() == 0) {
			deleteChannel(old.getName());
		}
		plugin.players.put(p.getName(), newer.getName());
		newer.addReceiver(p.getName());
	}
	public void deleteChannel(String name) {
		if(name.equals("default")) {
			return;
		}
		plugin.cnames.remove(name);
		plugin.channels.remove(name);
		
		
	}
	

}
