package cc.funkemunky.dreya.events;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.events
 */
public class PacketListener implements Listener {
    @EventHandler
    public void onPacketPlayerEvent(PacketPlayerEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Dreya.getInstance().getDataManager().getData(p);
        if (data != null) {
        if (data.getLastPlayerPacketDiff() > 200) {
            data.setLastDelayedPacket(System.currentTimeMillis());
        }
        data.setLastPlayerPacket(System.currentTimeMillis());
        }
    }
}
