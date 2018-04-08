package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.movement
 */
public class Gravity extends Check {
    public Gravity() {
        super("Gravity", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            double diff = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());
            double LastY = data.getLastY_Gravity();
            double MaxG = 5;
            if (e.getTo().getY() < e.getFrom().getY()) {
                return;
            }
            if (BlockUtils.isHalfBlock(p.getLocation().add(0, -1.50, 0).getBlock()) || BlockUtils.isNearHalfBlock(p) || BlockUtils.isStair(p.getLocation().add(0,1.50,0).getBlock())
                    || BlockUtils.isStair(p.getLocation().add(0,1,0).getBlock())
                    || BlockUtils.isStair(p.getLocation().add(0,2,0).getBlock()) || BlockUtils.isNearStiar(p) || NEW_Velocity_Utils.didTakeVel(p)) {
                data.setGravity_VL(0);
                return;
            }
            if (!PlayerUtils.onGround2(p) || !PlayerUtils.isOnGround3(p) || !PlayerUtils.isOnGround(p) || p.getLocation().getBlock().getType() != Material.CHEST ||
                    p.getLocation().getBlock().getType() != Material.TRAPPED_CHEST || p.getLocation().getBlock().getType() != Material.ENDER_CHEST || p.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                if (!PlayerUtils.onGround2(p) || !PlayerUtils.isOnGround3(p) || !PlayerUtils.isOnGround(p)) {
                    if ((((ServerUtils.isBukkitVerison("1_7") || ServerUtils.isBukkitVerison("1_8")) && Math.abs(p.getVelocity().getY() - LastY) > 0.000001)
                            || (!ServerUtils.isBukkitVerison("1_7") && !ServerUtils.isBukkitVerison("1_8") && Math.abs(p.getVelocity().getY() - diff) > 0.000001))
                            && !PlayerUtils.onGround2(p)
                            && e.getFrom().getY() < e.getTo().getY()
                            && (p.getVelocity().getY() >= 0 || p.getVelocity().getY() < (-0.0784 * 5)) && !VelocityUtils.didTakeVelocity(p) && p.getNoDamageTicks() == 0.0) {
                        if (data.getGravity_VL() >= MaxG) {
                            flag(p, "Player's motion was changed to an unexpected value.");
                            SetBackSystem.setBack(p);
                        } else {
                            data.setGravity_VL(data.getGravity_VL() + 1);
                        }
                    }
                    data.setLastY_Gravity(diff);
                }
            } else {
                data.setGravity_VL(0);
            }
        }
    }
}
