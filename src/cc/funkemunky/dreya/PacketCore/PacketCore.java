package cc.funkemunky.dreya.PacketCore;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.PacketCore
 */
public class PacketCore {
    public static void Init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.POSITION_LOOK) {

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, packetEvent.getPacket().getDoubles().read(0), packetEvent.getPacket().getDoubles().read(1), packetEvent.getPacket().getDoubles().read(2), packetEvent.getPacket().getFloat().read(0).floatValue(), packetEvent.getPacket().getFloat().read(1).floatValue()));
            }
        });
         ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.LOOK){

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), packetEvent.getPacket().getFloat().read(0).floatValue(), packetEvent.getPacket().getFloat().read(1).floatValue()));
            }
        });
                 ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.POSITION){

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, packetEvent.getPacket().getDoubles().read(0), packetEvent.getPacket().getDoubles().read(1), packetEvent.getPacket().getDoubles().read(2), player.getLocation().getYaw(), player.getLocation().getPitch()));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.FLYING){

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
            }
        });
          ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), new PacketType[]{PacketType.Play.Client.KEEP_ALIVE}){

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                PlayerData data = Dreya.getInstance().getDataManager().getData(player);
                if (data != null) {
                 data.setLastReceivedKeepAlive(System.currentTimeMillis());
                 data.setLastReceivedKeepAliveID(packetEvent.getPacket().getIntegers().read(0));
                }
            }
        });
    }
}

