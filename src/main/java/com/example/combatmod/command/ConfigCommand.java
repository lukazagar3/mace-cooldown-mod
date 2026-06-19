package com.example.combatmod.command;

import com.example.combatmod.config.ConfigManager;
<<<<<<< HEAD
=======
import com.example.combatmod.config.ModConfig;
>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
<<<<<<< HEAD
            dispatcher.register(CommandManager.literal("macemod")
                .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))
                .then(CommandManager.literal("config")
                    .then(CommandManager.literal("maceCooldownSeconds")
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg(0.0))
                            .executes(context -> {
                                double val = DoubleArgumentType.getDouble(context, "value");
                                ConfigManager.getConfig().maceCooldownSeconds = val;
                                ConfigManager.save();
                                context.getSource().sendMessage(Text.literal("Mace cooldown set to " + val + "s").formatted(Formatting.GREEN));
=======
            dispatcher.register(CommandManager.literal("combatlog")
                .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))
                .then(CommandManager.literal("config")
                    .then(CommandManager.literal("combatCooldownSeconds")
                        .then(CommandManager.argument("value", DoubleArgumentType.doubleArg(0.0))
                            .executes(context -> {
                                double val = DoubleArgumentType.getDouble(context, "value");
                                ModConfig config = ConfigManager.getConfig();
                                config.combatCooldownSeconds = val;
                                ConfigManager.save();
                                context.getSource().sendMessage(Text.literal("Combat cooldown set to " + val + "s").formatted(Formatting.GREEN));
>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
                                return 1;
                            })
                        )
                    )
                )
            );
        });
    }
}
