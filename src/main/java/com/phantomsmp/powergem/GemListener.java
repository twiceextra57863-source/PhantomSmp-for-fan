package com.phantomsmp.powergem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GemListener implements Listener {
    private final PowerGemPlugin plugin;

    public GemListener(PowerGemPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Gem gem = Gem.fromItemStack(item, plugin);
        
        if (gem != null) {
            event.setCancelled(true);
            GemAbility ability = plugin.getGemManager().getAbility(gem.getType());
            if (ability != null) {
                if (plugin.getCooldownManager().isOnCooldown(player, gem.getType())) {
                    int remaining = plugin.getCooldownManager().getRemainingCooldown(player, gem.getType());
                    String msg = plugin.getConfig().getString("messages.cooldown", "&cCooldown: {time}s");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("{time}", String.valueOf(remaining))));
                    return;
                }
                
                ability.onActivate(player, gem);
                int baseCooldown = plugin.getConfig().getInt("base-cooldown", 15);
                int finalCooldown = Math.max(5, baseCooldown - gem.getLevel());
                plugin.getCooldownManager().setCooldown(player, gem.getType(), finalCooldown);
                
                plugin.getLevelingSystem().addExp(player, item, 5);
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Gem gem = Gem.fromItemStack(attacker.getInventory().getItemInMainHand(), plugin);
            if (gem != null) {
                GemAbility ability = plugin.getGemManager().getAbility(gem.getType());
                if (ability != null) {
                    ability.onAttack(attacker, event.getEntity(), gem);
                    plugin.getLevelingSystem().addExp(attacker, attacker.getInventory().getItemInMainHand(), 10);
                }
            }
        }

        if (event.getEntity() instanceof Player) {
            Player defender = (Player) event.getEntity();
            Gem gem = Gem.fromItemStack(defender.getInventory().getItemInMainHand(), plugin);
            if (gem != null) {
                GemAbility ability = plugin.getGemManager().getAbility(gem.getType());
                if (ability != null) {
                    ability.onDefend(defender, event.getDamager(), gem);
                    plugin.getLevelingSystem().addExp(defender, defender.getInventory().getItemInMainHand(), 5);
                }
            }
        }
    }
}