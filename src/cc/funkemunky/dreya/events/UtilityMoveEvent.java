package cc.funkemunky.dreya.events;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.BlockUtils;
import cc.funkemunky.dreya.util.MathUtils;
import cc.funkemunky.dreya.util.PlayerUtils;
import cc.funkemunky.dreya.util.TimerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class UtilityMoveEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(player);

        if (data.isNearIce()) {
            if (TimerUtils.elapsed(data.getIsNearIceTicks(),500L)) {
                if (!BlockUtils.isNearIce(player)) {
                    data.setNearIce(false);
                } else {
                    data.setIsNearIceTicks(TimerUtils.nowlong());
                }
            }
        }

        if (BlockUtils.isNearIce(player) && !data.isNearIce()) {
            data.setNearIce(true);
            data.setIsNearIceTicks(TimerUtils.nowlong());
        } else if (BlockUtils.isNearIce(player)) {
            data.setIsNearIceTicks(TimerUtils.nowlong());
        }

        double distance = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());

        boolean onGround = PlayerUtils.isOnGround(player);
        if(!onGround
                && e.getFrom().getY() > e.getTo().getY()) {
            data.setFallDistance(data.getFallDistance() + distance);
        } else {
            data.setFallDistance(0);
        }

        if(onGround) {
            data.setGroundTicks(data.getGroundTicks() + 1);
            data.setAirTicks(0);
        } else {
            data.setAirTicks(data.getAirTicks() + 1);
            data.setGroundTicks(0);
        }

        if(PlayerUtils.isOnGround(player.getLocation().add(0, 2, 0))) {
            data.setAboveBlockTicks(data.getAboveBlockTicks() + 1);
        } else if(data.getAboveBlockTicks() > 0) {
            data.setAboveBlockTicks(data.getAboveBlockTicks() - 1);
        }

        if(PlayerUtils.isInWater(player.getLocation())
                || PlayerUtils.isInWater(player.getLocation().add(0, 1, 0))) {
            data.setWaterTicks(data.getWaterTicks() + 1);
        } else if(data.getWaterTicks() > 0) {
            data.setWaterTicks(data.getWaterTicks() - 1);
        }
    }
}
