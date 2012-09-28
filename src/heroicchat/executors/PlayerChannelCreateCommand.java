package heroicchat.executors;

import heroicchat.events.channel.ChannelCreateEvent;
import heroicchat.events.player.PlayerSwitchChannelEvent;
import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerChannelCreateCommand {
	private HeroicChat plugin;
	public PlayerChannelCreateCommand(HeroicChat instance){
		plugin=instance;
	}
	public boolean PlayerChannelCreate(CommandSender sender, Command cmd, String label, String[] arg){
		ChannelManager cm = new ChannelManager(plugin);
		///heroicchat createpermanent <name> <password>
		// /heroicchat createpermanent <name>
		///heroicchat create <name>
		///heroicchat create <name> <password>
		if(arg.length == 2) {
			if(arg[0].equalsIgnoreCase("create")) {
				if(sender.hasPermission("heroicchat.channel.create")) {
					String cname = arg[1];
					if(cm.channelExists(cname)) {
						sender.sendMessage(ChatColor.RED + "That channel does already exist");
						return true;
					}
					else {
						Player p = (Player) sender;
						ChannelCreateEvent event = new ChannelCreateEvent(p, cname);
						
						Bukkit.getServer().getPluginManager().callEvent(event);
						
						if(!event.isCancelled()) {
							cm.createNewChannel(cname, cname,sender.getName(), false, null, false);
							Channel current = cm.getChannel(plugin.players.get(sender.getName()));
							Channel newer = cm.getChannel(cname);
							PlayerSwitchChannelEvent pevent = new PlayerSwitchChannelEvent(p, current, newer);
							Bukkit.getPluginManager().callEvent(pevent);
							
							if(!pevent.isCancelled()) {
								
								
								sender.sendMessage(ChatColor.GREEN + "Channel switched");
								newer.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
								cm.playerSwitchChannel((Player) sender, cm.getChannel(cname));
								current.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
							}
							return true;
						}
						
						
					}
					return false;
				}
			}
			if(arg[0].equalsIgnoreCase("createpermanent")) {
				if(sender.hasPermission("heroicchat.channel.createpermanent")) {
					String cname = arg[1];
					if(cm.channelExists(cname)) {
						sender.sendMessage(ChatColor.RED + "That channel does already exist");
						return true;
					}
					else {
						Player p = (Player) sender;
						ChannelCreateEvent event = new ChannelCreateEvent(p, cname);
						
						Bukkit.getServer().getPluginManager().callEvent(event);
						
						if(!event.isCancelled()) {
							cm.createNewChannel(cname, cname,sender.getName(), false, null, true);
							Channel current = cm.getChannel(plugin.players.get(sender.getName()));
							Channel newer = cm.getChannel(cname);
							PlayerSwitchChannelEvent pevent = new PlayerSwitchChannelEvent(p, current, newer);
							Bukkit.getPluginManager().callEvent(pevent);
							
							if(!pevent.isCancelled()) {
								
								
								sender.sendMessage(ChatColor.GREEN + "Channel switched");
								newer.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has joined your channel");
								cm.playerSwitchChannel((Player) sender, cm.getChannel(cname));
								current.broadcast(ChatColor.GREEN+"[HeroicChat] " + ChatColor.DARK_AQUA + sender.getName() + " has left your channel");
							}
							return true;
						}
						
						
					}
				
				}
			}
		}
		return false;
	}

}
