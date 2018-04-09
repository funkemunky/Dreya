package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.PacketCore.PacketCore;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import cc.funkemunky.dreya.util.SetBackSystem;
import cc.funkemunky.dreya.util.TimerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * Created by Mr_JaVa_ on 2018-04-09 Package cc.funkemunky.dreya.check.player
 */
public class MorePackets extends Check {
    private Map<UUID, Integer> packets;
    private Map<UUID, Integer> verbose;
    private Map<UUID, Long> lastPacket;
    private List<Player> toCancel;

    public MorePackets() {
        super("MorePackets", CheckType.MISC, true);
        packets = new HashMap<>();
        verbose = new HashMap<>();
        toCancel = new ArrayList<>();
        lastPacket = new HashMap<>();
    }
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
    @EventHandler
    public void packetPlayer(PacketPlayerEvent event) {
        Player player = event.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(player);

        int packets = this.packets.getOrDefault(player.getUniqueId(), 0);
        long Time = this.lastPacket.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);

        if((System.currentTimeMillis() - data.getLastPacket()) > 100L) {
            toCancel.add(player);
        }
        double threshold = 42;
        if(TimerUtils.elapsed(Time, 1000L)) {
            if(toCancel.remove(player) && packets <= 67) {
                this.packets.put(player.getUniqueId(), 0);
               return;
            }
            if(packets > threshold + PacketCore.movePackets.getOrDefault(player.getUniqueId(), 0)) {
                verbose++;
            } else {
                verbose = 0;
            }

            //Bukkit.broadcastMessage(packets + ", " + verbose);

            if(verbose > 2) {
               flag(player,"Type: A");
                SetBackSystem.setBack(player);
            }
            packets = 0;
            Time = System.currentTimeMillis();
            PacketCore.movePackets.remove(player.getUniqueId());
        }
        packets++;

        this.packets.put(player.getUniqueId(), packets);
        this.verbose.put(player.getUniqueId(), verbose);
        this.lastPacket.put(player.getUniqueId(), Time);
    }
}
