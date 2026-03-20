package com.phantomsmp.powerbook;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BookListener implements Listener {
    private final PowerBookPlugin plugin;
    private final BookManager bookManager;
    private final CooldownManager cooldownManager;
    private final ParticleEffects particleEffects;

    public BookListener(PowerBookPlugin plugin) {
        this.plugin = plugin;
        this.bookManager = plugin.getBookManager();
        this.cooldownManager = plugin.getCooldownManager();
        this.particleEffects = plugin.getParticleEffects();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        PowerBookType type = bookManager.getBookType(item);
        if (type == null) return;
        
        event.setCancelled(true);

        int level = bookManager.getBookLevel(item);
        
        if (cooldownManager.isOnCooldown(player.getUniqueId(), type)) {
            int remaining = cooldownManager.getRemainingCooldown(player.getUniqueId(), type);
            player.sendMessage(ChatColor.RED + "You must wait " + remaining + " seconds before using this book again!");
            return;
        }

        int cooldown = Math.max(type.getBaseCooldown() - (level - 1), 2);
        cooldownManager.setCooldown(player.getUniqueId(), type, cooldown);

        particleEffects.castSpell(player, type, level);
        player.sendMessage(ChatColor.GREEN + "You cast " + type.getDisplayName() + "!");
    }
}