package cc.funkemunky.dreya;

import cc.funkemunky.dreya.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Dreya extends JavaPlugin {

    private static Dreya instance;
    private DataManager dataManager;

    public void onEnable() {
        instance = this;
        dataManager = new DataManager();

    }

    public static Dreya getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
