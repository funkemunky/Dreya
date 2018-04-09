package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-09 Package cc.funkemunky.dreya.check.movement
 */
public class Jesus extends Check {
    public Jesus() {
        super("Jesus", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (p.getLocation().add(0,-0.3,0).getBlock().isLiquid()) {
                boolean onWater = isHoveringOverWater(p.getLocation());
                Location to = e.getTo();
                Location from = e.getFrom();
                double motion = (to.getY() - from.getY());
                boolean goingUp;
                if (e.getTo().getY() > e.getTo().getY()) {
                    goingUp = true;
                } else {
                    goingUp = false;
                }
                if (onWater) {
                    if (motion > 0.10000 && !goingUp) {
                        p.sendMessage("1");
                    }
                }
            }
        }
    }
    public static boolean isOnWater(Location player, int blocks)
    {
        for (int i = player.getBlockY(); i > player.getBlockY() - blocks; i--)
        {
            Block newloc = new Location(player.getWorld(), player.getBlockX(), i, player.getBlockZ()).getBlock();
            if (newloc.getType() != Material.AIR) {
                return newloc.isLiquid();
            }
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player)
    {
        return isOnWater(player, 25);
    }
}
