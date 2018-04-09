package cc.funkemunky.dreya.util;

import cc.funkemunky.dreya.Dreya;
import org.bukkit.ChatColor;

public class Config {

    public static String ALERTS_MESSAGE = translate("%prefix%&e%player% &7failed &e%check% &8%data% &cx%vl%");

    public Config() {
        Dreya.getInstance().saveDefaultConfig();

        //Broken idk why
        init();
    }

    private void init() {
        ALERTS_MESSAGE = translate(Dreya.getInstance().getConfig().getString("Messages.ALERTS_MESSAGE"));
    }

    private static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string.replaceAll("%prefix%", getPrefix()));
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', Dreya.getInstance().getConfig().getString("Messages.PREFIX"));
    }


}
