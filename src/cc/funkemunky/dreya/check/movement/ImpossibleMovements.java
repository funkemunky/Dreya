package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.movement
 */
public class ImpossibleMovements extends Check {
    public ImpossibleMovements() {
        super("ImpossibleMovements", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (p.getLocation().add(0,-1.30,0).getBlock().getType() == Material.CACTUS && p.getLocation().getBlock().getType() == Material.AIR) {
                if (data.getAntiCactus_VL() >= 3) {
                    flag(p,"Anti Cactus");
                } else {
                    data.setAntiCactus_VL(data.getAntiCactus_VL()+1);
                }
            } else {
                data.setAntiCactus_VL(0);
            }
        }
    }
}
