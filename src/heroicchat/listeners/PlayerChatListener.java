package heroicchat.listeners;

import java.util.ArrayList;
import java.util.Set;

import heroicchat.events.player.PlayerChannelChatEvent;
import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		if(event.getPlayer().hasPermission("heroicchat.chat.colors")) {
			event.setMessage(removeColorsFromString(event.getMessage()));
		}
		
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

	private String removeColorsFromString(String message) {
		while(colorString(message) != null) {
		//	message = message.replaceAll(colorString(message), containsColors(message));
			message = message.split(colorString(message),2)[0] + containsColors(message) + message.split(colorString(message),2)[1];
		}
		return message;
	}
	private ChatColor containsColors(String message) {
		if(message.contains("&1")) {
			return ChatColor.DARK_BLUE;
		}
		if(message.contains("&2")) {
			return ChatColor.DARK_GREEN;
		}
		if(message.contains("&3")) {
			return ChatColor.DARK_AQUA;
		}
		if(message.contains("&4")) {
			return ChatColor.DARK_RED;
		}
		if(message.contains("&5")) {
			return ChatColor.DARK_PURPLE;
		}
		if(message.contains("&6")) {
			return ChatColor.GOLD;
		}
		if(message.contains("&7")) {
			return ChatColor.GRAY;
		}
		if(message.contains("&8")) {
			return ChatColor.DARK_GRAY;
		}
		if(message.contains("&9")) {
			return ChatColor.BLUE;
		}
		if(message.contains("&0")) {
			return ChatColor.BLACK;
		}
		if(message.contains("&a")) {
			return ChatColor.GREEN;
		}
		if(message.contains("&b")) {
			return ChatColor.AQUA;
		}
		if(message.contains("&c")) {
			return ChatColor.RED;
		}
		if(message.contains("&d")) {
			return ChatColor.LIGHT_PURPLE;
		}
		if(message.contains("&e")) {
			return ChatColor.YELLOW;
		}
		if(message.contains("&f")) {
			return ChatColor.WHITE;
		}
		
		return null;
	}
	private String colorString(String message) {
		if(message.contains("&1")) {
			return "&1";
		}
		if(message.contains("&2")) {
			return "&2";
		}
		if(message.contains("&3")) {
			return "&3";
		}
		if(message.contains("&4")) {
			return "&4";
		}
		if(message.contains("&5")) {
			return "&5";
		}
		if(message.contains("&6")) {
			return "&6";
		}
		if(message.contains("&7")) {
			return "&7";
		}
		if(message.contains("&8")) {
			return "&8";
		}
		if(message.contains("&9")) {
			return "&9";
		}
		if(message.contains("&0")) {
			return "&0";
		}
		if(message.contains("&a")) {
			return "&a";
		}
		if(message.contains("&b")) {
			return "&b";
		}
		if(message.contains("&c")) {
			return "&c";
		}
		if(message.contains("&d")) {
			return "&d";
		}
		if(message.contains("&e")) {
			return "&e";
		}
		if(message.contains("&f")) {
			return "&f";
		}
		
		return null;
	}
	private ArrayList<Player> ArrayListToPlayerList(ArrayList<String> receivers) {
		ArrayList<Player> players = new ArrayList<Player>();
		for(int i=0; i<receivers.size();i++) {
			players.add(Bukkit.getPlayerExact(receivers.get(i)));
		}
		return players;
		
	}

}
