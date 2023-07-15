package com.techiecrow.techiesminingrewards.listeners;

import com.techiecrow.techiesminingrewards.TechiesMiningRewards;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final TechiesMiningRewards plugin;
    private final List<String> ores;
    private final List<String> rewards;
    private final Random random;

    public BlockBreakListener(TechiesMiningRewards plugin) {
        this.plugin = plugin;
        this.ores = plugin.getConfig().getStringList("ores");
        this.rewards = plugin.getConfig().getStringList("rewards");
        this.random = new Random();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (isOre(blockType)) {
            double chance = plugin.getConfig().getDouble("diamond_chance");
            Player player = event.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE && Math.random() < chance) {
                dropRandomReward(event.getBlock().getLocation(), player);
            }
        }
    }

    private boolean isOre(Material material) {
        String materialName = material.name();
        return ores.contains(materialName);
    }

    private void dropRandomReward(Location location, Player player) {
        if (!rewards.isEmpty()) {
            String rewardString = getRandomReward();
            if (rewardString != null) {
                String[] rewardParts = rewardString.split(":");
                String rewardMaterialName = rewardParts[0];
                int quantity = Integer.parseInt(rewardParts[1]);
                Material rewardMaterial = Material.valueOf(rewardMaterialName);
                World world = location.getWorld();
                if (world != null) {
                    world.dropItemNaturally(location, new ItemStack(rewardMaterial, quantity));
                }
                player.sendMessage("You received a reward: " + rewardMaterial.name() + " x" + quantity);
                location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 10, 0.5, 0.5, 0.5);
            }
        }
    }

    private String getRandomReward() {
        if (!rewards.isEmpty()) {
            int index = random.nextInt(rewards.size());
            return rewards.get(index);
        }
        return null;
    }
}