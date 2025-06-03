package fr.acth2.p0pp.objects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {
    private final int id;
    private final Location player1Spawn;
    private final Location player2Spawn;
    private boolean started;
    private Player player1;
    private Player player2;

    public Arena(int id, Location player1Spawn, Location player2Spawn) {
        this.id = id;
        this.player1Spawn = player1Spawn;
        this.player2Spawn = player2Spawn;
        this.started = false;
    }

    public void startDuel(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.started = true;

        player1.teleport(player1Spawn);
        player2.teleport(player2Spawn);
        sendStartMessages();
    }

    private void sendStartMessages() {
        player1.sendMessage(ChatColor.GOLD + "Duel started in Arena #" + id);
        player2.sendMessage(ChatColor.GOLD + "Duel started in Arena #" + id);
    }

    public void endDuel(Player winner) {
        sendEndMessages(winner);
        clear();
    }

    private void sendEndMessages(Player winner) {
        String winnerMsg = ChatColor.GREEN + winner.getName() + " won the duel!";
        String loserMsg = ChatColor.RED + "You lost the duel!";

        winner.sendMessage(winnerMsg);
        (winner == player1 ? player2 : player1).sendMessage(loserMsg);
    }

    public void clear() {
        player1 = null;
        player2 = null;
        started = false;
    }



    public boolean hasStarted() {
        return started;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}