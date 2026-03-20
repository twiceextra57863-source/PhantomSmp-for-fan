package com.phantomsmp.powerbook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BookCommand implements CommandExecutor, TabCompleter {
    private final PowerBookPlugin plugin;

    public BookCommand(PowerBookPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("powerbook.admin") && !label.equalsIgnoreCase("trade")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /pb <give|trade|gui>");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 4) {
                sender.sendMessage(ChatColor.RED + "Usage: /pb give <player> <type> <level>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            PowerBookType type;
            try {
                type = PowerBookType.valueOf(args[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "Invalid book type.");
                return true;
            }
            int level;
            try {
                level = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid level.");
                return true;
            }

            ItemStack book = plugin.getBookManager().createBook(type, level);
            target.getInventory().addItem(book);
            sender.sendMessage(ChatColor.GREEN + "Given " + type.name() + " book to " + target.getName() + ".");
            return true;
        } else if (args[0].equalsIgnoreCase("gui")) {
            if (sender instanceof Player) {
                plugin.getBookGUI().openUpgradeGUI((Player) sender);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("trade")) {
            if (sender instanceof Player && args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && target != sender) {
                    plugin.getTradingSystem().startTrade((Player) sender, target);
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid player for trading.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /pb trade <player>");
            }
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("give");
            completions.add("gui");
            completions.add("trade");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            for (Player p : Bukkit.getOnlinePlayers()) completions.add(p.getName());
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            for (PowerBookType type : PowerBookType.values()) completions.add(type.name());
        } else if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
            completions.add("1");
            completions.add("2");
            completions.add("3");
        }
        return completions;
    }
}