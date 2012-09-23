package heroicchat.events.player;

import heroicchat.main.Channel;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSwitchChannelEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Channel old;
    private Channel newer;
    private Boolean cancelled;
    
    public PlayerSwitchChannelEvent(Player p, Channel old, Channel newer) {
    	cancelled = false;
    	player = p;
    	this.old = old;
    	this.newer = newer;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
	public Channel getNewChannel() {
		return newer;
	}
	public Channel getOldChannel() {
		return old;
	}
	public Player getPlayer() {
		return player;
	}
	public Boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
