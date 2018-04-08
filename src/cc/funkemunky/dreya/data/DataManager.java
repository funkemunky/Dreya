package cc.funkemunky.dreya.data;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.movement.Example;
import org.bukkit.entity.Player;

import java.util.*;

public class DataManager {

    public List<Check> checks;
    private Map<Player, Map<Check, Integer>> violations;

    public DataManager() {
        checks = new ArrayList<>();

        violations = new WeakHashMap<>();

        addChecks();
    }

    private void addChecks() {
        addCheck(new Example());
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

    public void addCheck(Check check) {
        if(!checks.contains(check)) checks.add(check);
    }

    public List<Check> getChecks() {
        return checks;
    }
}
