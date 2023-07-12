package com.techiecrow.miningrewards.listeners;

import com.techiecrow.miningrewards.MiningRewards;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockBreakListener implements Listener {

    private final MiningRewards plugin;
    private final List<String> ores;
    private final Map<String, Integer> rewards;
    private final Random random;

    public BlockBreakListener(MiningRewards plugin) {
        this.plugin = plugin;
        this.ores = plugin.getPluginConfig().getStringList("ores");
        this.rewards = parseRewards(plugin.getPluginConfig().getMapList("rewards"));
        this.random = new Random();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (isOre(blockType)) {
            double chance = plugin.getPluginConfig().getDouble("diamond_chance");
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
            String reward = getRandomReward();
            if (reward != null) {
                String rewardMaterialName = reward.split(":")[0];
                int quantity = Integer.parseInt(reward.split(":")[1]);
                Material rewardMaterial = Material.valueOf(rewardMaterialName);
                location.getWorld().dropItemNaturally(location, new ItemStack(rewardMaterial, quantity));
                player.sendMessage("You received a reward: " + rewardMaterial.name() + " x" + quantity);
            }
        }
    }

    private String getRandomReward() {
        List<String> rewardList = new ArrayList<>(rewards.keySet());
        if (!rewardList.isEmpty()) {
            String reward = rewardList.get(random.nextInt(rewardList.size()));
            int quantity = rewards.get(reward);
            return reward + ":" + quantity;
        }
        return null;
    }

    private Map<String, Integer> parseRewards(List<Map<?, ?>> rewardList) {
        Map<String, Integer> rewards = new HashMap<>();
        for (Map<?, ?> reward : rewardList) {
            for (Map.Entry<?, ?> entry : reward.entrySet()) {
                String rewardName = entry.getKey().toString();
                int quantity = Integer.parseInt(entry.getValue().toString());
                rewards.put(rewardName, quantity);
            }
        }
        return rewards;
    }
}