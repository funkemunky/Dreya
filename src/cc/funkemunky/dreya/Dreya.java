package cc.funkemunky.dreya;

import cc.funkemunky.dreya.PacketCore.PacketCore;
import cc.funkemunky.dreya.command.DreyaCommand;
import cc.funkemunky.dreya.data.DataManager;
import cc.funkemunky.dreya.events.UtilityJoinQuitEvent;
import cc.funkemunky.dreya.events.UtilityMoveEvent;
import cc.funkemunky.dreya.util.Ping;
import cc.funkemunky.dreya.util.SetBackSystem;
import cc.funkemunky.dreya.util.TimerUtils;
import cc.funkemunky.dreya.util.VelocityUtils;
import io.netty.util.Timer;
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
        registerCommands();
        registerListeners();
        new Ping(this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            getInstance().getDataManager().addPlayerData(p);
        }
        PacketCore.Init();
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
    }

    public static Dreya getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
