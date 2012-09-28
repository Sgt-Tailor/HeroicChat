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
	private ArrayList<String> members = new ArrayList<String>();
	private String password;
	private boolean locked;
	private boolean permanent;
	
	public Channel(String prefix, String name, String owner, String password, boolean locked, boolean permanent) {
		this.owner = owner;
		this.prefix = "["+prefix+"]";
		this.name = name;
		this.password = password;
		this.locked = locked;
		this.permanent = permanent;
	}
	public ArrayList<String> getMembers(){
		return members;
	}
	public void addMember(String name) {
		members.add(name);
	}
	public void removeMember(String name) {
		members.remove(name);
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
		if(!members.isEmpty()) {
			for(int i=0; i<members.size();i++) {
				Bukkit.getPlayerExact(members.get(i)).sendMessage(message);
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
