package heroicchat.executors;

import heroicchat.main.Channel;
import heroicchat.main.HeroicChat;
import heroicchat.managers.ChannelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GeneralCommand implements CommandExecutor {
	/*  /heroicchat createpermanent <name> <password>
	 *  /heroicchat create <name>
	 *  /heroicchat create <name> <password>
	 *  /heroicchat join <name>
	 *  /heroicchat join <name> <password>
	 *  /heroicchat edit <name> <property> <newvalue>
	 *  /heroicchat delete <name> 

	 */
	private HeroicChat plugin;
	public GeneralCommand(HeroicChat instance){
		plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		ChannelManager cm = new ChannelManager(plugin);
		if(cmd.getName().equals("heroicchat")){
			if(arg.length == 0) {
				//return help message
			}
			if(arg.length > 0) {
				if(arg[0].equalsIgnoreCase("createpermanent")) {
					PlayerChannelCreateCommand executor = new PlayerChannelCreateCommand(plugin);
					if(executor.PlayerChannelCreate(sender, cmd, label, arg)) {
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("create")) {
					PlayerChannelCreateCommand executor = new PlayerChannelCreateCommand(plugin);
					if(executor.PlayerChannelCreate(sender, cmd, label, arg)) {
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("join")) {
					if(sender instanceof Player) {
						PlayerChannelJoinCommand executor = new PlayerChannelJoinCommand(plugin);
						if(executor.PlayerChannelJoin(sender, cmd, label, arg)) {
							return true;
						}
					}
					else {
						sender.sendMessage("This command can only be ran as a player");
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("leave")) {
					PlayerChannelJoinCommand executor = new PlayerChannelJoinCommand(plugin);
					if(executor.PlayerChannelJoin(sender, cmd, label, arg)) {
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("edit")) {
					PlayerChannelEditCommand executor = new PlayerChannelEditCommand(plugin);
					if(executor.PlayerChannelEdit(sender, cmd, label, arg)) {
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("delete")) {
					PlayerChannelDeleteCommand executor = new PlayerChannelDeleteCommand(plugin);
					if(executor.PlayerChannelDelete(sender, cmd, label, arg)) {
						return true;
					}
				}
				if(arg[0].equalsIgnoreCase("list")) {
					sender.sendMessage(ChatColor.GOLD + "-----List-of-Channels-----");
					for(int i=0; i<plugin.cnames.size(); i++) {
						Channel c = cm.getChannel(plugin.cnames.get(i));
						String name = c.getName();
						String amountofmembers = Integer.toString(c.getReceivers().size());
						String locked = Boolean.toString(c.isLocked());
						String permanent = Boolean.toString(c.isPermanent());
						sender.sendMessage(ChatColor.AQUA+name+ChatColor.GRAY + " --- members: "+amountofmembers+", locked: "+locked+ ", permanent: " +permanent);
					}
					return true;
				}
				if(arg[0].equalsIgnoreCase("info")) {
					if(sender instanceof Player) {
						PlayerChannelInfoCommand executor = new PlayerChannelInfoCommand(plugin);
						if(executor.onCommand(sender, cmd, label, arg)) {
							return true;
						}
					}
				}
			}
			sender.sendMessage(ChatColor.GOLD + "----HeroicChat-Help-Message----");
			sender.sendMessage(ChatColor.GOLD + "/hc createpermanent <name>" + ChatColor.GRAY + " create a permanent channel");
			sender.sendMessage(ChatColor.GOLD + "/hc create <name>"+ ChatColor.GRAY + " create a temporary channel");
			sender.sendMessage(ChatColor.GOLD + "/hc edit <name> <property> <new value>" + ChatColor.GRAY + " edit a channel");
			sender.sendMessage(ChatColor.GOLD + "/hc delete <name>" + ChatColor.GRAY + " delete a channel channel");
			sender.sendMessage(ChatColor.GOLD + "/hc list" + ChatColor.GRAY + " list all channels");
			sender.sendMessage(ChatColor.GOLD + "/hc info (name)" + ChatColor.GRAY + " display info about a channel");
			sender.sendMessage(ChatColor.GOLD + "/hc join <name> (password)" + ChatColor.GRAY + " join a channel");
			sender.sendMessage(ChatColor.GOLD + "/hc leave" + ChatColor.GRAY + " exit to the default channel");
			return true;
			
		}
		return false;
	}

}
