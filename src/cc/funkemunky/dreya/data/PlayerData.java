package cc.funkemunky.dreya.data;

import org.bukkit.entity.Player;

public class PlayerData {

    /** Data fields **/
    private Player player;
    private boolean alerts = false;
    private double fallDistance = 0D;
    private int aboveBlockTicks = 0;
    private int waterTicks = 0;
<<<<<<< HEAD
    private long LastBlockPlacedTicks = 0;
    private boolean LastBlockPlaced_GroundSpoof = false;
=======
    private int airTicks = 0;
    private int groundTicks = 0;

>>>>>>> 597246ca2a81eb1976b1a506b1cb7d266a71f2f5
    /** Violation fields **/
    private int criticalsVerbose = 0;
    private int flyHoverVerbose = 0;


    public PlayerData(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAlerts() {
        return alerts;
    }

    public void setAlerts(boolean alerts) {
        this.alerts = alerts;
    }

    public double getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(double fallDistance) {
        this.fallDistance = fallDistance;
    }

    public int getAboveBlockTicks() {
        return aboveBlockTicks;
    }

    public void setAboveBlockTicks(int aboveBlockTicks) {
        this.aboveBlockTicks = aboveBlockTicks;
    }

    public int getWaterTicks() {
        return waterTicks;
    }

    public void setWaterTicks(int waterTicks) {
        this.waterTicks = waterTicks;
    }

    public int getCriticalsVerbose() {
        return criticalsVerbose;
    }

    public void setCriticalsVerbose(int criticalsVerbose) {
        this.criticalsVerbose = criticalsVerbose;
    }

<<<<<<< HEAD
    public long getLastBlockPlacedTicks() {
        return LastBlockPlacedTicks;
    }

    public void setLastBlockPlacedTicks(long lastBlockPlacedTicks) {
        LastBlockPlacedTicks = lastBlockPlacedTicks;
    }

    public boolean isLastBlockPlaced_GroundSpoof() {
        return LastBlockPlaced_GroundSpoof;
    }

    public void setLastBlockPlaced_GroundSpoof(boolean lastBlockPlaced_GroundSpoof) {
        LastBlockPlaced_GroundSpoof = lastBlockPlaced_GroundSpoof;
=======
    public int getAirTicks() {
        return airTicks;
    }

    public void setAirTicks(int airTicks) {
        this.airTicks = airTicks;
    }

    public int getGroundTicks() {
        return groundTicks;
    }

    public void setGroundTicks(int groundTicks) {
        this.groundTicks = groundTicks;
    }

    public int getFlyHoverVerbose() {
        return flyHoverVerbose;
    }

    public void setFlyHoverVerbose(int flyHoverVerbose) {
        this.flyHoverVerbose = flyHoverVerbose;
>>>>>>> 597246ca2a81eb1976b1a506b1cb7d266a71f2f5
    }
}
