package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
<<<<<<< HEAD
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
=======
import com.example.combatmod.cooldown.CooldownManager;
import com.example.combatmod.setup.SetupWizard;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModListeners {
    public static void register() {

        // Enter combat on PvP hit
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, amount, damageResult, state) -> {
            if (entity instanceof ServerPlayerEntity victim) {
                if (source.getAttacker() instanceof ServerPlayerEntity attacker) {
                    CooldownManager.enterCombat(victim);
                    CooldownManager.enterCombat(attacker);
                }
            }
        });

        // Remove from combat on death
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof ServerPlayerEntity player) {
                CooldownManager.removeCombat(player);
            }
        });

        // Tick combat timers
        ServerTickEvents.START_SERVER_TICK.register(CooldownManager::tick);

        // Setup wizard on OP player join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (!ConfigManager.getConfig().isConfigured &&
                CommandManager.ADMINS_CHECK.allows(player.getCommandSource().getPermissions())) {
                SetupWizard.startWizard(player);
            }
        });

        // Combat Log: kill on disconnect
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (CooldownManager.isInCombat(player)) {
                CooldownManager.removeCombat(player);

                if (player.getCommandSource().getWorld() != null) {
                    player.damage(
                        player.getCommandSource().getWorld(),
                        player.getDamageSources().genericKill(),
                        Float.MAX_VALUE
                    );
                }

                Text msg = Text.literal(player.getName().getString() + " died by combat logging!")
                    .formatted(Formatting.RED, Formatting.BOLD);
                server.getPlayerManager().broadcast(msg, false);
            }
>>>>>>> 0d0c52994ba6ab6938837d043dc058e58362fa5d
        });
    }
}