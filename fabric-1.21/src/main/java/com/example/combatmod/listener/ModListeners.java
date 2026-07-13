package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.setup.SetupWizardHandler;
import com.example.mcbridge.v121.Fabric121Player;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class ModListeners {
    public static void register() {
        
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
                ItemStack mainHand = serverPlayer.getStackInHand(hand);
                
                if (mainHand.isOf(Items.MACE)) {
                    if (!(entity instanceof PlayerEntity)) {
                        return ActionResult.PASS;
                    }
                    
                    Fabric121Player attacker = new Fabric121Player(serverPlayer);
                    Fabric121Player victim = entity instanceof ServerPlayerEntity ? new Fabric121Player((ServerPlayerEntity) entity) : null;
                    
                    boolean allowAttack = MaceCooldownHandler.handleAttack(attacker, victim, "mace");
                    if (!allowAttack) {
                        return ActionResult.FAIL;
                    }
                }
            }
            return ActionResult.PASS;
        });

        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("macemod-setup")
                .requires(CommandManager.requirePermissionLevel(CommandManager.ADMINS_CHECK))
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    Fabric121Player sender = player == null ? null : new Fabric121Player(player);
                    SetupWizardHandler.showInfo(sender);
                    return 1;
                })
                .then(CommandManager.literal("cooldown")
                    .then(CommandManager.argument("seconds", DoubleArgumentType.doubleArg(0.0))
                        .executes(context -> {
                            double sec = DoubleArgumentType.getDouble(context, "seconds");
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            Fabric121Player sender = player == null ? null : new Fabric121Player(player);
                            return SetupWizardHandler.setCooldown(sender, sec);
                        })
                    )
                )
            );
        });
    }
}
