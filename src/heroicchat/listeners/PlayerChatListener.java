package heroicchat.listeners;

import java.util.ArrayList;
import java.util.Set;

import heroicchat.events.player.PlayerChannelChatEvent;
import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener{
	private HeroicChat plugin;
	public PlayerChatListener(HeroicChat instance){
		plugin = instance;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		ChannelManager cm = new ChannelManager(plugin);
		Channel c = cm.getChannel(plugin.players.get(event.getPlayer().getName()));
		ArrayList<String> receivers = c.getReceivers();
		ArrayList<Player> players = ArrayListToPlayerList(receivers);
		if(plugin.config.getBoolean("show-channel-prefix")) {
		event.setFormat( c.getPrefix() + event.getFormat());
		}
		PlayerChannelChatEvent playerevent = new PlayerChannelChatEvent(event.getPlayer(), c, event.getMessage(), event.getFormat(), players);//create new event
		Bukkit.getPluginManager().callEvent(playerevent);//call playerevent
		
		if(!playerevent.isCancelled()) {//if it is cancelled the playerchatevent will be cancelled as well
			Set<Player> rece = event.getRecipients();
			rece.clear();//remove all current recipients
			

			for(int i=0; i<players.size(); i++) {//add all channel receivers
				Player p = players.get(i);
				event.getRecipients().add(p);
			}
			return;
		}
		else {
			event.setCancelled(true);
			return;
		}
		
	}

	private ArrayList<Player> ArrayListToPlayerList(ArrayList<String> receivers) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(int i=0; i<receivers.size();i++) {
			players.add(Bukkit.getPlayerExact(receivers.get(i)));
		}
		return players;
		
	}

}
