package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Test extends Check {

    public Test() {
        super("Test", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(e.getPlayer().getName().equalsIgnoreCase("funkemunky")) e.getPlayer().sendMessage("works");
    }
}
