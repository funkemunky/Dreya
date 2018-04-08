package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.PlayerUtils;
import cc.funkemunky.dreya.util.TimerUtils;
import cc.funkemunky.dreya.util.VelocityUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.player
 */
public class GroundSpoofCheck extends Check {
     public GroundSpoofCheck() {
        super("Ground Spoof", CheckType.MISC, true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
         Player p = e.getPlayer();
          PlayerData data = Dreya.getInstance().getDataManager().getData(p);
         if (data != null) {
            if (e.getTo().getY() > e.getFrom().getY()) {
                return;
            }
             if (data.isLastBlockPlaced_GroundSpoof()) {
                 if (TimerUtils.elapsed(data.getLastBlockPlacedTicks(),500L)) {
                     data.setLastBlockPlaced_GroundSpoof(false);
                 }
                 return;
             }
             Location to = e.getTo();
             Location from = e.getFrom();
             double diff = to.toVector().distance(from.toVector());
             int dist = PlayerUtils.getDistanceToGround(p);
             if (p.getLocation().add(0,-1.50,0).getBlock().getType() != Material.AIR) {
                 data.setGroundSpoofVL(0);
                 return;
             }
             if (e.getTo().getY() > e.getFrom().getY() || PlayerUtils.isOnGround3(p) || VelocityUtils.didTakeVelocity(p)) {
                 data.setGroundSpoofVL(0);
                 return;
             }
             if (p.isOnGround() && diff > 0.0 && !PlayerUtils.isOnGround(p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
                 if (data.getGroundSpoofVL() >= 4) {
                     flag(p, "Spoofed On-Ground Packet.");
                 } else {
                     data.setGroundSpoofVL(data.getGroundSpoofVL()+1);
                 }
             }
         }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
         Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
         if (data != null) {
             if (!data.isLastBlockPlaced_GroundSpoof()) {
                 data.setLastBlockPlaced_GroundSpoof(true);
                 data.setLastBlockPlacedTicks(TimerUtils.nowlong());
             }
         }
    }
}
