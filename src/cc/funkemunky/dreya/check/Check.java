package cc.funkemunky.dreya.check;

import cc.funkemunky.dreya.Dreya;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Check implements Listener {

    private String name;
    private CheckType type;
    private boolean enabled;

    public Check(String name, CheckType type, boolean enabled) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;

        Bukkit.getPluginManager().registerEvents(this, Dreya.getInstance());
    }

    public void flag(Player player, String data) {

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }
}
