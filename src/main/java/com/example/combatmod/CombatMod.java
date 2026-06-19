package com.example.combatmod;

import com.example.combatmod.command.ConfigCommand;
import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.listener.ModListeners;
import com.example.combatmod.setup.SetupWizard;
import net.fabricmc.api.ModInitializer;

public class CombatMod implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("[MaceCooldownMod] Initializing...");
        ConfigManager.load();
        SetupWizard.register();
        ConfigCommand.register();
        ModListeners.register();
    }
}
