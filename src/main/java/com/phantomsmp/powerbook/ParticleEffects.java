package com.phantomsmp.powerbook;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleEffects {
    
    public void playFireNova(Player player, int level) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        double radius = 3.0 + level;
        for (int i = 0; i < 360; i += 10) {
            double x = Math.cos(Math.toRadians(i)) * radius;
            double z = Math.sin(Math.toRadians(i)) * radius;
            world.spawnParticle(Particle.FLAME, loc.clone().add(x, 0.5, z), 2, 0, 0, 0, 0.05);
        }
        
        for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof LivingEntity && e != player) {
                e.setFireTicks(100 + (level * 20));
                ((LivingEntity) e).damage(4.0 + (level * 2.0), player);
            }
        }
    }

    public void playIceNova(Player player, int level) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        double radius = 3.0 + level;
        for (int i = 0; i < 360; i += 10) {
            double x = Math.cos(Math.toRadians(i)) * radius;
            double z = Math.sin(Math.toRadians(i)) * radius;
            world.spawnParticle(Particle.SNOWFLAKE, loc.clone().add(x, 0.5, z), 5, 0.2, 0.2, 0.2, 0.01);
        }
        
        for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
            if (e instanceof LivingEntity && e != player) {
                ((LivingEntity) e).damage(2.0 + level, player);
                e.setVelocity(new Vector(0, 0, 0));
            }
        }
    }

    public void playHealing(Player player, int level) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        world.spawnParticle(Particle.HEART, loc.clone().add(0, 2, 0), 10 + (level * 5), 0.5, 0.5, 0.5, 0);
        double health = player.getHealth() + (4.0 * level);
        player.setHealth(Math.min(health, player.getMaxHealth()));
    }

    public void playLightning(Player player, int level) {
        World world = player.getWorld();
        Location target = player.getTargetBlock(null, 20 + (level * 5)).getLocation();
        world.strikeLightning(target);
        world.spawnParticle(Particle.CRIT_MAGIC, target, 50, 1, 1, 1, 0.1);
    }
    
    public void castSpell(Player player, PowerBookType type, int level) {
        switch (type) {
            case FIRE: playFireNova(player, level); break;
            case ICE: playIceNova(player, level); break;
            case HEALING: playHealing(player, level); break;
            case LIGHTNING: playLightning(player, level); break;
        }
    }
}