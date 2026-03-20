package com.phantomsmp.powerbook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final Plugin plugin;
    private final NamespacedKey typeKey;
    private final NamespacedKey levelKey;

    public BookManager(Plugin plugin) {
        this.plugin = plugin;
        this.typeKey = new NamespacedKey(plugin, "book_type");
        this.levelKey = new NamespacedKey(plugin, "book_level");
    }

    public ItemStack createBook(PowerBookType type, int level) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + type.getDisplayName() + " Tome " + ChatColor.YELLOW + "[Lv." + level + "]");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + type.getDescription());
            lore.add(ChatColor.DARK_AQUA + "Cooldown: " + ChatColor.AQUA + type.getBaseCooldown() + "s");
            meta.setLore(lore);
            
            meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.name());
            meta.getPersistentDataContainer().set(levelKey, PersistentDataType.INTEGER, level);
            
            item.setItemMeta(meta);
        }
        return item;
    }

    public PowerBookType getBookType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String typeStr = item.getItemMeta().getPersistentDataContainer().get(typeKey, PersistentDataType.STRING);
        if (typeStr != null) {
            try {
                return PowerBookType.valueOf(typeStr);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    public int getBookLevel(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;
        Integer level = item.getItemMeta().getPersistentDataContainer().get(levelKey, PersistentDataType.INTEGER);
        return level != null ? level : 0;
    }
}