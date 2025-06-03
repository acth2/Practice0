package fr.acth2.p0pp.objects;

import org.bukkit.ChatColor;
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

    public void startDuel() {
        if (player1 != null && player2 != null) {
            player1.teleport(player1spawn);
            player2.teleport(player2spawn);
            sendMessages(null);
        }
     }

     public void sendMessages(Player definedWinner) {
        if (definedWinner == null) {
            player1.sendMessage(ChatColor.GOLD + "===================");
            player1.sendMessage(ChatColor.YELLOW + "Player " + player1.getName() + " (you)" + ChatColor.GRAY + " against");
            player1.sendMessage(ChatColor.YELLOW + "Player " + player2.getName());
            player1.sendMessage(ChatColor.GRAY +  "&oMay you be the winner of this fight");
            player1.sendMessage(ChatColor.GOLD + "===================");

            player2.sendMessage(ChatColor.GOLD + "===================");
            player2.sendMessage(ChatColor.YELLOW + "Player " + player2.getName() + " (you)" + ChatColor.GRAY + " against");
            player2.sendMessage(ChatColor.YELLOW + "Player " + player1.getName());
            player2.sendMessage(ChatColor.GRAY +  "&oMay you be the winner of this fight");
            player2.sendMessage(ChatColor.GOLD + "===================");
        } else {
            if (definedWinner == player1) {
                player1.sendMessage(ChatColor.GOLD + "=================== RESULTS");
                player1.sendMessage(ChatColor.GREEN + "Player " + player1.getName() + " won");
                player1.sendMessage(ChatColor.RED + "Player " + player2.getName() + " lose");
                player1.sendMessage(ChatColor.GRAY +  "&oYou won the fight!");
                player1.sendMessage(ChatColor.GOLD + "=================== RESULTS");

                player2.sendMessage(ChatColor.GOLD + "=================== RESULTS");
                player2.sendMessage(ChatColor.GREEN + "Player " + player1.getName() + " won");
                player2.sendMessage(ChatColor.RED + "Player " + player2.getName() + " lose");
                player2.sendMessage(ChatColor.GRAY +  "&oYou lose the fight!");
                player2.sendMessage(ChatColor.GOLD + "=================== RESULTS");
            } else {
                player2.sendMessage(ChatColor.GOLD + "=================== RESULTS");
                player2.sendMessage(ChatColor.GREEN + "Player " + player2.getName() + " won");
                player2.sendMessage(ChatColor.RED + "Player " + player1.getName() + " lose");
                player2.sendMessage(ChatColor.GRAY +  "&oYou won the fight!");
                player2.sendMessage(ChatColor.GOLD + "=================== RESULTS");

                player1.sendMessage(ChatColor.GOLD + "=================== RESULTS");
                player1.sendMessage(ChatColor.GREEN + "Player " + player2.getName() + " won");
                player1.sendMessage(ChatColor.RED + "Player " + player1.getName() + " lose");
                player1.sendMessage(ChatColor.GRAY +  "&oYou lose the fight!");
                player1.sendMessage(ChatColor.GOLD + "=================== RESULTS");
            }
        }
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
