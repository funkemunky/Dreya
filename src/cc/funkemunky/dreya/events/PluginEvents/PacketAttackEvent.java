package cc.funkemunky.dreya.events.PluginEvents;

import cc.funkemunky.dreya.PacketCore.PacketTypes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketAttackEvent extends Event {
    private Player player;
    private Entity entity;
    private PacketTypes type;
    private static final HandlerList handlers = new HandlerList();

    public PacketAttackEvent(Player player, Entity entity, PacketTypes type) {
        this.player = player;
        this.entity = entity;
        this.type = type;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Entity getEntity() {
        return entity;
    }

    public PacketTypes getType() {
        return type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
