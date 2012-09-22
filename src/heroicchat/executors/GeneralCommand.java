package heroicchat.executors;

import heroicchat.main.HeroicChat;

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
						sender.sendMessage(plugin.cnames.get(i));
					}
					return true;
				}
			}
		}
		return false;
	}

}
