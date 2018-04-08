package cc.funkemunky.dreya;

import cc.funkemunky.dreya.command.DreyaCommand;
import cc.funkemunky.dreya.data.DataManager;
import cc.funkemunky.dreya.util.Ping;
import org.bukkit.plugin.java.JavaPlugin;

public class Dreya extends JavaPlugin {

    private static Dreya instance;
    private DataManager dataManager;

    public void onEnable() {
        instance = this;
        dataManager = new DataManager();

        registerCommands();
        new Ping(this);
    }

    private void registerCommands() {
        getCommand("dreya").setExecutor(new DreyaCommand());
    }

    public static Dreya getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
