package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Example extends Check {

    public Example() {
        super("Example", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        flag(e.getPlayer(), "Test data");
    }

}
