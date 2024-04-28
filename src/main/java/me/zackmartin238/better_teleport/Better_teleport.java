package me.zackmartin238.better_teleport;



import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.logging.*;

public final class Better_teleport extends JavaPlugin {

    @Override
    public void onEnable() {
        final Logger LOGGER = Logger.getLogger("Better_Teleport");
        LOGGER.info("Better Teleport Initialized");
        getServer().getPluginManager().registerEvents(new TeleportEventListener(), this);
        // Registering the command
        Objects.requireNonNull(getCommand("teleport_forward8")).setExecutor(new TeleportForward());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        final Logger LOGGER = Logger.getLogger("Better_Teleport");
        LOGGER.info("Better Teleport Disabled");
    }
}

