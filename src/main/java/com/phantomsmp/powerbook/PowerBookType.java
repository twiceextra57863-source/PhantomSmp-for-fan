package com.phantomsmp.powerbook;

public enum PowerBookType {
    FIRE("Fire Nova", "Launch a wave of fire.", 10),
    ICE("Frost Nova", "Freeze nearby enemies.", 15),
    HEALING("Holy Radiance", "Heal yourself and allies.", 20),
    LIGHTNING("Thunder Strike", "Strike enemies with lightning.", 12);

    private final String displayName;
    private final String description;
    private final int baseCooldown;

    PowerBookType(String displayName, String description, int baseCooldown) {
        this.displayName = displayName;
        this.description = description;
        this.baseCooldown = baseCooldown;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public int getBaseCooldown() { return baseCooldown; }
}