package cc.funkemunky.dreya.data;

import cc.funkemunky.dreya.check.Check;
import cc.funkemunky.dreya.check.movement.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManager {

    public List<Check> checks;

    public DataManager() {
        checks = new ArrayList<>();

        addChecks();
    }

    private void addChecks() {
        addCheck(new Test());
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

    public void addCheck(Check check) {
        if(!checks.contains(check)) checks.add(check);
    }

    public List<Check> getChecks() {
        return checks;
    }
}
