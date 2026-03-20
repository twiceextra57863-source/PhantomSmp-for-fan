package com.phantomsmp.powergem;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<GemType, Long>> cooldowns = new HashMap<>();

    public void setCooldown(Player player, GemType type, int seconds) {
        cooldowns.putIfAbsent(player.getUniqueId(), new HashMap<>());
        cooldowns.get(player.getUniqueId()).put(type, System.currentTimeMillis() + (seconds * 1000L));
    }

    public boolean isOnCooldown(Player player, GemType type) {
        if (!cooldowns.containsKey(player.getUniqueId())) return false;
        Map<GemType, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (!playerCooldowns.containsKey(type)) return false;
        return playerCooldowns.get(type) > System.currentTimeMillis();
    }

    public int getRemainingCooldown(Player player, GemType type) {
        if (!isOnCooldown(player, type)) return 0;
        return (int) ((cooldowns.get(player.getUniqueId()).get(type) - System.currentTimeMillis()) / 1000);
    }
}