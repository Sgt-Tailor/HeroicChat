package heroicchat.listeners;

import java.util.ArrayList;
import java.util.Set;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener{
	private HeroicChat plugin;
	public PlayerChatListener(HeroicChat instance){
		plugin = instance;
	}
	
	@EventHandler 
	public void onPlayerChat(AsyncPlayerChatEvent event){
		ChannelManager cm = new ChannelManager(plugin);
		Channel c = cm.getChannel(plugin.players.get(event.getPlayer().getName()));
		ArrayList<String> receivers = c.getReceivers();
		Set<Player> rece = event.getRecipients();
		rece.clear();
		for(int i=0; i<receivers.size(); i++) {
			Player p = Bukkit.getPlayer(receivers.get(i));
			event.getRecipients().add(p);
		}
		
	}

}
