package heroicchat.listeners;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectListener implements Listener {
	private HeroicChat plugin;
	public PlayerDisconnectListener(HeroicChat instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		ChannelManager cm = new ChannelManager(plugin);
		Player p = event.getPlayer();
		String name = p.getName();
		Channel c = cm.getChannel(plugin.players.get(name));
		c.removeReceiver(name);
		c.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + name + " has left your channel");
		if(c.getReceivers().size() == 0) {
			cm.deleteChannel(c.getName());
		}
		
	}
	
}
