package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.util.LineOfSight_Utils.BlockPathFinder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.player
 */
public class Line_Of_Sight_Check extends Check {
      public Line_Of_Sight_Check() {
        super("Line Of Sight", CheckType.WORLD, true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
     Player p = e.getPlayer();
          if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              flag(p,"Broke a block without a line of sight too it.");
              e.setCancelled(true);
          }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
          Player p = e.getPlayer();
            if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              flag(p,"Placed a block without a line of sight too it.");
              e.setCancelled(true);
          }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            if ((e.getClickedBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
                    && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getClickedBlock().getLocation()).contains(e.getClickedBlock()) && !e.isCancelled()) {
                flag(p, "Interacted without a line of sight too it.");
                e.setCancelled(true);
            }
        }
    }
}
