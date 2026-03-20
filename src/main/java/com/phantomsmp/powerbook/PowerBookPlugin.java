package com.phantomsmp.powerbook;

import org.bukkit.plugin.java.JavaPlugin;

public class PowerBookPlugin extends JavaPlugin {

    private BookManager bookManager;
    private CooldownManager cooldownManager;
    private ParticleEffects particleEffects;
    private BookLevelingSystem levelingSystem;
    private BookGUI bookGUI;
    private TradingSystem tradingSystem;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        int maxLevel = getConfig().getInt("max-book-level", 5);

        bookManager = new BookManager(this);
        cooldownManager = new CooldownManager();
        particleEffects = new ParticleEffects();
        levelingSystem = new BookLevelingSystem(bookManager, maxLevel);
        bookGUI = new BookGUI(this);
        tradingSystem = new TradingSystem(this);

        getServer().getPluginManager().registerEvents(new BookListener(this), this);
        getServer().getPluginManager().registerEvents(bookGUI, this);
        getServer().getPluginManager().registerEvents(tradingSystem, this);

        BookCommand command = new BookCommand(this);
        getCommand("powerbook").setExecutor(command);
        getCommand("powerbook").setTabCompleter(command);

        getLogger().info("PowerBook has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PowerBook has been disabled!");
    }

    public BookManager getBookManager() { return bookManager; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public ParticleEffects getParticleEffects() { return particleEffects; }
    public BookLevelingSystem getLevelingSystem() { return levelingSystem; }
    public BookGUI getBookGUI() { return bookGUI; }
    public TradingSystem getTradingSystem() { return tradingSystem; }
}