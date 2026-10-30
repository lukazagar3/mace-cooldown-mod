package com.example.combatmod.mixin;

import com.example.combatmod.listener.MaceCooldownHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** When EnchantmentsAllowed is false, a mace can't be enchanted in the enchanting table. */
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    @Inject(method = "clickMenuButton", at = @At("HEAD"), cancellable = true)
    private void maceCooldown$blockMaceEnchanting(Player player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (MaceCooldownHandler.enchantmentsAllowed()) return;

        EnchantmentMenu menu = (EnchantmentMenu) (Object) this;
        if (menu.getSlot(0).getItem().is(Items.MACE)) {
            cir.setReturnValue(false);
        }
    }
}
