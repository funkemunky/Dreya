package cc.funkemunky.dreya.data;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.movement.Fly;
import cc.funkemunky.dreya.check.player.GroundSpoofCheck;
import cc.funkemunky.dreya.check.player.Impossible_pitch;
import cc.funkemunky.dreya.check.player.Line_Of_Sight_Check;
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
      //  addCheck(new Example());
        addCheck(new Impossible_pitch());
        addCheck(new Line_Of_Sight_Check());
        addCheck(new Fly());
        addCheck(new Criticals());
<<<<<<< HEAD
        addCheck(new GroundSpoofCheck());
=======
>>>>>>> 597246ca2a81eb1976b1a506b1cb7d266a71f2f5
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
