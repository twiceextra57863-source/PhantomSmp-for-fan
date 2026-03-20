package com.phantomsmp.powerbook;

import org.bukkit.inventory.ItemStack;

public class BookLevelingSystem {
    private final BookManager bookManager;
    private final int maxLevel;

    public BookLevelingSystem(BookManager bookManager, int maxLevel) {
        this.bookManager = bookManager;
        this.maxLevel = maxLevel;
    }

    public ItemStack upgradeBook(ItemStack book) {
        PowerBookType type = bookManager.getBookType(book);
        if (type == null) return book;

        int currentLevel = bookManager.getBookLevel(book);
        if (currentLevel >= maxLevel) return book;

        return bookManager.createBook(type, currentLevel + 1);
    }
    
    public boolean canUpgrade(ItemStack book) {
        PowerBookType type = bookManager.getBookType(book);
        if (type == null) return false;
        return bookManager.getBookLevel(book) < maxLevel;
    }
}