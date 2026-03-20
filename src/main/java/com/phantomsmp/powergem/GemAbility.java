package com.phantomsmp.powergem;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface GemAbility {
    void onActivate(Player player, Gem gem);
    void onAttack(Player attacker, Entity victim, Gem gem);
    void onDefend(Player defender, Entity attacker, Gem gem);
}