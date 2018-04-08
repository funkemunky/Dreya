package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.player
 */
public class ImpossiblePitch extends Check {
      public ImpossiblePitch() {
        super("Impossible Pitch", CheckType.MOVEMENT, true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        double Pitch = e.getPlayer().getLocation().getPitch();
        if (Pitch > 90 || Pitch < -90) {
            flag(e.getPlayer(),"Players head went back too far. P:["+Pitch+"]");
        }
    }
}
