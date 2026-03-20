package com.phantomsmp.powerbook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<PowerBookType, Long>> cooldowns = new HashMap<>();

    public void setCooldown(UUID player, PowerBookType type, int seconds) {
        cooldowns.putIfAbsent(player, new HashMap<>());
        cooldowns.get(player).put(type, System.currentTimeMillis() + (seconds * 1000L));
    }

    public int getRemainingCooldown(UUID player, PowerBookType type) {
        if (!cooldowns.containsKey(player) || !cooldowns.get(player).containsKey(type)) {
            return 0;
        }
        long expiry = cooldowns.get(player).get(type);
        long remaining = expiry - System.currentTimeMillis();
        return remaining > 0 ? (int) Math.ceil(remaining / 1000.0) : 0;
    }

    public boolean isOnCooldown(UUID player, PowerBookType type) {
        return getRemainingCooldown(player, type) > 0;
    }
}