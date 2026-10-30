package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.config.ModConfig;
import com.example.mcbridge.api.IPlayer;

public class MaceCooldownHandler {
    /**
     * Returns true if hitting this kind of target should go through the mace cooldown,
     * based on the PlayerCooldown / MonsterCooldown / EntityCooldown config flags.
     */
    public static boolean appliesTo(boolean targetIsPlayer, boolean targetIsMonster) {
        ModConfig config = ConfigManager.getConfig();
        if (config.entityCooldown) return true;
        if (targetIsPlayer && config.playerCooldown) return true;
        return targetIsMonster && config.monsterCooldown;
    }

    public static boolean enchantmentsAllowed() {
        return ConfigManager.getConfig().enchantmentsAllowed;
    }

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
