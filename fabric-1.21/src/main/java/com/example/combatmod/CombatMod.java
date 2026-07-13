package com.example.combatmod;

import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.listener.ModListeners;
import net.fabricmc.api.ModInitializer;

public class CombatMod implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("[MaceCooldownMod] Initializing Fabric 1.21...");
        ConfigManager.load();
        ModListeners.register();
    }
}
