package cc.funkemunky.dreya.data;

import org.bukkit.entity.Player;

public class PlayerData {

    /** Data fields **/
    private Player player;
    private boolean alerts = false;

    /** Violation fields **/
    //TODO Fields for checks.


    public PlayerData(Player player) {
        this.player = player;
    }


}
