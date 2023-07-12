package com.techiecrow.miningrewards;

import com.techiecrow.miningrewards.listeners.BlockBreakListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MiningRewards extends JavaPlugin {

    private static MiningRewards instance;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // bStats
        int pluginId = 18844;
        Metrics metrics = new Metrics(this, pluginId);

        instance = this;

        loadConfig();
        getLogger().info("MiningRewards plugin has been enabled.");

        // Register the event listener
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MiningRewards plugin has been disabled.");
    }

    public static MiningRewards getInstance() {
        return instance;
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }

    private void loadConfig() {
        config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();
    }

//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (command.getName().equalsIgnoreCase("miningrewardsreload")) {
//            reloadConfig();
//            loadConfig();
//            sender.sendMessage("MiningRewards configuration reloaded.");
//            return true;
//        }
//        return false;
//    }
}