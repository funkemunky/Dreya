package cc.funkemunky.dreya.check.movement;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

        if ((to.getX() == from.getX()
                && to.getY() == from.getY()
                && to.getZ() == from.getZ())
                || p.getAllowFlight()) {
            return;
        }

        if (data != null) {

            if (data.isSpeed_PistonExpand_Set()) {
                if (TimerUtils.elapsed(data.getSpeed_PistonExpand_MS(), 9900L)) {
                    data.setSpeed_PistonExpand_Set(false);
                }
            }
            //Type A
            double speed = MathUtils.getHorizontalDistance(from, to);
           if(MathUtils.elapsed(data.getLastVelMS(), 3000)) {
               int verbose = data.getSpeedAVerbose();
               double speedEffect = PlayerUtils.getPotionEffectLevel(p, PotionEffectType.SPEED);
               double speedAThreshold = (data.getAirTicks() > 0 ? data.getAirTicks() >= 6
                       ? data.getAirTicks() == 13 ? 0.466 : 0.35 : (0.345 * Math.pow(0.986938064, data.getAirTicks()))
                       : data.getGroundTicks() > 5 ? 0.362 : data.getGroundTicks() == 3 ? 0.62 : 0.4)
                       + (data.getAirTicks() > 0 ? (-0.001 * data.getAirTicks() + 0.014) : (0.018 - (data.getGroundTicks() >= 6 ? 0 : data.getGroundTicks() * 0.001)) * speedEffect);

               speedAThreshold = data.getAboveBlockTicks() > 0 ? speedAThreshold + 0.25 : speedAThreshold;
               speedAThreshold = data.getIceTicks() > 0 ? speedAThreshold + 0.14 : speedAThreshold;
               speedAThreshold = data.getSlimeTicks() > 0 ? speedAThreshold + 0.1 : speedAThreshold;
               speedAThreshold = data.getIceTicks() > 0 && data.getAboveBlockTicks() > 0 ? speedAThreshold + 0.24 : speedAThreshold;

               if(PlayerUtils.isOnStair(p.getLocation())
                       || PlayerUtils.isOnSlab(p.getLocation())) {
                   speedAThreshold+= 0.12;
               }


               if (speed > speedAThreshold) {
                   verbose += 8;
                  // Bukkit.broadcastMessage("Verbose: " + verbose + ", " + speed + ", " + speedAThreshold + ", " + data.getGroundTicks() + ", " + data.getAirTicks() + ", " + data.getAboveBlockTicks() + ", " + data.getIceTicks());
               } else {
                   verbose = verbose > 0 ? verbose - 1 : 0;
               }

               if (verbose > 38) {
                   flag(p, "Type: A");
                   SetBackSystem.setBack(p);
                   verbose = 0;
               }

               data.setSpeedAVerbose(verbose);
           } else {
               data.setSpeedAVerbose(0);
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
            if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                int level = PlayerUtils.getPotionEffectLevel(p, PotionEffectType.SPEED);
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
            if (!PlayerUtils.isOnGround4(p) && speed >= MaxAirSpeed && !data.isNearIce()
                    && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
                    && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
                    && blockLoc.getBlock().getType() != Material.AIR && !NEW_Velocity_Utils.didTakeVel(p) && !BlockUtils.isNearStiar(p)) {
                if (!NEW_Velocity_Utils.didTakeVel(p) && PlayerUtils.getDistanceToGround(p) > 4 == false) {
                    if (data.getSpeed2Verbose() >= 8 || p.getNoDamageTicks() == 0 == false && !VelocityUtils.didTakeVelocity(p) && !NEW_Velocity_Utils.didTakeVel(p)
                            && p.getLocation().add(0, 1.94, 0).getBlock().getType() != Material.AIR) {
                        flag(p, "Type: C [1] - Player Moved Too Fast.");
                        SetBackSystem.setBack(p);
                    } else {
                        data.setSpeed2Verbose(data.getSpeed2Verbose() + 1);
                    }
                } else {
                    data.setSpeed2Verbose(0);
                }
            }

            //2
            double onGroundDiff = (to.getY() - from.getY());
            if (speed > Max && !PlayerUtils.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR && data.getAboveBlockTicks() != 0) {
                flag(p, "Type: C [2] - Player Moved Too Fast.");
                SetBackSystem.setBack(p);
            }

            //3
            if (speed > Max && !PlayerUtils.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                    && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                    && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                    && above3.getBlock().getType() == Material.AIR && !NEW_Velocity_Utils.didTakeVel(p) && !VelocityUtils.didTakeVelocity(p) && !PlayerUtils.hasPistonNear(p) &&
                    p.getLocation().getBlock().getType() != Material.PISTON_MOVING_PIECE && p.getLocation().getBlock().getType() != Material.PISTON_BASE
                    && p.getLocation().getBlock().getType() != Material.PISTON_STICKY_BASE && !BlockUtils.isNearPistion(p) && !data.isSpeed_PistonExpand_Set()) {
                if (!data.isSpeed_PistonExpand_Set()) {
                    if (data.getSpeed_C_3_Verbose() > 1) {
                        flag(p, "Type: C [3] - Player Moved Too Fast.");
                        SetBackSystem.setBack(p);
                    } else {
                        data.setSpeed_C_3_Verbose(data.getSpeed_C_3_Verbose() + 1);
                    }
                    //Type A

                }
                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                    int level = PlayerUtils.getPotionEffectLevel(p, PotionEffectType.SPEED);
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
                if (!PlayerUtils.isOnGround4(p) && speed >= MaxAirSpeed && !data.isNearIce()
                        && blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
                        && !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                        && above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
                        && blockLoc.getBlock().getType() != Material.AIR && !NEW_Velocity_Utils.didTakeVel(p) && !BlockUtils.isNearStiar(p)) {
                    if (!NEW_Velocity_Utils.didTakeVel(p) && PlayerUtils.getDistanceToGround(p) > 4 == false) {
                        if (data.getSpeed2Verbose() >= 8 || p.getNoDamageTicks() == 0 == false && !VelocityUtils.didTakeVelocity(p) && !NEW_Velocity_Utils.didTakeVel(p)
                                && p.getLocation().add(0, 1.94, 0).getBlock().getType() != Material.AIR) {
                            flag(p, "Type: C [1] - Player Moved Too Fast.");
                            SetBackSystem.setBack(p);
                        } else {
                            data.setSpeed2Verbose(data.getSpeed2Verbose() + 1);
                        }
                    } else {
                        data.setSpeed2Verbose(0);
                    }
                    //Type D
                    boolean speedPot = false;
                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        if (effect.getType().equals(PotionEffectType.SPEED)) {
                            speedPot = true;
                        }
                    }
                    if (speed > 0.29 && PlayerUtils.isOnGround(p) && !data.isNearIce() && !BlockUtils.isNearStiar(p) && !NEW_Velocity_Utils.didTakeVel(p) && !speedPot) {
                        if (data.getSpeed_OnGround_Verbose() >= 5) {
                        }

                        //2
                        if (speed > Max && !PlayerUtils.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                                && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                                && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                                && above3.getBlock().getType() == Material.AIR && data.getIceTicks() == 0 && !PlayerUtils.hasIceNear(p)) {
                            flag(p, "Type: C [2] - Player Moved Too Fast.");
                            SetBackSystem.setBack(p);
                        }

                        //3
                        if (speed > Max && !PlayerUtils.isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4 && blockLoc.getBlock().getType() != Material.ICE
                                && e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
                                && loc2.getBlock().getType() != Material.TRAP_DOOR && above.getBlock().getType() == Material.AIR
                                && above3.getBlock().getType() == Material.AIR && !NEW_Velocity_Utils.didTakeVel(p) && !VelocityUtils.didTakeVelocity(p) && !PlayerUtils.hasPistonNear(p) &&
                                p.getLocation().getBlock().getType() != Material.PISTON_MOVING_PIECE && p.getLocation().getBlock().getType() != Material.PISTON_BASE
                                && p.getLocation().getBlock().getType() != Material.PISTON_STICKY_BASE && !BlockUtils.isNearPistion(p)) {
                            //   flag(p,"Type: C [3] - Player Moved Too Fast.");
                            //     setBack(p);
                            //       SetBackSystem.setBack(p);
                        }
                        //4


                    }
                }
            }
        }
    }
}