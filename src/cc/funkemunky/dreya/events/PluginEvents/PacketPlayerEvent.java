package cc.funkemunky.dreya.events.PluginEvents;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketPlayerEvent
extends Event {
    private Player Player;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private static final HandlerList handlers = new HandlerList();

    public PacketPlayerEvent(Player player, double d, double d2, double d3, float f, float f2) {
        this.Player = player;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.pitch = f2;
        this.yaw = f;
    }

    public Player getPlayer() {
        return this.Player;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
