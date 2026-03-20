package com.phantomsmp.powergem;

import org.bukkit.ChatColor;

public enum GemType {
    FIRE("Fire Gem", ChatColor.RED, "Ignite your enemies and cast fireballs."),
    WATER("Water Gem", ChatColor.AQUA, "Heal over time and push enemies back."),
    EARTH("Earth Gem", ChatColor.GREEN, "Gain immense defense and knockback resistance."),
    WIND("Wind Gem", ChatColor.WHITE, "Dash through the air with incredible speed."),
    VOID("Void Gem", ChatColor.DARK_PURPLE, "Teleport and blind your foes.");

    private final String displayName;
    private final ChatColor color;
    private final String description;

    GemType(String displayName, ChatColor color, String description) {
        this.displayName = displayName;
        this.color = color;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public ChatColor getColor() { return color; }
    public String getDescription() { return description; }
}