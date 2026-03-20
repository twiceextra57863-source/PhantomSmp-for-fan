package com.phantomsmp.powergem;

import org.bukkit.Material;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Gem {
    private final UUID id;
    private final GemType type;
    private int level;
    private int exp;

    public Gem(GemType type, int level, int exp) {
        this(UUID.randomUUID(), type, level, exp);
    }

    public Gem(UUID id, GemType type, int level, int exp) {
        this.id = id;
        this.type = type;
        this.level = level;
        this.exp = exp;
    }

    public UUID getId() { return id; }
    public GemType getType() { return type; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public ItemStack toItemStack(PowerGemPlugin plugin) {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(type.getColor() + type.getDisplayName() + " [Lv." + level + "]");
            List<String> lore = new ArrayList<>();
            lore.add(org.bukkit.ChatColor.GRAY + type.getDescription());
            lore.add("");
            lore.add(org.bukkit.ChatColor.YELLOW + "EXP: " + exp + " / " + (level * 100));
            meta.setLore(lore);

            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gem_id"), PersistentDataType.STRING, id.toString());
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gem_type"), PersistentDataType.STRING, type.name());
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gem_level"), PersistentDataType.INTEGER, level);
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "gem_exp"), PersistentDataType.INTEGER, exp);

            item.setItemMeta(meta);
        }
        return item;
    }

    public static Gem fromItemStack(ItemStack item, PowerGemPlugin plugin) {
        if (item == null || !item.hasItemMeta()) return null;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey typeKey = new NamespacedKey(plugin, "gem_type");
        if (!meta.getPersistentDataContainer().has(typeKey, PersistentDataType.STRING)) return null;

        String idStr = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "gem_id"), PersistentDataType.STRING);
        String typeStr = meta.getPersistentDataContainer().get(typeKey, PersistentDataType.STRING);
        Integer level = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "gem_level"), PersistentDataType.INTEGER);
        Integer exp = meta.getPersistentDataContainer().get(new NamespacedKey(plugin, "gem_exp"), PersistentDataType.INTEGER);

        if (idStr == null || typeStr == null || level == null || exp == null) return null;

        return new Gem(UUID.fromString(idStr), GemType.valueOf(typeStr), level, exp);
    }
}