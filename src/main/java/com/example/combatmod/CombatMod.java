package com.example.combatmod;

import com.example.combatmod.command.ConfigCommand;
import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.listener.ModListeners;
import com.example.combatmod.setup.SetupWizard;
import net.fabricmc.api.ModInitializer;

public class CombatMod implements ModInitializer {
    @Override
    public void onInitialize() {
<<<<<<< HEAD
        System.out.println("[MaceCooldownMod] Initializing...");
=======
        System.out.println("[CombatLogMod] Initializing...");
>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
        ConfigManager.load();
        SetupWizard.register();
        ConfigCommand.register();
        ModListeners.register();
    }
}
