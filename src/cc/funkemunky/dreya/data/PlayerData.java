package cc.funkemunky.dreya.data;

import org.bukkit.entity.Player;

public class PlayerData {

    /** Data fields **/
    private Player player;
    private boolean alerts = false;
    private double fallDistance = 0D;
    private int aboveBlockTicks = 0;
    private int waterTicks = 0;

    /** Violation fields **/
    private int criticalsVerbose = 0;


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
}
