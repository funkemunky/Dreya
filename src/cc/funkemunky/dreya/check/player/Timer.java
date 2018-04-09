package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.PacketCore.PacketCore;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import cc.funkemunky.dreya.util.SetBackSystem;
import cc.funkemunky.dreya.util.TimerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * Created by Mr_JaVa_ on 2018-04-09 Package cc.funkemunky.dreya.check.player
 */
public class Timer extends Check {
    public Timer() {
        super("Timer", CheckType.MISC, true);
        packets = new HashMap<UUID, Map.Entry<Integer, Long>>();
        verbose = new HashMap<UUID, Integer>();
        toCancel = new ArrayList<Player>();
        lastPacket = new HashMap<UUID, Long>();
    }
    private Map<UUID, Map.Entry<Integer, Long>> packets;
    private Map<UUID, Integer> verbose;
    private Map<UUID, Long> lastPacket;
    private List<Player> toCancel;
    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        if(packets.containsKey(e.getPlayer().getUniqueId())) {
            packets.remove(e.getPlayer().getUniqueId());
        }
        if(verbose.containsKey(e.getPlayer().getUniqueId())) {
            verbose.remove(e.getPlayer().getUniqueId());
        }
        if(lastPacket.containsKey(e.getPlayer().getUniqueId())) {
            lastPacket.remove(e.getPlayer().getUniqueId());
        }
        if(toCancel.contains(e.getPlayer())) {
            toCancel.remove(e.getPlayer());
        }
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PacketPlayer(PacketPlayerEvent event) {
        Player player = event.getPlayer();
        long lastPacket = this.lastPacket.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        int packets = 0;
        long Time = System.currentTimeMillis();
        int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);
        if (this.packets.containsKey(player.getUniqueId())) {
            packets = this.packets.get(player.getUniqueId()).getKey().intValue();
            Time = this.packets.get(player.getUniqueId()).getValue().longValue();
        }

        if((System.currentTimeMillis() - lastPacket) > 100L) {
            toCancel.add(player);
        }
        double threshold = 23;
        if(TimerUtils.elapsed(Time, 1000L)) {
            if(toCancel.remove(player) && packets <= 13) {
                //	return;
            }
            if(packets >= threshold + PacketCore.movePackets.getOrDefault(player.getUniqueId(), 0) && PacketCore.movePackets.getOrDefault(player.getUniqueId(), 0) < 5) {
                verbose = (packets - threshold) > 10 ? verbose + 2 : verbose + 1;
            } else {
                verbose = 0;
            }

            if(verbose >= 2) {
               flag(player,"Type: A");
                SetBackSystem.setBack(player);
            }
            packets = 0;
            Time = TimerUtils.nowlong();
            PacketCore.movePackets.remove(player.getUniqueId());
        }
        packets++;

        this.lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
        this.packets.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(packets, Time));
        this.verbose.put(player.getUniqueId(), verbose);
    }
}
