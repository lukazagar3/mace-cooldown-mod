package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.setup.SetupWizardHandler;
import com.example.mcbridge.v26.Fabric26Player;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;

public class ModListeners {
    public static void register() {
        
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                ItemStack mainHand = serverPlayer.getItemInHand(hand);
                
                if (mainHand.is(Items.MACE)) {
                    if (!(entity instanceof Player)) {
                        return InteractionResult.PASS;
                    }
                    
                    Fabric26Player attacker = new Fabric26Player(serverPlayer);
                    Fabric26Player victim = entity instanceof ServerPlayer ? new Fabric26Player((ServerPlayer) entity) : null;
                    
                    boolean allowAttack = MaceCooldownHandler.handleAttack(attacker, victim, "mace");
                    if (!allowAttack) {
                        return InteractionResult.FAIL;
                    }
                }
            }
            return InteractionResult.PASS;
        });

        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("macemod-setup")
                .requires(source -> source.permissions().hasPermission(new net.minecraft.server.permissions.Permission.HasCommandLevel(net.minecraft.server.permissions.PermissionLevel.GAMEMASTERS)))
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayer();
                    Fabric26Player sender = player == null ? null : new Fabric26Player(player);
                    SetupWizardHandler.showInfo(sender);
                    return 1;
                })
                .then(Commands.literal("cooldown")
                    .then(Commands.argument("seconds", DoubleArgumentType.doubleArg(0.0))
                        .executes(context -> {
                            double sec = DoubleArgumentType.getDouble(context, "seconds");
                            ServerPlayer player = context.getSource().getPlayer();
                            Fabric26Player sender = player == null ? null : new Fabric26Player(player);
                            return SetupWizardHandler.setCooldown(sender, sec);
                        })
                    )
                )
            );
        });
    }
}
