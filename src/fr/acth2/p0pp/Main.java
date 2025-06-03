package fr.acth2.p0pp;

import fr.acth2.p0pp.commands.AcceptCommand;
import fr.acth2.p0pp.commands.DuelCommand;
import fr.acth2.p0pp.listener.DuelDeathChecker;
import fr.acth2.p0pp.objects.Arena;
import fr.acth2.p0pp.utils.References;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    public List<Arena> arenas = new ArrayList<>();
    private static Main instance;
    private Map<Player, Player> pendingDuels = new HashMap<>();


    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new DuelDeathChecker(), this);
        getCommand("accept").setExecutor(new AcceptCommand(this));
        getCommand("duel").setExecutor(new DuelCommand(this));
        getLogger().info("Plugin Practice - ACTIVÉ");

        World world = Bukkit.getWorld("world");
        if (world != null) {
            arenas.add(new Arena(1, new Location(world, 100, 100, 100), new Location(world, 100, 100, -100)));
            arenas.add(new Arena(2, new Location(world, -100, 100, 100), new Location(world, -100, 100, -100)));
            arenas.add(new Arena(3, new Location(world, 0, 100, 200), new Location(world, 0, 100, -200)));
        } else {
            getLogger().severe("Le monde principal 'world' n'existe pas !");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("duel").setExecutor(new DuelCommand(this));
    }

    public Arena getAvailableArena() {
        for (Arena arena : arenas) {
            if (!arena.hasStarted()) {
                return arena;
            }
        }
        return null;
    }

    public void startDuel(Player p1, Player p2) {
        Arena arena = getAvailableArena();
        if (arena == null) {
            p1.sendMessage("§cNo available arenas!");
            p2.sendMessage("§cNo available arenas!");
            return;
        }

        arena.startDuel(p1, p2);
        References.addPlayersToDuel(p1, p2);
    }

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onDisable() {
        getLogger().info("Plugin Practice - DÉSACTIVÉ");
        References.clearAllDuels();
    }

    public void addPendingDuel(Player challenger, Player target) {
        pendingDuels.put(target, challenger);
    }

    public void acceptDuel(Player target) {
        Player challenger = pendingDuels.get(target);
        if (challenger != null && challenger.isOnline()) {
            startDuel(challenger, target);
            pendingDuels.remove(target);
        } else {
            target.sendMessage("§cLe défi a expiré ou le joueur s'est déconnecté !");
        }
    }


}