package com.phantomsmp.powergem;

import org.bukkit.plugin.java.JavaPlugin;

public class PowerGemPlugin extends JavaPlugin {

    private GemManager gemManager;
    private CooldownManager cooldownManager;
    private GemLevelingSystem levelingSystem;
    private TradingSystem tradingSystem;
    private GemGUI gemGUI;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        this.cooldownManager = new CooldownManager();
        this.levelingSystem = new GemLevelingSystem(this);
        this.gemManager = new GemManager(this);
        this.tradingSystem = new TradingSystem(this);
        this.gemGUI = new GemGUI(this);

        getServer().getPluginManager().registerEvents(new GemListener(this), this);
        getCommand("gem").setExecutor(new GemCommand(this));

        getLogger().info("PowerGem has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PowerGem has been disabled!");
    }

    public GemManager getGemManager() { return gemManager; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public GemLevelingSystem getLevelingSystem() { return levelingSystem; }
    public TradingSystem getTradingSystem() { return tradingSystem; }
    public GemGUI getGemGUI() { return gemGUI; }
}