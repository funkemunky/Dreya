package cc.funkemunky.dreya.PacketCore;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketAttackEvent;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.PacketCore
 */
public class PacketCore {

    public static void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.USE_ENTITY) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        Player player = event.getPlayer();
                        if (player == null) {
                            return;
                        }

                        EnumWrappers.EntityUseAction type;
                        try {
                            type = packet.getEntityUseActions().read(0);
                        } catch (Exception ex) {
                            return;
                        }

                        Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                        if (entity == null) {
                            return;
                        }

                        if (type == EnumWrappers.EntityUseAction.ATTACK) {
                            Bukkit.getServer().getPluginManager().callEvent(new PacketAttackEvent(player, entity, PacketTypes.USE));
                        }
                    }
                });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.LOOK) {

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), packetEvent.getPacket().getFloat().read(0), packetEvent.getPacket().getFloat().read(1), PacketTypes.LOOK));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.POSITION) {

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, packetEvent.getPacket().getDoubles().read(0), packetEvent.getPacket().getDoubles().read(1), packetEvent.getPacket().getDoubles().read(2), player.getLocation().getYaw(), player.getLocation().getPitch(), PacketTypes.POSITION));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.POSITION_LOOK) {

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }

                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, packetEvent.getPacket().getDoubles().read(0), packetEvent.getPacket().getDoubles().read(1), packetEvent.getPacket().getDoubles().read(2), packetEvent.getPacket().getFloat().read(0), packetEvent.getPacket().getFloat().read(1), PacketTypes.POSLOOK));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.FLYING) {

            public void onPacketReceiving(PacketEvent packetEvent) {
                Player player = packetEvent.getPlayer();
                if (player == null) {
                    return;
                }
                Bukkit.getServer().getPluginManager().callEvent(new PacketPlayerEvent(player, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch(), PacketTypes.FLYING));
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Dreya.getInstance(), PacketType.Play.Client.KEEP_ALIVE) {

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

