package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

                    if (serverPlayer.getItemCooldownManager().isCoolingDown(mainHand)) {
                        return ActionResult.FAIL;
                    }

                    boolean isSmashAttack = serverPlayer.fallDistance > 1.5F;

                    if (isSmashAttack) {
                        double maceSec = ConfigManager.getConfig().maceCooldownSeconds;
                        int ticks = (int) (maceSec * 20);
                        
                        serverPlayer.getItemCooldownManager().set(mainHand, ticks);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}