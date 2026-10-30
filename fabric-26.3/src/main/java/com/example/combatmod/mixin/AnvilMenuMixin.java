package com.example.combatmod.mixin;

import com.example.combatmod.listener.MaceCooldownHandler;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** When EnchantmentsAllowed is false, an anvil can't produce an enchanted mace (books are ignored). */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
    @Shadow @Final private DataSlot cost;

    @Inject(method = "createResult", at = @At("TAIL"))
    private void maceCooldown$blockMaceEnchanting(CallbackInfo ci) {
        if (MaceCooldownHandler.enchantmentsAllowed()) return;

        AnvilMenu menu = (AnvilMenu) (Object) this;
        ItemStack result = menu.getSlot(2).getItem();
        if (!result.is(Items.MACE) || result.getEnchantments().isEmpty()) return;

        menu.getSlot(2).set(ItemStack.EMPTY);
        this.cost.set(0);
        menu.broadcastChanges();
    }
}
