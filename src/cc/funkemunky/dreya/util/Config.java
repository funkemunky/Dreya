package cc.funkemunky.dreya.util;

import cc.funkemunky.dreya.Dreya;
import org.bukkit.ChatColor;

public class Config {

    public static String ALERTS_MESSAGE = translate("%prefix%&b%player% &7failed &b%check% &8%data% &cx%vl%");

    public Config() {
        Dreya.getInstance().saveDefaultConfig();

        init();
    }

    private void init() {
        ALERTS_MESSAGE = translate(Dreya.getInstance().getConfig().getString("ALERTS_MESSAGE"));
    }

    private static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string.replaceAll("%prefix%", getPrefix()));
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', Dreya.getInstance().getConfig().getString("Messages.PREFIX"));
    }


}