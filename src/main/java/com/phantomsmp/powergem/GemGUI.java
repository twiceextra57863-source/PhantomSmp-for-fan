package com.phantomsmp.powergem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GemGUI {
    private final PowerGemPlugin plugin;

    public GemGUI(PowerGemPlugin plugin) {
        this.plugin = plugin;
    }

    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Power Gems");
        player.openInventory(inv);
    }

    public void openTradeMenu(Player p1, Player p2) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Gem Trade: " + p1.getName() + " & " + p2.getName());
        p1.openInventory(inv);
        p2.openInventory(inv);
        p1.sendMessage(ChatColor.YELLOW + "Trade menu opened. Please be careful with your items.");
        p2.sendMessage(ChatColor.YELLOW + "Trade menu opened. Please be careful with your items.");
    }
}