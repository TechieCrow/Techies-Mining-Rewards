package com.techiecrow.techiesminingrewards;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class Commands implements CommandExecutor, TabCompleter {

    private final TechiesMiningRewards plugin;

    public Commands(TechiesMiningRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("tmr")) {
            // Handle reload command
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {

                plugin.reloadConfig();

                sender.sendMessage("[TMR] Reloaded config!");
                return true;

            } else {
                sender.sendMessage("Incorrect usage!");
                return false;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        if (alias.equalsIgnoreCase("tmr")) {
            List<String> completions = new ArrayList<>();
            completions.add("reload");
            return completions;
        }

        return null;
    }
}