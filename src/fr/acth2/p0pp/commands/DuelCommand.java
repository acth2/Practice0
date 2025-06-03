package fr.acth2.p0pp.commands;

import fr.acth2.p0pp.Main;
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

        handleDuelStart(player, args[0]);
        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage("§6Usage du duel:");
        player.sendMessage("§6/duel <joueur> - Défier un joueur");
        player.sendMessage("§6/duel end - Terminer le duel");
    }

    private void handleDuelEnd(Player player) {
        if (!plugin.isInDuel(player)) {
            player.sendMessage("§cVous n'êtes pas dans un duel !");
            return;
        }

        plugin.endPracticeMatch();
    }

    private void handleDuelStart(Player player, String targetName) {
        Player target = plugin.getServer().getPlayer(targetName);

        if (target == null) {
            player.sendMessage("§cJoueur introuvable !");
            return;
        }

        if (target.equals(player)) {
            player.sendMessage("§cVous ne pouvez pas vous défier vous-même !");
            return;
        }

        if (plugin.isInDuel(player) || plugin.isInDuel(target)) {
            player.sendMessage("§cUn des joueurs est déjà en duel !");
            return;
        }

        plugin.startPracticeMatch(player, target);
    }
}