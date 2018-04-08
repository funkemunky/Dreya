package cc.funkemunky.dreya;

import cc.funkemunky.dreya.command.DreyaCommand;
import cc.funkemunky.dreya.data.DataManager;
import cc.funkemunky.dreya.events.UtilityJoinQuitEvent;
import cc.funkemunky.dreya.events.UtilityMoveEvent;
import cc.funkemunky.dreya.util.Ping;
import cc.funkemunky.dreya.util.SetBackSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Dreya extends JavaPlugin {

    private static Dreya instance;
    private DataManager dataManager;

    public void onEnable() {
        instance = this;
        dataManager = new DataManager();
        registerCommands();
        registerListeners();
        new Ping(this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            getInstance().getDataManager().addPlayerData(p);
        }
    }

    private void registerCommands() {
        getCommand("dreya").setExecutor(new DreyaCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new UtilityMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new UtilityJoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new SetBackSystem(), this);
    }

    public static Dreya getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
