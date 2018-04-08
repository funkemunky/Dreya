package cc.funkemunky.dreya;

import cc.funkemunky.dreya.PacketCore.PacketCore;
import cc.funkemunky.dreya.command.DreyaCommand;
import cc.funkemunky.dreya.data.DataManager;
import cc.funkemunky.dreya.events.UtilityJoinQuitEvent;
import cc.funkemunky.dreya.events.UtilityMoveEvent;
import cc.funkemunky.dreya.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Dreya extends JavaPlugin {

    private static Dreya instance;
    private DataManager dataManager;
    public static long MS_PluginLoad;
    public static String coreVersion;
    public void onEnable() {
        instance = this;
        dataManager = new DataManager();
        new Config();
        registerCommands();
        registerListeners();
        new Ping(this);
        addDataPlayers();
        PacketCore.init();
        MS_PluginLoad = TimerUtils.nowlong();
        coreVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    private void registerCommands() {
        getCommand("dreya").setExecutor(new DreyaCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new UtilityMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new UtilityJoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new SetBackSystem(), this);
        getServer().getPluginManager().registerEvents(new VelocityUtils(), this);
        getServer().getPluginManager().registerEvents(new NEW_Velocity_Utils(), this);
    }

    public static Dreya getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    private void addDataPlayers() {
        for (Player playerLoop : Bukkit.getOnlinePlayers()) {
            getInstance().getDataManager().addPlayerData(playerLoop);
        }
    }
}
