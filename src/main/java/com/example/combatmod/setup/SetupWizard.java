package com.example.combatmod.setup;

import com.example.combatmod.config.ConfigManager;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SetupWizard {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("macemod-setup")
                .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("=== Mace Cooldown Mod Setup ===").formatted(Formatting.GOLD, Formatting.BOLD), false);
                    context.getSource().sendFeedback(() -> Text.literal("  /macemod-setup cooldown <seconds>  - Set Mace attack cooldown").formatted(Formatting.YELLOW), false);
                    context.getSource().sendFeedback(() -> Text.literal("  Current cooldown: " + ConfigManager.getConfig().maceCooldownSeconds + "s").formatted(Formatting.AQUA), false);
                    return 1;
                })
                .then(CommandManager.literal("cooldown")
                    .then(CommandManager.argument("seconds", DoubleArgumentType.doubleArg(0.0))
                        .executes(context -> {
                            double sec = DoubleArgumentType.getDouble(context, "seconds");
                            ConfigManager.getConfig().maceCooldownSeconds = sec;
                            ConfigManager.save();
                            context.getSource().sendFeedback(() -> Text.literal("[MaceMod] Mace cooldown set to " + sec + "s!").formatted(Formatting.GREEN), true);
                            return 1;
                        })
                    )
                )
            );
        });
    }
}
