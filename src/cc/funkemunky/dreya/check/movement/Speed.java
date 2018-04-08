package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.movement
 */
public class Speed extends Check {
    public Speed() {
        super("Speed", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        Location to = e.getTo();
        Location from = e.getFrom();
        if (data != null) {
            //Type A
            double OXZ = MathUtils.offset(getHorizontalVector(e.getFrom().toVector()), getHorizontalVector(e.getTo().toVector()));
            double LXZ;
            if (p.getVehicle() == null) {
                LXZ = 0.42D;
            } else {
                LXZ = 2D;
            }
            if (data.isSpeed_TicksSet()) {
                if (TimerUtils.elapsed(data.getSpeed_Ticks(),500L)) {
                    data.setSpeed_TicksSet(false);
                    data.setSpeedVerbose(0);
                }
            }
            for (PotionEffect effect : p.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SPEED)) {
                    LXZ += 0.3D;
                }
            }
            if (BlockUtils.isIce(p.getLocation().add(0,1.50,0).getBlock())) {
                LXZ += 0.5D;
            } else if (BlockUtils.isNearIce(p)) {
                LXZ += 0.5D;
            } else if (data.isNearIce()) {
                LXZ += 0.5D;
            } else if (BlockUtils.isNearStiar(p)) {
                LXZ += 0.5D;
            }
           if (OXZ > LXZ && !VelocityUtils.didTakeVelocity(p) && !NEW_Velocity_Utils.didTakeVel(p)) {
                if (!data.isSpeed_TicksSet()) {
                    data.setSpeed_TicksSet(true);
                    data.setSpeed_Ticks(TimerUtils.nowlong());
                }
                data.setSpeedVerbose(data.getSpeedVerbose()+1);
           } else if (data.getSpeedVerbose() >= 6 && !VelocityUtils.didTakeVelocity(p) && !NEW_Velocity_Utils.didTakeVel(p)) {
               flag(p,"Type: A - Player Moved Too Fast.");
               setBack(p);
           }
        }

        //Type B
        double YSpeed = MathUtils.offset(getVerticalVector(e.getFrom().toVector()),
                getVerticalVector(e.getTo().toVector()));
        if (((YSpeed == 0.25D || (YSpeed >= 0.58D && YSpeed < 0.581D))
                || (YSpeed > 0.2457D && YSpeed < 0.24582D) || (YSpeed > 0.329 && YSpeed < 0.33) || YSpeed == 0.4200000000000017)
                && !p.getLocation().clone().subtract(0.0D, 0.1, 0.0D).getBlock().getType().equals(Material.SNOW)) {
            flag(p,"Type: B - Player Moved Too Fast.");
            setBack(p);

        }
            //Type C
            Location l = p.getLocation();
            int x = l.getBlockX();
            int y = l.getBlockY();
            int z = l.getBlockZ();
            Location blockLoc = new Location(p.getWorld(), x, y - 1, z);
            Location loc = new Location(p.getWorld(), x, y, z);
            Location loc2 = new Location(p.getWorld(), x, y + 1, z);
            Location above = new Location(p.getWorld(), x, y + 2, z);
            Location above3 = new Location(p.getWorld(), x - 1, y + 2, z - 1);
            double MaxAirSpeed = 0.4;
            double maxSpeed = 0.42;
            double MaxSpeedNEW = 0.75;
            if (data.isNearIce()) {
                MaxSpeedNEW = 1.0;
            }
            double Max = 0.28;
            double speed = MathUtils.offset(getHorizontalVector(to.toVector()), getHorizontalVector(from.toVector()));
            if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                int level = getPotionEffectLevel(p, PotionEffectType.SPEED);
                if (level > 0) {
                    maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                    MaxAirSpeed = (MaxAirSpeed * (((level * 20) * 0.011) + 1));
                    maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
                    MaxSpeedNEW = (MaxSpeedNEW * (((level * 20) * 0.011) + 1));
                }
            }
            MaxAirSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
            maxSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
            //1
            if (!PlayerUtils.isOnGround3(p) && speed >= MaxAirSpeed && !data.isNearIce()
                    && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
                    && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
                    && blockLoc.getBlock().getType() != Material.AIR && !NEW_Velocity_Utils.didTakeVel(p) && !BlockUtils.isNearStiar(p)) {
                if (!NEW_Velocity_Utils.didTakeVel(p)) {
                    if (data.getSpeed2Verbose() >= 3) {
                        flag(p, "Type: C [1] - Player Moved Too Fast.");
                        setBack(p);
                    } else {
                        data.setSpeed2Verbose(data.getSpeed2Verbose()+1);
                    }
                } else {
                    data.setSpeed2Verbose(0);
                }
            }

            //2
            double onGroundDiff = (to.getY() - from.getY());
            if (speed > Max && !isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4  && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR) {
                flag(p,"Type: C [2] - Player Moved Too Fast.");
                setBack(p);
            }

            //3
            if (speed > Max && !isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR) {
                flag(p,"Type: C [3] - Player Moved Too Fast.");
                setBack(p);
            }
    }
    public boolean isAir(final Player player) {
        final Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        return b.getType().equals(Material.AIR)
                && b.getRelative(BlockFace.WEST).getType().equals(Material.AIR)
                && b.getRelative(BlockFace.NORTH).getType().equals(Material.AIR)
                && b.getRelative(BlockFace.EAST).getType().equals(Material.AIR)
                && b.getRelative(BlockFace.SOUTH).getType().equals(Material.AIR);
    }
    private int getPotionEffectLevel(Player p, PotionEffectType pet) {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (pe.getType().getName().equals(pet.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }
    private Vector getHorizontalVector(final Vector vector) {
        vector.setY(0);
        return vector;
    }
    private Vector getVerticalVector(final Vector v) {
        v.setX(0);
        v.setZ(0);
        return v;
    }
    private void setBack(Player p) {
        SetBackSystem.setBack(p);
    }
}
