package heroicchat.events.player;

import java.util.ArrayList;

import heroicchat.main.Channel;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChannelChatEvent extends Event {
	  private static final HandlerList handlers = new HandlerList();
	  private Player p;
	  private Channel c;
	  private String message;
	  private String format;
	  private ArrayList<Player> receivers;
	  private Boolean cancelled;
	  
	  public PlayerChannelChatEvent(Player p, Channel c, String message, String format, ArrayList<Player> receivers) {
		  setCancelled(false);
		  this.p = p;
		  this.c = c;
		  this.message = message;
		  this.format = format;
		  this.receivers = receivers;
	  }
	  
	  public Player getPlayer() {
		  return p;
	  }
	  public Channel getChannel() {
		  return c;
	  }
	  public String getMessage() {
		  return message;
	  }
	  public String getFormat() {
		  return format;
	  }
	  public ArrayList<Player> getReceivers(){
		  return receivers;
	  }
	  
	  
	  public HandlerList getHandlers() {
	        return handlers;
	    }
	     
	  public static HandlerList getHandlerList() {
	        return handlers;
	  }

	public Boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	

}
