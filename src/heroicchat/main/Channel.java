package heroicchat.main;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Channel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String owner;
	private String prefix;
	private String name;
	private ArrayList<String> receivers = new ArrayList<String>();
	private String password;
	private boolean locked;
	private boolean permanent;
	
	public Channel(String prefix, String name, String password, boolean locked, boolean permanent) {
		this.prefix = "["+prefix+"]";
		this.name = name;
		this.password = password;
		this.locked = locked;
		this.permanent = permanent;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void addReceiver(String name) {
		if(!receivers.contains(name)) {
			receivers.add(name);
		}
	}
	public void removeReceiver(String name) {
		if(receivers.contains(name)) {
			receivers.remove(name);
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getReceivers() {
		return receivers;
	}
	public void setReceivers(ArrayList<String> receivers) {
		this.receivers = receivers;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isPermanent() {
		return permanent;
	}
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	public void PlayerSaySomething(Player p, String message) {
		String finalmessage = "<"+prefix +" "+ p.getName()+ ">"+message;
		for(int i=0; i <receivers.size(); i++) {
			Bukkit.getPlayer(receivers.get(i)).sendMessage(finalmessage);
		}
	}
	public void broadcast(String message) {
		if(!receivers.isEmpty()) {
			for(int i=0; i<receivers.size();i++) {
				Bukkit.getPlayerExact(receivers.get(i)).sendMessage(message);
			}
		}
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
