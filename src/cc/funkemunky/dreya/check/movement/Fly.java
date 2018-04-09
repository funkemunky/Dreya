package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.*;
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
                || p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE
                || PlayerUtils.isOnClimbable(p, 0)
                || PlayerUtils.isOnClimbable(p, 1) || VelocityUtils.didTakeVelocity(p)) {
            return;
        }

        PlayerData data = Dreya.getInstance().getDataManager().getData(p);

        if (data == null) {
            return;
        }
        //Ascension Check
        if (!NEW_Velocity_Utils.didTakeVel(p) && !PlayerUtils.wasOnSlime(p)) {
            Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
            double Distance = vec.distance(new Vector(from.getX(), from.getY(), from.getZ()));
            if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
                if (Distance > 0.50 && !PlayerUtils.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ() && !VelocityUtils.didTakeVelocity(p)) {
                    flag(p, "Type: A [1]");
                    setBackPlayer(p);
                } else if (Distance > 0.90 && !PlayerUtils.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                    flag(p, "Type: A [2]");
                    setBackPlayer(p);
                } else if (Distance > 1.0 && !PlayerUtils.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                    flag(p, "Type: A [3]");
                    setBackPlayer(p);
                } else if (Distance > 3.24 && !PlayerUtils.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
                    flag(p, "Type: A [4]");
                    setBackPlayer(p);
                }
            }
        }
        if (!NEW_Velocity_Utils.didTakeVel(p) && !PlayerUtils.wasOnSlime(p)) {
            if (e.getTo().getY() > e.getFrom().getY() && data.getAirTicks() > 2 && !VelocityUtils.didTakeVelocity(p)) {
                if (!PlayerUtils.isOnGround3(p) && !PlayerUtils.onGround2(p) && !PlayerUtils.isOnGround(p)) {
                    if (PlayerUtils.getDistanceToGround(p) > 2) {
                        if (data.getGoingUp_Blocks() >= 3 && data.getAirTicks() >= 10) {
                            flag(p, "Type: A [5]");
                            setBackPlayer(p);
                        } else {
                            data.setGoingUp_Blocks(data.getGoingUp_Blocks() + 1);
                        }
                    } else {
                        data.setGoingUp_Blocks(0);
                    }
                } else {
                    data.setGoingUp_Blocks(0);
                }
            } else if (e.getTo().getY() < e.getFrom().getY()) {
                data.setGoingUp_Blocks(0);
            } else {
                data.setGoingUp_Blocks(0);
            }
        } else {
            data.setGoingUp_Blocks(0);
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
                setBackPlayer(p);
                verbose = 0;
            }
            data.setFlyHoverVerbose(verbose);
        }

        //Velocity Diff check
        double diffY = Math.abs(from.getY() - to.getY());
        double lastDiffY = data.getLastVelocityFlyY();
        int verboseC = data.getFlyVelocityVerbose();

        double finalDifference = Math.abs(diffY - lastDiffY);

        //Bukkit.broadcastMessage(Math.abs(diffY - lastDiffY) + ", " + PlayerUtils.isOnGround(p));

        if(finalDifference < 0.08
                && e.getFrom().getY() < e.getTo().getY()
                && !PlayerUtils.isOnGround(p) && !p.getLocation().getBlock().isLiquid() && !BlockUtils.isNearLiquid(p)) {
            if(++verboseC > 2) {
                flag(p, "Type: C");
                SetBackSystem.setBack(p);
                verboseC = 0;
            }
        } else {
            verboseC = verboseC > 0 ? verboseC - 1 : 0;
        }
        data.setLastVelocityFlyY(diffY);
        data.setFlyVelocityVerbose(verboseC);
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
    private static void setBackPlayer(Player p) {
        SetBackSystem.setBack(p);
    }
}