package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.MathUtils;
import cc.funkemunky.dreya.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.movement
 */
public class Fly extends Check {
    public Fly() {
        super("Flight", CheckType.MOVEMENT, true);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)
                || p.getAllowFlight()
                || e.getPlayer().getVehicle() != null
                || p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
            return;
        }

        PlayerData data = Dreya.getInstance().getDataManager().getData(p);

        if(data == null) {
            return;
        }

        //Ascension Check
        Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
        double Distance = vec.distance(new Vector(from.getX(),from.getY(),from.getZ()));
        if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
            if (Distance > 0.50 && !PlayerUtils.isOnGround(p)) {
                flag(p,"Type: A");
            }
        }

        //Hover check
        if(!PlayerUtils.isOnGround(p)) {
            double distanceToGround = getDistanceToGround(p);
            double yDiff = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());
            int verbose = data.getFlyHoverVerbose();

            if(distanceToGround > 2) {
                verbose = yDiff == 0 ? verbose + 6 : yDiff < 0.06 ? verbose + 4 : 0;
            } else if(data.getAirTicks() > 7
                    && yDiff == 0) {
                verbose+= 2;
            } else {
                verbose = 0;
            }

            if(verbose > 17) {
                flag(p, "Type: B");
                verbose = 0;
            }
            data.setFlyHoverVerbose(verbose);
        }
    }

    private int getDistanceToGround(Player p){
        Location loc = p.getLocation().clone();
        double y = loc.getBlockY();
        int distance = 0;
        for (double i = y; i >= 0; i--){
            loc.setY(i);
            if(loc.getBlock().getType().isSolid() || loc.getBlock().isLiquid())break;
            distance++;
        }
        return distance;
    }
}
