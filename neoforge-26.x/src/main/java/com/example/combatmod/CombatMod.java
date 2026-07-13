package com.example.combatmod;

import com.example.combatmod.config.ConfigManager;
import net.neoforged.fml.common.Mod;

@Mod("macecooldownmod")
public class CombatMod {
    public CombatMod() {
        System.out.println("[MaceCooldownMod] Initializing NeoForge 26.x...");
        ConfigManager.load();
    }
}
