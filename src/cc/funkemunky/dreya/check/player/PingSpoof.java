package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import cc.funkemunky.dreya.util.Ping;
import cc.funkemunky.dreya.util.TimerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.check.player
 */
public class PingSpoof extends Check {
    public PingSpoof() {
        super("Ping Spoof", CheckType.MISC, true);
    }
    ArrayList<Player> ShouldKick = new ArrayList<>();
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPacketPlayer(PacketPlayerEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(e.getPlayer());
        if (data != null) {
                data.setSlowPingSpoof_Count(0);
            if (data.getLastReceivedKeepAlive() == 0 || data.getLastDelayedPacketDiff() < 500) {
                return;
            }
            if (TimerUtils.elapsed(data.getLastTypeBPingSpoof(),19000L) && data.isDidSetPingspoof2()) {
                data.setPingSpoof_VL2(0);
            }
            if (data.isDidSetPingspoof2()) {
                if (TimerUtils.elapsed(data.getLastTypeBPingSpoof(),1000L)) {
                      data.setPingSpoof_VL2(0);
                    data.setDidSetPingspoof2(false);
                }
            }
            int Max = 2340;
            long lastRec = System.currentTimeMillis() - data.getLastReceivedKeepAlive();
            long lastKeepAlive = System.currentTimeMillis() - data.getLastSentKeepAlive();
            long Final = lastRec > 7000 && lastKeepAlive < 3000 ? lastRec + 3 : lastRec - 20;
            if (Final > Max) {
                data.setDidSetPingspoof2(true);
                data.setLastTypeBPingSpoof(TimerUtils.nowlong());
                if (data.getPingSpoof_VL2() >= 3) {
                    flag(p, "Type: A - Tried to spoof their ping.");
                    if (!ShouldKick.contains(p)) {
                        data.setLastPingBeforKick_Pingspoof(Ping.getPing(p));
                        data.setDidGetKicked_Pingspoof(true);
                        ShouldKick.add(p);
                    }
                } else {
                    data.setPingSpoof_VL2(data.getPingSpoof_VL2()+1);
                }
            }
            int Min = 19;
            int DoubleTimes = 0;
            String strFinal = String.valueOf(Final);
            if (Final < Min && strFinal.startsWith("-")) {
                DoubleTimes++;
                if (DoubleTimes > 1) {
                    flag(p,"Type: B - Tried to spoof their ping.");
                }
            }
        }
    }
}
