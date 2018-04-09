package cc.funkemunky.dreya.util;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.util
 */
public class VelocityUtils implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (data.isDidTakeVelocity()) {
                if (TimerUtils.elapsed(data.getLastVelMS(),2000L)) {
                    data.setDidTakeVelocity(false);
                }
            }
        }
    }
    @EventHandler
    public void onVelEvent(PlayerVelocityEvent e) {
        Player p = e.getPlayer();
         PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (!data.isDidTakeVelocity() && p.getNoDamageTicks() > 0) {
                data.setDidTakeVelocity(true);
                data.setLastVelMS(TimerUtils.nowlong());
            }
        }
    }
    public static boolean didTakeVelocity(Player p) {
        boolean out = false;
     PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null && data.isDidTakeVelocity()) {
            out = true;
        }
        return out;
    }
}
