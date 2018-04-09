package cc.funkemunky.dreya.check.player;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.CheckType;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent;
import cc.funkemunky.dreya.events.PluginEvents.PacketPlayerEvent2;
import cc.funkemunky.dreya.util.SetBackSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * Created by Mr_JaVa_ on 2018-04-09 Package cc.funkemunky.dreya.check.player
 */
public class TimerB extends Check {
    public TimerB() {
        super("Timer", CheckType.MISC, true);
        lastTimer = new HashMap<UUID, Long>();
        MS = new HashMap<UUID, List<Long>>();
        timerTicks = new HashMap<UUID, Integer>();
    }
    public static Map<UUID, Long> lastTimer;
    public static Map<UUID, List<Long>> MS;
    public static Map<UUID, Integer> timerTicks;

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(lastTimer.containsKey(e.getPlayer().getUniqueId())) {
            lastTimer.remove(e.getPlayer().getUniqueId());
        }
        if(MS.containsKey(e.getPlayer().getUniqueId())) {
            MS.remove(e.getPlayer().getUniqueId());
        }
        if(timerTicks.containsKey(e.getPlayer().getUniqueId())) {
            timerTicks.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void PacketPlayer(PacketPlayerEvent2 e) {
        Player p = e.getPlayer();
        Player player = e.getPlayer();
        int Count = 0;
        if (timerTicks.containsKey(player.getUniqueId())) {
            Count = timerTicks.get(player.getUniqueId()).intValue();
        }
        if (lastTimer.containsKey(player.getUniqueId())) {
            long MS = System.currentTimeMillis() - lastTimer.get(player.getUniqueId()).longValue();
            List<Long> List = new ArrayList();
            if (TimerB.MS.containsKey(player.getUniqueId())) {
                List =TimerB.MS.get(player.getUniqueId());
            }
            List.add(Long.valueOf(MS));
            if (List.size() == 20) {
                boolean doeet = true;
                for (Long ListMS : List) {
                    if (ListMS.longValue() < 1L) {
                        doeet = false;
                    }
                }
                Long average = Long.valueOf(averageLong(List));
                if ((average.longValue() < 48L) && (doeet)) {
                    Count++;
                } else {
                    Count = 0;
                }
                TimerB.MS.remove(player.getUniqueId());
            } else {
                TimerB.MS.put(player.getUniqueId(), List);
            }
        }
        if (Count > 2) {
            Count = 2;
            flag(p,"Type: B");
            SetBackSystem.setBack(p);
        }
        lastTimer.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        timerTicks.put(player.getUniqueId(), Integer.valueOf(Count));
    }
    public static long averageLong(List<Long> list) {
        long add = 0L;
        for (final Long listlist : list) {
            add += listlist;
        }
        return add / list.size();
    }
}
