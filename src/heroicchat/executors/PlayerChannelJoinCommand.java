package heroicchat.executors;

import heroicchat.events.player.PlayerSwitchChannelEvent;
import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelJoinCommand {
	private HeroicChat plugin;
	public PlayerChannelJoinCommand(HeroicChat instance){
		plugin = instance;
	}
public boolean PlayerChannelJoin(CommandSender sender, Command cmd, String label, String[] arg){
		//hc join <name>
		ChannelManager cm = new ChannelManager(plugin);
		if(arg.length == 2) {//hc join <name>
			if(arg[0].equalsIgnoreCase("join")) {
				String channel = arg[1];
				if(cm.channelExists(channel)) {
					Player p = (Player) sender;
					Channel old = cm.getChannel(p);
					Channel newer = cm.getChannel(channel);
					if(newer.isLocked()) {
						if(!sender.hasPermission("heroicchat.channel.bypasslock")) {
							sender.sendMessage(ChatColor.RED + "That channel is locked");
							return true;
						}
					}
					PlayerSwitchChannelEvent event = new PlayerSwitchChannelEvent(p, old, newer);
					Bukkit.getServer().getPluginManager().callEvent(event);
					
					if(!event.isCancelled()) {
						if(old.equals(newer)) {
							sender.sendMessage(ChatColor.RED + "You are already in that channel");
							return true;
						}
						cm.getChannel(channel).broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
						
						cm.playerSwitchChannel((Player) sender, cm.getChannel(channel));
						
						old.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
						
						sender.sendMessage(ChatColor.GREEN + "Channel switched");
					}
					
					
				}
				else {
					sender.sendMessage(ChatColor.RED + "That channel does not exist");
				}
				return true;
			}
			
			
		}
		if(arg.length == 3) {//hc join <name> <password>
			String channel = arg[1];
			if(cm.channelExists(channel)) {
				
				Player p = (Player) sender;
				Channel old = cm.getChannel(p);
				Channel newer = cm.getChannel(channel);
				String password = arg[2];
				String cpassword = newer.getPassword();
				if(password.equals(cpassword)) {
					PlayerSwitchChannelEvent event = new PlayerSwitchChannelEvent(p, old, newer);
					Bukkit.getServer().getPluginManager().callEvent(event);
					
					if(!event.isCancelled()) {
						if(old.equals(newer)) {
							sender.sendMessage(ChatColor.RED + "You are already in that channel");
							return true;
						}
						cm.getChannel(channel).broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
						
						cm.playerSwitchChannel((Player) sender, cm.getChannel(channel));
						
						old.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
						
						sender.sendMessage(ChatColor.GREEN + "Channel switched");
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Password incorrect");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "That channel does not exist");
			}
			return true;
			
		}
		if(arg.length == 1) {
			if(arg[0].equalsIgnoreCase("leave")) {
				Player p = (Player) sender;
				Channel old = cm.getChannel(p);
				Channel newer = cm.getChannel("default");
				PlayerSwitchChannelEvent event = new PlayerSwitchChannelEvent(p, old, newer);
				Bukkit.getServer().getPluginManager().callEvent(event);
				
				if(!event.isCancelled()) {
					if(old.equals(newer)) {
						sender.sendMessage(ChatColor.RED + "You are already in that channel");
						return true;
					}
					cm.getChannel("default").broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
					
					cm.playerSwitchChannel((Player) sender, cm.getChannel("default"));
					
					old.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
					
					sender.sendMessage(ChatColor.GREEN + "Channel switched");
					return true;
				}
				return true;
				
			}
		}
		return false;
	}
}
