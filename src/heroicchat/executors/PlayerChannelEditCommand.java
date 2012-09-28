package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PlayerChannelEditCommand {
	private HeroicChat plugin;
	public PlayerChannelEditCommand(HeroicChat instance){
		plugin = instance;
	}
public boolean PlayerChannelEdit(CommandSender sender, Command cmd, String label, String[] arg){
		ChannelManager cm = new ChannelManager(plugin);
		if(arg.length == 4 ) {//hc edit <name> <property> <new value>
			if(arg[0].equalsIgnoreCase("edit")) {
				String cname = arg[1];
				String property = arg[2];
				String newvalue = arg[3];
				if(cm.channelExists(cname)) {
					if(!cname.equalsIgnoreCase("default")) {
						Channel c = cm.getChannel(cname);
						if(sender.hasPermission("heroicchat.channel.edit.all") || c.getOwner().equals(sender.getName())){
							if(sender.hasPermission("heroicchat.channel.edit.own")) {
								if(property.equalsIgnoreCase("locked")) {
									if(sender.hasPermission("heroicchat.channel.edit.password")) {
										if(newvalue.equalsIgnoreCase("true")) {
											c.setLocked(true);
											sender.sendMessage(ChatColor.GREEN + "Channel edited");
											return true;
										}
										if(newvalue.equalsIgnoreCase("false")) {
											c.setLocked(false);
											c.setPassword(null);
											sender.sendMessage(ChatColor.GREEN + "Channel edited");
											return true;
										}
									}
									else {
										sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
										return true;
									}
								}
								else if(property.equalsIgnoreCase("prefix")) {
									if(sender.hasPermission("heroicchat.channel.edit.prefix")) {
										c.setPrefix(newvalue);
										sender.sendMessage(ChatColor.GREEN + "Channel edited");
										return true;
									}
									else {
										sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
										return true;
									}
								}
								else if(property.equalsIgnoreCase("password")) {
									if(sender.hasPermission("heroicchat.channel.edit.password")) {
										if(!c.isLocked()) {
											c.setLocked(true);
										}
										c.setPassword(newvalue);
										sender.sendMessage(ChatColor.GREEN + "Channel edited");
										return true;
									}
									else {
										sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
										return true;
									}

								}
								else if(property.equalsIgnoreCase("permanent")) {
									if(sender.hasPermission("heroicchat.channel.edit.permanent")) {
										if(newvalue.equalsIgnoreCase("true")) {
											c.setPermanent(true);
											sender.sendMessage(ChatColor.GREEN + "Channel edited");
											return true;
										}
										if(newvalue.equalsIgnoreCase("false")) {
											c.setPermanent(false);
											return true;
										}
										return false;
									}
									else {
										sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
										return true;
									}
								}
								else if(property.equalsIgnoreCase("owner")) {
									if(sender.hasPermission("heroicchat.channel.edit.owner")) {
										c.setOwner(newvalue);
										sender.sendMessage(ChatColor.GREEN + "Channel edited");
										return true;
									}
									else {
										sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
									}
									
									
								}
								else {
									sender.sendMessage(ChatColor.RED + "That is not a property of a channel, the properties that you can change are: " +ChatColor.GRAY + "password, locked, permanent, prefix, owner");
									return true;
								}
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "You are not allowed to edit this channel");
						}
					}
					else {
						if(property.equalsIgnoreCase("prefix")) {
							if(sender.hasPermission("heroicchat.channel.edit.prefix")) {
								Channel c = cm.getChannel(cname);
								c.setPrefix(newvalue);
								sender.sendMessage(ChatColor.GREEN + "Channel edited");
								return true;
							}
							else {
								sender.sendMessage(ChatColor.RED + "You don't have permissions to do this");
								return true;
							}
						}	
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "That channel does not exist");
					return true;
				}
				
			}
		}
		
		return false;
	}
}
