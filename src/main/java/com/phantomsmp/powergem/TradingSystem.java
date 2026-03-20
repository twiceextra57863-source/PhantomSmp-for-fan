package com.phantomsmp.powergem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TradingSystem {
    private final PowerGemPlugin plugin;
    private final Map<UUID, UUID> tradeRequests = new HashMap<>();

    public TradingSystem(PowerGemPlugin plugin) {
        this.plugin = plugin;
    }

    public void requestTrade(Player sender, Player target) {
        tradeRequests.put(target.getUniqueId(), sender.getUniqueId());
        target.sendMessage(ChatColor.YELLOW + sender.getName() + " has requested to trade gems with you. Type /gem trade accept to accept.");
        sender.sendMessage(ChatColor.GREEN + "Trade request sent to " + target.getName());
    }

    public void acceptTrade(Player target) {
        UUID senderId = tradeRequests.get(target.getUniqueId());
        if (senderId == null) {
            target.sendMessage(ChatColor.RED + "You have no pending trade requests.");
            return;
        }
        
        Player sender = plugin.getServer().getPlayer(senderId);
        if (sender == null || !sender.isOnline()) {
            target.sendMessage(ChatColor.RED + "The player who requested the trade is no longer online.");
            tradeRequests.remove(target.getUniqueId());
            return;
        }

        tradeRequests.remove(target.getUniqueId());
        plugin.getGemGUI().openTradeMenu(sender, target);
    }
}