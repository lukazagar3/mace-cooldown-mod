package com.example.combatmod.setup;

import com.example.combatmod.config.ConfigManager;
import com.example.mcbridge.api.ChatColor;
import com.example.mcbridge.api.IPlayer;

public class SetupWizardHandler {
    public static void showInfo(IPlayer sender) {
        if (sender != null) {
            sender.sendSystemMessage("=== Mace Cooldown Mod Setup ===", false, ChatColor.GOLD, true);
            sender.sendSystemMessage("  /macemod-setup cooldown <seconds>  - Set Mace attack cooldown", false, ChatColor.YELLOW, false);
            sender.sendSystemMessage("  Current cooldown: " + ConfigManager.getConfig().maceCooldownSeconds + "s", false, ChatColor.AQUA, false);
        }
    }

    public static int setCooldown(IPlayer sender, double seconds) {
        ConfigManager.getConfig().maceCooldownSeconds = seconds;
        ConfigManager.save();
        if (sender != null) {
            sender.sendSystemMessage("[MaceMod] Mace cooldown set to " + seconds + "s!", false, ChatColor.GREEN, false);
        }
        return 1;
    }
}
