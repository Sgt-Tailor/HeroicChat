package heroicchat.events.channel;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChannelCreateEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private String channel;
    private Boolean cancelled;
    
    public ChannelCreateEvent(Player p, String c) {
    	player = p;
    	channel = c;
    	cancelled = false;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public Player getPlayer() {
		return player;
	}
	public String getChannel() {
		return channel;
	}
	public Boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(Boolean cancel) {
		this.cancelled = cancel;
	}

}
