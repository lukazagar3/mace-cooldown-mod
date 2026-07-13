package com.example.combatmod.listener;

import com.example.combatmod.config.ConfigManager;
import com.example.combatmod.setup.SetupWizardHandler;
import com.example.mcbridge.neo121.Neo121Player;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "macecooldownmod")
public class ModListeners {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        
        dispatcher.register(Commands.literal("macemod-setup")
            .requires(source -> source.hasPermission(2))
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayer();
                Neo121Player sender = player == null ? null : new Neo121Player(player);
                SetupWizardHandler.showInfo(sender);
                return 1;
            })
            .then(Commands.literal("cooldown")
                .then(Commands.argument("seconds", DoubleArgumentType.doubleArg(0.0))
                    .executes(context -> {
                        double sec = DoubleArgumentType.getDouble(context, "seconds");
                        ServerPlayer player = context.getSource().getPlayer();
                        Neo121Player sender = player == null ? null : new Neo121Player(player);
                        return SetupWizardHandler.setCooldown(sender, sec);
                    })
                )
            )
        );
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack mainHand = serverPlayer.getMainHandItem();
            
            if (mainHand.is(Items.MACE)) {
                Neo121Player attacker = new Neo121Player(serverPlayer);
                Neo121Player victim = event.getTarget() instanceof ServerPlayer ? new Neo121Player((ServerPlayer) event.getTarget()) : null;
                
                boolean allowAttack = MaceCooldownHandler.handleAttack(attacker, victim, "mace");
                if (!allowAttack) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
