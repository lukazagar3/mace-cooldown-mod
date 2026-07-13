package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import com.example.mcbridge.api.IPlayer;

public class MaceCooldownHandler {
    public static boolean handleAttack(IPlayer attacker, IPlayer victim, String itemType) {
        if (!"mace".equalsIgnoreCase(itemType)) {
            return true; // Pass (allow attack)
        }
        
        if (attacker.isItemOnCooldown("mace")) {
            return false; // Fail (cancel attack)
        }
        
        if (attacker.getFallDistance() > 1.5F) {
            double maceSec = ConfigManager.getConfig().maceCooldownSeconds;
            attacker.setItemCooldown("mace", maceSec);
        }
        
        return true; // Pass (allow attack)
    }
}
