package cc.funkemunky.dreya.events;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.MathUtils;
import cc.funkemunky.dreya.util.PlayerUtils;
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

        double distance = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());

        if(!PlayerUtils.isOnGround(player)
                && e.getFrom().getY() > e.getTo().getY()) {
            data.setFallDistance(data.getFallDistance() + distance);
        } else {
            data.setFallDistance(0);
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
