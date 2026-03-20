package com.phantomsmp.powergem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GemCommand implements CommandExecutor {
    private final PowerGemPlugin plugin;

    public GemCommand(PowerGemPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /gem <give|menu|trade>");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("powergem.admin")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Usage: /gem give <player> <type> [level]");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            try {
                GemType type = GemType.valueOf(args[2].toUpperCase());
                int level = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                Gem gem = new Gem(type, level, 0);
                ItemStack item = gem.toItemStack(plugin);
                target.getInventory().addItem(item);
                
                String msg = plugin.getConfig().getString("messages.gem-received", "&aYou received a {type} gem!");
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("{type}", type.getDisplayName()).replace("{level}", String.valueOf(level))));
                sender.sendMessage(ChatColor.GREEN + "Gave a " + type.name() + " gem to " + target.getName());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "Invalid gem type.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("menu")) {
            if (sender instanceof Player) {
                plugin.getGemGUI().openMainMenu((Player) sender);
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("trade")) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            
            if (args.length == 2 && args[1].equalsIgnoreCase("accept")) {
                plugin.getTradingSystem().acceptTrade(player);
                return true;
            }

            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null || target == player) {
                    player.sendMessage(ChatColor.RED + "Invalid player.");
                    return true;
                }
                plugin.getTradingSystem().requestTrade(player, target);
                return true;
            }
            
            player.sendMessage(ChatColor.RED + "Usage: /gem trade <player> OR /gem trade accept");
            return true;
        }

        return false;
    }
}