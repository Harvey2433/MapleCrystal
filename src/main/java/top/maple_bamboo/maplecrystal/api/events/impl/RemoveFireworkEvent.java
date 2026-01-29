/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.projectile.FireworkRocketEntity
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.entity.projectile.FireworkRocketEntity;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class RemoveFireworkEvent
extends Event {
    public static final RemoveFireworkEvent instance = new RemoveFireworkEvent();
    private FireworkRocketEntity entity;

    private RemoveFireworkEvent() {
    }

    public static RemoveFireworkEvent get(FireworkRocketEntity entity) {
        RemoveFireworkEvent.instance.entity = entity;
        instance.setCancelled(false);
        return instance;
    }

    public FireworkRocketEntity getRocketEntity() {
        return this.entity;
    }
}

