package com.example.combatmod.command;

import com.example.combatmod.config.ConfigManager;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
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
                                return 1;
                            })
                        )
                    )
                )
            );
        });
    }
}
