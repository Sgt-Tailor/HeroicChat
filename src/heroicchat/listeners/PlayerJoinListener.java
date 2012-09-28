package heroicchat.listeners;

import java.util.ArrayList;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener{
	private HeroicChat plugin;
	public PlayerJoinListener(HeroicChat instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		String name = event.getPlayer().getName();
		ChannelManager cm = new ChannelManager(plugin);
		if(!plugin.receivelist.containsKey(p.getName())) {
			ArrayList<String> newlist = new ArrayList<String>();
			plugin.receivelist.put(p.getName(), newlist);
		}
		if(!plugin.players.containsKey(name)) {
			Channel c = cm.getChannel("default");
			plugin.players.put(name, c.getName());
			
			c.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + name + " has joined your channel");
			c.addReceiver(name);
			p.sendMessage(ChatColor.YELLOW + "You have been put in the default channel");
			return;
			
		}
		if(!cm.channelExists(plugin.players.get(name))) {
			Channel c = cm.getChannel("default");
			plugin.players.put(name, "default");
			
			c.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + name + " has joined your channel");
			c.addReceiver(name);
			p.sendMessage(ChatColor.YELLOW + "You have been put in the default channel");
			return;
		}
		else {
			Channel c = cm.getChannel(plugin.players.get(name));
			String cname = c.getName();
			c.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + name + " has joined your channel");
			c.addReceiver(name);
			event.getPlayer().sendMessage(ChatColor.YELLOW + "You have been put in the channel: " +ChatColor.GREEN + cname);
			return;
		}
	}

}
