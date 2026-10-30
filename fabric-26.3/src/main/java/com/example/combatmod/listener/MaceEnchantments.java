package com.example.combatmod.listener;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/** Removes enchantments from maces (used when EnchantmentsAllowed is false). */
public final class MaceEnchantments {
    private MaceEnchantments() {}

    /** Strips enchantments from the stack if it is an enchanted mace. Returns true if it changed. */
    public static boolean strip(ItemStack stack) {
        if (stack.isEmpty() || !stack.is(Items.MACE) || stack.getEnchantments().isEmpty()) {
            return false;
        }
        EnchantmentHelper.setEnchantments(stack, ItemEnchantments.EMPTY);
        return true;
    }

    public static void stripFromInventory(ServerPlayer player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (strip(inventory.getItem(i))) {
                inventory.setChanged();
            }
        }
    }
}
