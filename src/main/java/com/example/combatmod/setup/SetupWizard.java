package com.example.combatmod.setup;

import com.example.combatmod.config.ConfigManager;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SetupWizard {
<<<<<<< HEAD
=======
    private static double tempCombatSec = 10.0;

>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
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
<<<<<<< HEAD
=======

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("combatlog-setup")
            .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) startWizard(player);
                return 1;
            })
            .then(CommandManager.literal("combat")
                .then(CommandManager.argument("seconds", DoubleArgumentType.doubleArg(0.1, 3600.0))
                    .executes(context -> {
                        double sec = DoubleArgumentType.getDouble(context, "seconds");
                        tempCombatSec = sec;
                        ModConfig config = ConfigManager.getConfig();
                        config.combatCooldownSeconds = sec;
                        config.isConfigured = true;
                        ConfigManager.save();
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        if (player != null) {
                            player.sendMessage(Text.literal("[CombatLog] Combat cooldown set to: " + sec + "s").formatted(Formatting.GREEN), false);
                        }
                        context.getSource().getServer().getPlayerManager().broadcast(
                            Text.literal("[CombatLog] Setup complete! Combat cooldown = " + sec + "s").formatted(Formatting.GREEN),
                            false
                        );
                        return 1;
                    })
                )
            )
            .then(CommandManager.literal("show")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null) startWizard(player);
                    return 1;
                })
            )
        );
    }

    public static void startWizard(ServerPlayerEntity player) {
        player.sendMessage(Text.literal("----------------------------------------").formatted(Formatting.GOLD), false);
        player.sendMessage(Text.literal("[Combat Log Mod] Setup Wizard").formatted(Formatting.YELLOW, Formatting.BOLD), false);
        player.sendMessage(Text.literal("Current combat cooldown: " + ConfigManager.getConfig().combatCooldownSeconds + "s").formatted(Formatting.WHITE), false);
        player.sendMessage(Text.literal("\nSelect combat cooldown duration:").formatted(Formatting.YELLOW), false);

        MutableText options = Text.empty();
        options.append(createClickableOption(" [5s] ", "/combatlog-setup combat 5.0", "Set to 5 seconds"));
        options.append(createClickableOption(" [10s] ", "/combatlog-setup combat 10.0", "Set to 10 seconds"));
        options.append(createClickableOption(" [15s] ", "/combatlog-setup combat 15.0", "Set to 15 seconds"));
        options.append(createClickableOption(" [30s] ", "/combatlog-setup combat 30.0", "Set to 30 seconds"));
        options.append(createClickableOption(" [60s] ", "/combatlog-setup combat 60.0", "Set to 60 seconds"));
        player.sendMessage(options, false);
        player.sendMessage(Text.literal("\nOr type /combatlog-setup combat <seconds>").formatted(Formatting.GRAY), false);
        player.sendMessage(Text.literal("----------------------------------------").formatted(Formatting.GOLD), false);
    }

    private static MutableText createClickableOption(String text, String command, String hoverText) {
        Style style = Style.EMPTY
            .withClickEvent(new ClickEvent.RunCommand(command))
            .withHoverEvent(new HoverEvent.ShowText(Text.literal(hoverText)));
        return Text.literal(text).setStyle(style).formatted(Formatting.AQUA);
    }
>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
}
