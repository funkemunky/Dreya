package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.movement
 */
public class Fly extends Check {
    public Fly() {
        super("Flight", CheckType.MOVEMENT, true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE) || p.getAllowFlight() || e.getPlayer().getVehicle() != null || p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
            return;
        }
       Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
        double Distance = vec.distance(new Vector(from.getX(),from.getY(),from.getZ()));
        if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
            if (Distance > 0.50 && !PlayerUtils.isOnGround(p)) {
                flag(p,"Type: A");
            }
        }
    }
}
