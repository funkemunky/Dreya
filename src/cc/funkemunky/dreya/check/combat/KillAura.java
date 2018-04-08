package cc.funkemunky.dreya.check.combat;

import cc.funkemunky.dreya.Dreya;
import cc.funkemunky.dreya.PacketCore.PacketTypes;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.data.PlayerData;
import cc.funkemunky.dreya.events.PluginEvents.PacketAttackEvent;
import cc.funkemunky.dreya.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class KillAura extends Check {

    public KillAura() {
        super("KillAura", CheckType.COMBAT, true);
    }

    @EventHandler
    public void onAttack(PacketAttackEvent e) {
        if(e.getType() != PacketTypes.USE) {
            return;
        }

        Player player = e.getPlayer();
        Entity entity = e.getEntity();
        PlayerData data = Dreya.getInstance().getDataManager().getData(player);

        if(data == null) {
            return;
        }

        int verboseA = data.getKillauraAVerbose();
        long time = data.getLastAimTime();

        if(MathUtils.elapsed(time, 1000L)) {
            time = System.currentTimeMillis();
            verboseA = 0;
        }

        if ((Math.abs(data.getLastKillauraPitch() - e.getPlayer().getEyeLocation().getPitch()) > 1
                || angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()) > 1
                || Double.compare(player.getEyeLocation().getYaw(), data.getLastKillauraYaw()) != 0)
                && !MathUtils.elapsed(data.getLastPacket(), 100L)) {

            if(angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()) != data.getLastKillauraYawDif()) {
                if(++verboseA > 9) {
                    flag(player, "Type: A");
                }
                Bukkit.broadcastMessage("Verbose: " + verboseA);
            }
            data.setLastKillauraYawDif(angleDistance((float) data.getLastKillauraYaw(), player.getEyeLocation().getYaw()));
        } else {
            verboseA = 0;
        }

        data.setKillauraAVerbose(verboseA);
        data.setLastAimTime(time);
    }

    public static float angleDistance(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        return phi > 180 ? 360 - phi : phi;
    }
}
