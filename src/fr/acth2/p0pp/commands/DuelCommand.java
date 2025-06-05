package fr.acth2.p0pp.commands;

import fr.acth2.p0pp.Main;
import fr.acth2.p0pp.objects.Arena;
import fr.acth2.p0pp.utils.References;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {
    private final Main plugin;

    public DuelCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSeuls les joueurs peuvent utiliser cette commande !");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("end")) {
            handleDuelEnd(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            handleArenaList(player);
            return true;
        }

        handleDuelStart(player, args[0]);
        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage("§6=== Utilisation du Duel ===");
        player.sendMessage("§6/duel <joueur> §f- Défier un joueur");
        player.sendMessage("§6/duel end §f- Terminer votre duel");
        player.sendMessage("§6/duel list §f- Voir les arènes disponibles");
    }

    private void handleDuelEnd(Player player) {
        if (!References.inDuelList.contains(player)) {
            player.sendMessage("§cVous n'êtes pas dans un duel !");
            return;
        }

        Arena arena = findPlayerArena(player);
        if (arena != null) {
            Player opponent = (player.equals(arena.getPlayer1())) ? arena.getPlayer2() : arena.getPlayer1();
            arena.endDuel(player);
            References.removePlayersFromDuel(player, opponent);
            player.sendMessage("§aVous avez abandonné le duel !");
            opponent.sendMessage("§cVotre adversaire a abandonné le duel !");
        }
    }

    private void handleArenaList(Player player) {
        StringBuilder message = new StringBuilder("§6=== Arènes Disponibles ===\n");
        for (Arena arena : plugin.arenas) {
            String status = arena.hasStarted() ? "§cOccupée" : "§aLibre";
            message.append("§7Arène #").append(arena.getId()).append(": ").append(status).append("\n");
        }
        player.sendMessage(message.toString());
    }

    private void handleDuelStart(Player challenger, String targetName) {
        Player target = plugin.getServer().getPlayer(targetName);

        if (target == null) {
            challenger.sendMessage("§cJoueur introuvable !");
            return;
        }

        if (target.equals(challenger)) {
            challenger.sendMessage("§cVous ne pouvez pas vous défier vous-même !");
            return;
        }

        if (References.inDuelList.contains(challenger) || References.inDuelList.contains(target)) {
            challenger.sendMessage("§cUn des joueurs est déjà en duel !");
            return;
        }

        if (plugin.getAvailableArena() == null) {
            challenger.sendMessage("§cAucune arène disponible actuellement !");
            return;
        }

        challenger.sendMessage("§aDéfi envoyé à " + target.getName() + " !");
        target.sendMessage("§6" + challenger.getName() + " vous défie en duel !");
        target.sendMessage("§6Tapez §a/accept " + challenger.getName() + " §6pour accepter");

        plugin.addPendingDuel(challenger, target);
    }

    private Arena findPlayerArena(Player player) {
        for (Arena arena : plugin.arenas) {
            if (arena.hasStarted() &&
                    (player.equals(arena.getPlayer1()) || player.equals(arena.getPlayer2()))) {
                return arena;
            }
        }
        return null;
    }
}