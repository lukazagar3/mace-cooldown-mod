package com.example.combatmod.cooldown;

import com.example.combatmod.config.ConfigManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private static final Map<UUID, Integer> COMBAT_TIMERS = new HashMap<>();

    public static void enterCombat(ServerPlayerEntity player) {
        double seconds = ConfigManager.getConfig().combatCooldownSeconds;
        int ticks = (int) (seconds * 20);
        
        UUID uuid = player.getUuid();
        if (!COMBAT_TIMERS.containsKey(uuid)) {
            player.sendMessage(Text.literal("You have entered combat!").formatted(Formatting.RED), false);
        }
        COMBAT_TIMERS.put(uuid, ticks);
    }

    public static boolean isInCombat(ServerPlayerEntity player) {
        return COMBAT_TIMERS.containsKey(player.getUuid());
    }

    public static void removeCombat(ServerPlayerEntity player) {
        COMBAT_TIMERS.remove(player.getUuid());
    }

    public static void tick(MinecraftServer server) {
        Iterator<Map.Entry<UUID, Integer>> iterator = COMBAT_TIMERS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Integer> entry = iterator.next();
            UUID uuid = entry.getKey();
            int remainingTicks = entry.getValue() - 1;

            ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);

            if (remainingTicks <= 0) {
                iterator.remove();
                if (player != null) {
                    player.sendMessage(Text.literal("Out of Combat").formatted(Formatting.GREEN), true);
                    player.sendMessage(Text.literal("You are no longer in combat.").formatted(Formatting.GREEN), false);
                }
            } else {
                entry.setValue(remainingTicks);
                if (player != null) {
                    double seconds = remainingTicks / 20.0;
                    String formattedTime = String.format("%.1f", seconds);
                    player.sendMessage(Text.literal("In Combat: " + formattedTime + "s").formatted(Formatting.RED), true);
                }
            }
        }
    }

    public static void clear() {
        COMBAT_TIMERS.clear();
    }
}
