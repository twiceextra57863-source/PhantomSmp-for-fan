package com.phantomsmp.powergem;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleEffects {
    public static void playFireEffect(Location loc) {
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 50, 0.5, 1, 0.5, 0.05);
    }

    public static void playWaterEffect(Location loc) {
        loc.getWorld().spawnParticle(Particle.WATER_SPLASH, loc, 50, 0.5, 1, 0.5, 0.1);
    }

    public static void playWindEffect(Location loc) {
        loc.getWorld().spawnParticle(Particle.CLOUD, loc, 30, 0.5, 0.5, 0.5, 0.1);
    }
}