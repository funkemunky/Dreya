package cc.funkemunky.dreya.check.combat;

import cc.funkemunky.dreya.PacketCore.PacketTypes;
import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.events.PluginEvents.PacketAttackEvent;
import cc.funkemunky.dreya.util.MathUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Reach extends Check {

    public Reach() {
        super("Reach", CheckType.COMBAT, true);
    }

    @EventHandler
    public void onAttack(PacketAttackEvent e) {
        if(e.getType() != PacketTypes.USE
                || e.getEntity() == null) {
            return;
        }

        Player player = e.getPlayer();
        Entity entity = e.getEntity();

        double distance = MathUtils.getHorizontalDistance(player.getLocation(), entity.getLocation()) - 0.35;
        double maxReach = 4.2;
        double yawDifference = 180 - Math.abs(Math.abs(player.getEyeLocation().getYaw()) - Math.abs(entity.getLocation().getYaw()));

        maxReach+= Math.abs(player.getVelocity().length() + entity.getVelocity().length()) * 0.4;
        maxReach+= yawDifference * 0.01;

        if(maxReach < 4.2) maxReach = 4.2;
        

        if(distance > maxReach) {
            flag(player, MathUtils.trim(3, distance) + " > " + MathUtils.trim(3, maxReach));
        }
    }
}
