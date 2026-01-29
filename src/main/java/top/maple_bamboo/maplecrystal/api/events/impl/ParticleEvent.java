/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.particle.Particle
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.client.particle.Particle;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class ParticleEvent
extends Event {
    private static final ParticleEvent instance = new ParticleEvent();
    public Particle particle;

    public static ParticleEvent get(Particle particle) {
        ParticleEvent.instance.particle = particle;
        instance.setCancelled(false);
        return instance;
    }
}

