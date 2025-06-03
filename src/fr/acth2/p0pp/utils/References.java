package fr.acth2.p0pp.utils;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class References {
    public static List<Player> inDuelList = new ArrayList<>();

    public static void addPlayersToDuel(Player player1, Player player2) {
        if (!inDuelList.contains(player1)) inDuelList.add(player1);
        if (!inDuelList.contains(player2)) inDuelList.add(player2);
    }

    public static void removePlayersFromDuel(Player player1, Player player2) {
        inDuelList.remove(player1);
        inDuelList.remove(player2);
    }

    public static void clearAllDuels() {
        inDuelList.clear();
    }
}