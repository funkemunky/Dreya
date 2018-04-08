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
public class NEW_Velocity_Utils implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (data.isLastVelUpdateBoolean()) {

                if (TimerUtils.elapsed(data.getLastVelUpdate(),ConfigValues.VelTimeReset_1_FORCE_RESET)) {
                    //FORCE Reset
                    data.setLastVelUpdateBoolean(false);
                }
                if (TimerUtils.elapsed(data.getLastVelUpdate(),ConfigValues.VelTimeReset_1)) {
                    if (!p.isOnGround()) {
                        data.setLastVelUpdate(TimerUtils.nowlong());
                    } else {
                        data.setLastVelUpdateBoolean(false);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onVelChange(PlayerVelocityEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (!data.isLastVelUpdateBoolean()) {
                data.setLastVelUpdateBoolean(true);
                data.setLastVelUpdate(TimerUtils.nowlong());
            }
        }
    }
    public static boolean didTakeVel(Player p) {
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            return data.isLastVelUpdateBoolean();
        } else {
            return false;
        }
    }
}
