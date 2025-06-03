package fr.acth2.p0pp.listener;

import fr.acth2.p0pp.Main;
import fr.acth2.p0pp.objects.Arena;
import fr.acth2.p0pp.utils.References;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DuelDeathChecker implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player deadPlayer = (Player) event.getEntity();
        if (!References.inDuelList.contains(deadPlayer)) return;

        for (Arena arena : Main.getInstance().arenas) {
            if (arena.hasStarted() &&
                    (deadPlayer.equals(arena.getPlayer1()) || deadPlayer.equals(arena.getPlayer2()))) {

                Player winner = deadPlayer.equals(arena.getPlayer1()) ?
                        arena.getPlayer2() : arena.getPlayer1();

                arena.endDuel(winner);
                References.removePlayersFromDuel(arena.getPlayer1(), arena.getPlayer2());
                break;
            }
        }
    }
}