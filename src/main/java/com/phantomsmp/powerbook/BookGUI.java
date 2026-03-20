package com.phantomsmp.powerbook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BookGUI implements Listener {
    private final PowerBookPlugin plugin;
    private final BookManager bookManager;
    private final BookLevelingSystem levelingSystem;

    public BookGUI(PowerBookPlugin plugin) {
        this.plugin = plugin;
        this.bookManager = plugin.getBookManager();
        this.levelingSystem = plugin.getLevelingSystem();
    }

    public void openUpgradeGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Power Book Upgrade");
        
        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta meta = info.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + "Place your Power Book here!");
            info.setItemMeta(meta);
        }
        
        inv.setItem(13, info);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Power Book Upgrade")) return;
        
        event.setCancelled(true);
        
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        
        if (clickedItem != null && bookManager.getBookType(clickedItem) != null) {
            if (levelingSystem.canUpgrade(clickedItem)) {
                ItemStack upgraded = levelingSystem.upgradeBook(clickedItem);
                event.setCurrentItem(upgraded);
                player.sendMessage(ChatColor.GREEN + "Your book was successfully upgraded!");
            } else {
                player.sendMessage(ChatColor.RED + "This book is already at the max level!");
            }
        }
    }
}