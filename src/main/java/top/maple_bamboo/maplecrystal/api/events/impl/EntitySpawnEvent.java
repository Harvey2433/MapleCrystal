/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.entity.Entity;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class EntitySpawnEvent
extends Event {
    private static final EntitySpawnEvent INSTANCE = new EntitySpawnEvent();
    private Entity entity;

    private EntitySpawnEvent() {
    }

    public static EntitySpawnEvent get(Entity entity) {
        EntitySpawnEvent.INSTANCE.entity = entity;
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

