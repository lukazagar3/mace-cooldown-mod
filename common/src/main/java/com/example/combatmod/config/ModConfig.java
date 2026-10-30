package com.example.combatmod.config;

import com.google.gson.annotations.SerializedName;

public class ModConfig {
    /** Duration of the mace cooldown, in seconds. */
    public double maceCooldownSeconds = 5.0;

    /** Hitting a player with a mace triggers the cooldown. */
    @SerializedName("PlayerCooldown")
    public boolean playerCooldown = true;

    /** Hitting any monster (zombie, enderman, creeper, ...) with a mace triggers the cooldown. */
    @SerializedName("MonsterCooldown")
    public boolean monsterCooldown = false;

    /** Hitting any entity at all (cows, zombies, players, ...) with a mace triggers the cooldown. */
    @SerializedName("EntityCooldown")
    public boolean entityCooldown = false;

    /** Maces may be enchanted. When false, enchanting is blocked and existing mace enchantments are removed. */
    @SerializedName("EnchantmentsAllowed")
    public boolean enchantmentsAllowed = true;
}
