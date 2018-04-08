package cc.funkemunky.dreya.check;

import cc.funkemunky.dreya.Dreya;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Check implements Listener {

    private String name;
    private CheckType type;
    private boolean enabled;

    public Check(String name, CheckType type, boolean enabled) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;

       if(enabled) Bukkit.getPluginManager().registerEvents(this, Dreya.getInstance());
    }

    protected void flag(Player player, String data) {
     /**   Dreya.getInstance().getDataManager().addViolation(player, this);
        for(Player playerLoop : Bukkit.getOnlinePlayers()) {
            if(playerLoop.hasPermission("dreya.alerts")) {
                playerLoop.sendMessage(Config.ALERTS_MESSAGE.replaceAll("%player%", player.getName()
                        .replaceAll("%data%", data != null ? ChatColor.DARK_GRAY + "(" + ChatColor.RED + data + ChatColor.DARK_GRAY + ")" : ""))
                .replaceAll("%check%", getName())
                .replaceAll("%vl%", Dreya.getInstance().getDataManager().getViolatonsPlayer(player, this) + ""));
            }
        }*/
        Dreya.getInstance().getDataManager().addViolation(player, this);
        for(Player playerLoop : Bukkit.getOnlinePlayers()) {
            if(playerLoop.hasPermission("dreya.alerts")) {
                playerLoop.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "!" + ChatColor.DARK_GRAY + "] "
                        + ChatColor.AQUA + player.getName() + ChatColor.GRAY + " failed " + ChatColor.AQUA + getName()
                        + (data != null ? ChatColor.DARK_GRAY + " (" + ChatColor.RED + data + ChatColor.DARK_GRAY + ") " : " ")
                        + ChatColor.RED + "x" + Dreya.getInstance().getDataManager().getViolatonsPlayer(player, this));
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CheckType getType() {
        return type;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if(this.enabled) {
            Bukkit.getPluginManager().registerEvents(this, Dreya.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
    }

    public String getName() {
        return name;
    }
}
