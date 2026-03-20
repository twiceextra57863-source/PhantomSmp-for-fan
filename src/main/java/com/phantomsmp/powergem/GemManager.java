package com.phantomsmp.powergem;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class GemManager {
    private final PowerGemPlugin plugin;
    private final Map<GemType, GemAbility> abilities = new HashMap<>();

    public GemManager(PowerGemPlugin plugin) {
        this.plugin = plugin;
        registerAbilities();
    }

    private void registerAbilities() {
        abilities.put(GemType.FIRE, new GemAbility() {
            @Override
            public void onActivate(Player player, Gem gem) {
                player.launchProjectile(org.bukkit.entity.Fireball.class);
                ParticleEffects.playFireEffect(player.getLocation());
            }
            @Override
            public void onAttack(Player attacker, Entity victim, Gem gem) {
                victim.setFireTicks(60 + (gem.getLevel() * 20));
            }
            @Override
            public void onDefend(Player defender, Entity attacker, Gem gem) {}
        });

        abilities.put(GemType.WIND, new GemAbility() {
            @Override
            public void onActivate(Player player, Gem gem) {
                Vector dir = player.getLocation().getDirection().multiply(1.5 + (gem.getLevel() * 0.1));
                player.setVelocity(dir);
                ParticleEffects.playWindEffect(player.getLocation());
            }
            @Override
            public void onAttack(Player attacker, Entity victim, Gem gem) {}
            @Override
            public void onDefend(Player defender, Entity attacker, Gem gem) {
                if (Math.random() < 0.1 * gem.getLevel()) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
                }
            }
        });
        
        abilities.put(GemType.WATER, new GemAbility() {
            @Override
            public void onActivate(Player player, Gem gem) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, gem.getLevel() / 3));
                ParticleEffects.playWaterEffect(player.getLocation());
            }
            @Override
            public void onAttack(Player attacker, Entity victim, Gem gem) {}
            @Override
            public void onDefend(Player defender, Entity attacker, Gem gem) {}
        });

        abilities.put(GemType.EARTH, new GemAbility() {
            @Override
            public void onActivate(Player player, Gem gem) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, gem.getLevel() / 4));
            }
            @Override
            public void onAttack(Player attacker, Entity victim, Gem gem) {}
            @Override
            public void onDefend(Player defender, Entity attacker, Gem gem) {}
        });

        abilities.put(GemType.VOID, new GemAbility() {
            @Override
            public void onActivate(Player player, Gem gem) {
                player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(5)));
            }
            @Override
            public void onAttack(Player attacker, Entity victim, Gem gem) {
                if (victim instanceof LivingEntity) {
                    ((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
                }
            }
            @Override
            public void onDefend(Player defender, Entity attacker, Gem gem) {}
        });
    }

    public GemAbility getAbility(GemType type) {
        return abilities.get(type);
    }
}