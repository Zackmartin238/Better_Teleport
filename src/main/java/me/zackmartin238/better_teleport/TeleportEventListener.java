package me.zackmartin238.better_teleport;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class TeleportEventListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = Objects.requireNonNull(event.getTo()).getBlock();

        if (block.getType().isSolid() && player.getFallDistance() > 0) {
            player.setFallDistance(0);
            // Additional actions if needed
        }
    }
}
