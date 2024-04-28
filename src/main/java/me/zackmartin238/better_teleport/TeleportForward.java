package me.zackmartin238.better_teleport;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.bukkit.World;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



public class TeleportForward implements CommandExecutor {


    private static final Set<Material> ALLOWED_MATERIALS = new HashSet<>(Arrays.asList(
            Material.AIR,
            Material.BROWN_MUSHROOM,
            Material.RED_MUSHROOM,
            Material.WHEAT,
            Material.CARROTS,
            Material.SHORT_GRASS,
            Material.TALL_GRASS,
            Material.FLOWER_POT,
            Material.DANDELION,
            Material.POPPY,
            Material.BLUE_ORCHID,
            Material.ALLIUM,
            Material.AZURE_BLUET,
            Material.RED_TULIP,
            Material.ORANGE_TULIP,
            Material.WHITE_TULIP,
            Material.PINK_TULIP,
            Material.OXEYE_DAISY,
            Material.CORNFLOWER,
            Material.LILY_OF_THE_VALLEY,
            Material.WITHER_ROSE
    ));


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage("Teleporting");

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        AuraSkillsApi auraSkills = AuraSkillsApi.get();

        SkillsUser user = auraSkills.getUser(player.getUniqueId());

        double mana = user.getMana();


        if (mana > 5){
            user.setMana(mana - 5.0);
        }
        else {
            sender.sendMessage("Not enough §bMana!");
            return true;
        }
        // Get the player's current mana from the placeholder
        Location loc = player.getLocation();

        // Get the direction the player is facing
        Vector direction = loc.getDirection();

        // Calculate the destination location 8 blocks ahead
        Location destination = loc.clone().add(direction.clone().multiply(8));

        // Find the nearest safe location
        Location safeLocation = findSafeLocation(loc, destination);

        if (safeLocation == null) {
            sender.sendMessage("No safe location found!");
            return true;
        }

        // Teleport the player to the safe location
        player.teleport(safeLocation);
        player.setFallDistance(-100000);

        return true;
    }



    private Location findSafeLocation(Location start, Location end) {
        World world = start.getWorld();
        if (world == null) return null;

        Vector direction = end.toVector().subtract(start.toVector()).normalize();
        Location loc = start.clone();
        Location safeLocation = null;

        while (loc.distanceSquared(start) < end.distanceSquared(start)) {
            if (isSafeLocation(loc)) {
                safeLocation = loc.clone();
            }
            loc.add(direction);
        }

        return safeLocation;
    }


    private boolean isSafeLocation(Location location) {
        Block block = location.getBlock();
        Material material = block.getType();
        return ALLOWED_MATERIALS.contains(material);
    }
}
