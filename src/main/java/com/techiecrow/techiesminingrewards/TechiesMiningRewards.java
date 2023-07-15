package com.techiecrow.techiesminingrewards;

import com.techiecrow.techiesminingrewards.listeners.BlockBreakListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TechiesMiningRewards extends JavaPlugin {

    @Override
    public void onEnable() {
        // bStats
        int pluginId = 18844;
        new Metrics(this, pluginId);

        this.RegisterConfig();
        getLogger().info("MiningRewards plugin has been enabled.");

        // Register the event listener
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        Commands commands = new Commands(this);

        Objects.requireNonNull(getCommand("tmr"))
                .setExecutor(commands);
        Objects.requireNonNull(getCommand("tmr"))
                .setTabCompleter(commands);
    }

    @Override
    public void onDisable() {
        getLogger().info("MiningRewards plugin has been disabled.");
    }

    private void RegisterConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}