package fr.acth2.p0pp;

import fr.acth2.p0pp.commands.DuelCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private Player player1;
    private Player player2;
    private Location player1OriginalLoc;
    private Location player2OriginalLoc;

    private Location arenaPlayer1Loc;
    private Location arenaPlayer2Loc;

    @Override
    public void onEnable() {
        getLogger().info("Plugin Practice - ACTIVÉ");

        World world = Bukkit.getWorld("world");
        if (world != null) {
            arenaPlayer1Loc = new Location(world, 0, 100, 0);
            arenaPlayer2Loc = new Location(world, 100, 100, 0);
        } else {
            getLogger().severe("Le monde principal 'world' n'existe pas !");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("duel").setExecutor(new DuelCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Practice - DÉSACTIVÉ");
        if (isDuelActive()) {
            endPracticeMatch();
        }
    }

    public void startPracticeMatch(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        this.player1OriginalLoc = p1.getLocation();
        this.player2OriginalLoc = p2.getLocation();

        try {
            player1.teleport(arenaPlayer1Loc);
            player2.teleport(arenaPlayer2Loc);

            player1.sendMessage("§aDuel commencé ! Vous êtes dans l'arène.");
            player2.sendMessage("§aDuel commencé ! Vous êtes dans l'arène.");

            getLogger().info("Duel entre " + player1.getName() + " et " + player2.getName() + " a commencé");
        } catch (Exception e) {
            getLogger().severe("Erreur de téléportation: " + e.getMessage());
            player1.sendMessage("§cErreur lors du démarrage du duel !");
            player2.sendMessage("§cErreur lors du démarrage du duel !");
        }
    }

    public void endPracticeMatch() {
        if (!isDuelActive()) return;

        try {
            player1.teleport(player1OriginalLoc);
            player2.teleport(player2OriginalLoc);

            player1.sendMessage("§aDuel terminé ! Vous avez été ramené à votre position initiale.");
            player2.sendMessage("§aDuel terminé ! Vous avez été ramené à votre position initiale.");

            getLogger().info("Duel entre " + player1.getName() + " et " + player2.getName() + " est terminé");
        } catch (Exception e) {
            getLogger().severe("Erreur lors de la fin du duel: " + e.getMessage());
        } finally {
            player1 = null;
            player2 = null;
            player1OriginalLoc = null;
            player2OriginalLoc = null;
        }
    }

    public boolean isInDuel(Player player) {
        return isDuelActive() && (player.equals(player1) || player.equals(player2));
    }

    private boolean isDuelActive() {
        return player1 != null && player2 != null;
    }
}