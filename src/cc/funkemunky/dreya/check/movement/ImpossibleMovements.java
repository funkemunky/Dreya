package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.DebugUtils;
import cc.funkemunky.dreya.util.SetBackSystem;
import cc.funkemunky.dreya.util.TimerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
        Location from  =e.getFrom();
        Location to = e.getTo();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
            //Anti Cactus
            if (p.getLocation().add(0,-0.30,0).getBlock().getType() == Material.CACTUS && p.getLocation().getBlock().getType() == Material.AIR) {
                if (data.getAntiCactus_VL() >= 3) {
                    flag(p,"Anti Cactus");
                } else {
                    data.setAntiCactus_VL(data.getAntiCactus_VL()+1);
                }
            } else {
                data.setAntiCactus_VL(0);
            }

            //Web Float
            if (!data.isWebFloatMS_Set() && p.getLocation().add(0,-0.50,0).getBlock().getType() == Material.WEB) {
                data.setWebFloatMS_Set(true);
             data.setWebFloatMS(TimerUtils.nowlong());
            } else if (data.isWebFloatMS_Set()) {
                if (e.getTo().getY() == e.getFrom().getY()) {
                    double x = Math.floor(from.getX());
                    double z = Math.floor(from.getZ());
                    if(Math.floor(to.getX())!=x||Math.floor(to.getZ())!=z) {
                        if (data.getWebFloat_BlockCount() > 0) {
                            if (p.getLocation().add(0,-0.50,0).getBlock().getType() != Material.WEB) {
                                data.setWebFloatMS_Set(false);
                                data.setWebFloat_BlockCount(0);
                            }
                            flag(p,"Web Float");
                            SetBackSystem.setBack(p);
                        } else {
                            data.setWebFloat_BlockCount(data.getWebFloat_BlockCount()+1);
                        }
                    }
                } else {
                    data.setWebFloatMS_Set(false);
                    data.setWebFloat_BlockCount(0);
                }
            }
        }
    }
}
