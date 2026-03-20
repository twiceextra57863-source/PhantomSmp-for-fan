package com.phantomsmp.powergem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GemLevelingSystem {
    private final PowerGemPlugin plugin;

    public GemLevelingSystem(PowerGemPlugin plugin) {
        this.plugin = plugin;
    }

    public int getRequiredExp(int level) {
        return level * 100;
    }

    public void addExp(Player player, ItemStack item, int amount) {
        Gem gem = Gem.fromItemStack(item, plugin);
        if (gem == null) return;

        int maxLevel = plugin.getConfig().getInt("max-gem-level", 10);
        if (gem.getLevel() >= maxLevel) return;

        gem.setExp(gem.getExp() + amount);
        while (gem.getExp() >= getRequiredExp(gem.getLevel()) && gem.getLevel() < maxLevel) {
            gem.setExp(gem.getExp() - getRequiredExp(gem.getLevel()));
            gem.setLevel(gem.getLevel() + 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                plugin.getConfig().getString("messages.level-up")
                .replace("{type}", gem.getType().getDisplayName())
                .replace("{level}", String.valueOf(gem.getLevel()))));
        }

        // Update item in player's inventory
        player.getInventory().setItemInMainHand(gem.toItemStack(plugin));
    }
}