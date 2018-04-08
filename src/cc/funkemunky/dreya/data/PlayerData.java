package cc.funkemunky.dreya.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerData {

    /** Data fields **/
    private Player player;
    private boolean alerts = false;
    private double fallDistance = 0D;
    private int aboveBlockTicks = 0;
    private int waterTicks = 0;
    private long LastBlockPlacedTicks = 0;
    private boolean LastBlockPlaced_GroundSpoof = false;
    private int airTicks = 0;
    private int groundTicks = 0;
    private int GroundSpoofVL;
    private boolean ShouldSetBack = false;
    private int setBackTicks = 0;
    private long LastVelMS = 0;
    private boolean DidTakeVelocity = false;
    private long lastDelayedPacket;
    private long lastReceivedKeepAlive;
    private int lastReceivedKeepAliveID;
    private long lastSentKeepAlive;
    private long lastPlayerPacket;
    private long LastTypeBPingSpoof;
    private int PingSpoof_VL2 = 0;
    private long LastTypeBPingSpoof2;
    private boolean DidSetPingspoof2 = false;
    private int LastPingBeforKick_Pingspoof = 0;
    private boolean didGetKicked_Pingspoof = false;
    private int SlowPingSpoof_Count = 0;
    private Location setbackLocation;
    private long GoingUp_MS;
    private double GoingUp_Blocks;
    private double LastY_Gravity;
    private int Gravity_VL;
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
    }
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
    }

    public int getGroundSpoofVL() {
        return GroundSpoofVL;
    }

    public void setGroundSpoofVL(int groundSpoofVL) {
        GroundSpoofVL = groundSpoofVL;
    }

    public boolean isShouldSetBack() {
        return ShouldSetBack;
    }

    public void setShouldSetBack(boolean shouldSetBack) {
        ShouldSetBack = shouldSetBack;
    }

    public int getSetBackTicks() {
        return setBackTicks;
    }

    public void setSetBackTicks(int setBackTicks) {
        this.setBackTicks = setBackTicks;
    }

    public long getLastVelMS() {
        return LastVelMS;
    }

    public void setLastVelMS(long lastVelMS) {
        LastVelMS = lastVelMS;
    }

    public boolean isDidTakeVelocity() {
        return DidTakeVelocity;
    }

    public void setDidTakeVelocity(boolean didTakeVelocity) {
        DidTakeVelocity = didTakeVelocity;
    }

    public long getLastDelayedPacket() {
        return this.lastDelayedPacket;
    }

    public long getLastDelayedPacketDiff() {
        return System.currentTimeMillis() - this.getLastDelayedPacket();
    }

    public void setLastDelayedPacket(long l) {
        this.lastDelayedPacket = l;
    }

    public long getLastReceivedKeepAlive() {
        return this.lastReceivedKeepAlive;
    }

    public void setLastReceivedKeepAliveID(int n) {
        this.lastReceivedKeepAliveID = n;
    }


    public long getLastSentKeepAlive() {
        return this.lastSentKeepAlive;
    }

    public long getLastPlayerPacketDiff() {
        return System.currentTimeMillis() - this.getLastPlayerPacket();
    }

    public long getLastPlayerPacket() {
        return this.lastPlayerPacket;
    }
    public void setLastPlayerPacket(long l) {
        this.lastPlayerPacket = l;
    }

    public void setLastReceivedKeepAlive(long l) {
        this.lastReceivedKeepAlive = l;
    }
    public long getLastTypeBPingSpoof() {
        return LastTypeBPingSpoof;
    }

    public void setLastTypeBPingSpoof(long lastTypeBPingSpoof) {
        LastTypeBPingSpoof = lastTypeBPingSpoof;
    }

    public int getPingSpoof_VL2() {
        return PingSpoof_VL2;
    }

    public long getLastTypeBPingSpoof2() {
        return LastTypeBPingSpoof2;
    }

    public void setPingSpoof_VL2(int pingSpoof_VL2) {
        PingSpoof_VL2 = pingSpoof_VL2;
    }
    public boolean isDidSetPingspoof2() {
        return DidSetPingspoof2;
    }

    public void setDidSetPingspoof2(boolean didSetPingspoof2) {
        DidSetPingspoof2 = didSetPingspoof2;
    }

    public int getLastReceivedKeepAliveID() {
        return lastReceivedKeepAliveID;
    }

    public void setLastSentKeepAlive(long lastSentKeepAlive) {
        this.lastSentKeepAlive = lastSentKeepAlive;
    }

    public void setLastTypeBPingSpoof2(long lastTypeBPingSpoof2) {
        LastTypeBPingSpoof2 = lastTypeBPingSpoof2;
    }

    public int getLastPingBeforKick_Pingspoof() {
        return LastPingBeforKick_Pingspoof;
    }

    public void setLastPingBeforKick_Pingspoof(int lastPingBeforKick_Pingspoof) {
        LastPingBeforKick_Pingspoof = lastPingBeforKick_Pingspoof;
    }

    public boolean isDidGetKicked_Pingspoof() {
        return didGetKicked_Pingspoof;
    }

    public void setDidGetKicked_Pingspoof(boolean didGetKicked_Pingspoof) {
        this.didGetKicked_Pingspoof = didGetKicked_Pingspoof;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSlowPingSpoof_Count() {
        return SlowPingSpoof_Count;
    }

    public void setSlowPingSpoof_Count(int slowPingSpoof_Count) {
        SlowPingSpoof_Count = slowPingSpoof_Count;
    }

    public Location getSetbackLocation() {
        return setbackLocation;
    }

    public void setSetbackLocation(Location setbackLocation) {
        this.setbackLocation = setbackLocation;
    }

    public long getGoinUp_MS() {
        return GoingUp_MS;
    }

    public void setGoinUp_MS(long goinUp_MS) {
        GoingUp_MS = goinUp_MS;
    }

    public long getGoingUp_MS() {
        return GoingUp_MS;
    }

    public void setGoingUp_MS(long goingUp_MS) {
        GoingUp_MS = goingUp_MS;
    }

    public double getGoingUp_Blocks() {
        return GoingUp_Blocks;
    }

    public void setGoingUp_Blocks(double goingUp_Blocks) {
        GoingUp_Blocks = goingUp_Blocks;
    }

    public double getLastY_Gravity() {
        return LastY_Gravity;
    }

    public void setLastY_Gravity(double lastY_Gravity) {
        LastY_Gravity = lastY_Gravity;
    }

    public int getGravity_VL() {
        return Gravity_VL;
    }

    public void setGravity_VL(int gravity_VL) {
        Gravity_VL = gravity_VL;
    }
}
