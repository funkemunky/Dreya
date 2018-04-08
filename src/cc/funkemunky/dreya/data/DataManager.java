package cc.funkemunky.dreya.data;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.combat.KillAura;
import cc.funkemunky.dreya.check.combat.Reach;
import cc.funkemunky.dreya.check.movement.Fly;
import cc.funkemunky.dreya.check.movement.Gravity;
import cc.funkemunky.dreya.check.player.GroundSpoofCheck;
<<<<<<< HEAD
import cc.funkemunky.dreya.check.player.ImpossibleMovements;
=======
>>>>>>> 44c84dc60cfb5b53d130a9c44b2b4c1b949d7106
import cc.funkemunky.dreya.check.player.ImpossiblePitch;
import cc.funkemunky.dreya.check.player.LineOfSight;
import cc.funkemunky.dreya.check.combat.Criticals;
import org.bukkit.entity.Player;

import java.util.*;

public class DataManager {

    public List<Check> checks;
    private Map<Player, Map<Check, Integer>> violations;
    public List<PlayerData> players;

    public DataManager() {
        checks = new ArrayList<>();
        violations = new WeakHashMap<>();
        players = new ArrayList<>();

        addChecks();
    }

    private void addChecks() {
        addCheck(new ImpossiblePitch());
        addCheck(new LineOfSight());
        addCheck(new Fly());
        addCheck(new Criticals());
        addCheck(new GroundSpoofCheck());
        addCheck(new Reach());
        addCheck(new Gravity());
<<<<<<< HEAD
<<<<<<< HEAD
        addCheck(new ImpossibleMovements());
=======
        addCheck(new KillAura());
>>>>>>> 00ad5272dd8f08fd07f0d1f6a2ec91326a2de6a6
=======
        addCheck(new KillAura());
>>>>>>> 44c84dc60cfb5b53d130a9c44b2b4c1b949d7106
    }

    public void removeCheck(Check check) {
        if(checks.contains(check)) checks.remove(check);
    }

    public boolean isCheck(Check check) {
        return checks.contains(check);
    }

    public Check getCheckByName(String checkName) {
        for(Check checkLoop : Collections.synchronizedList(checks)) {
            if(checkLoop.getName().equalsIgnoreCase(checkName)) return checkLoop;
        }

        return null;
    }

    public Map<Player, Map<Check, Integer>> getViolationsMap() {
        return violations;
    }

    public int getViolatonsPlayer(Player player, Check check) {
        if(violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            return vlMap.getOrDefault(check, 0);
        }
        return 0;
    }

    public void addViolation(Player player, Check check) {
        if (violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            vlMap.put(check, vlMap.getOrDefault(check, 0) + 1);
            violations.put(player, vlMap);
        } else {
            Map<Check, Integer> vlMap = new HashMap<>();

            vlMap.put(check, 1);

            violations.put(player, vlMap);
        }
    }

    public void addPlayerData(Player player) {
        players.add(new PlayerData(player));
    }

    public PlayerData getData(Player player) {
        for(PlayerData dataLoop : Collections.synchronizedList(players)) {
            if(dataLoop.getPlayer() == player) {
                return dataLoop;
            }
        }
        return null;
    }

    public void removePlayerData(Player player) {
        for(PlayerData dataLoop : Collections.synchronizedList(players)) {
            if(dataLoop.getPlayer() == player) {
                players.remove(dataLoop);
                break;
            }
        }
    }

    public void addCheck(Check check) {
        if(!checks.contains(check)) checks.add(check);
    }

    public List<Check> getChecks() {
        return checks;
    }
}
