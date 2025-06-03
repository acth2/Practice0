package fr.acth2.p0pp.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class Arena {

    private static int id;
    private static Location player1spawn;
    private static Location player2spawn;
    private static boolean start;
    private static Player player1;
    private static Player player2;
    public Arena(int id, Location player1spawn, Location player2spawn, boolean start) {
        id = this.id;
        player1spawn = this.player1spawn;
        player2spawn = this.player2spawn;
        start = this.start;
    }

    public boolean hasStarted() {
        return start;
    }

    public void setCurrentPlayers(@Nullable  Player player1, @Nullable Player player2) {
        if (player1 != null) {
            player1 = this.player1;
        }

        if (player2 != null) {
            player2 = this.player2;
        }
    }

    public boolean hasEnded() {
        return  start == true ? true : false;
    }

    public void clear() {
        player1 = null;
        player2 = null;
        start = false;
    }
}
