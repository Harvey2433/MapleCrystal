/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.sound.SoundInstance
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.client.sound.SoundInstance;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class PlaySoundEvent
extends Event {
    private static final PlaySoundEvent INSTANCE = new PlaySoundEvent();
    public SoundInstance sound;

    public static PlaySoundEvent get(SoundInstance sound) {
        INSTANCE.setCancelled(false);
        PlaySoundEvent.INSTANCE.sound = sound;
        return INSTANCE;
    }
}

