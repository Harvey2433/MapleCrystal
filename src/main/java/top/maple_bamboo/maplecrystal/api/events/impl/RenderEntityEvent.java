/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.entity.Entity;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class RenderEntityEvent
extends Event {
    private static final RenderEntityEvent INSTANCE = new RenderEntityEvent();
    private Entity entity;

    private RenderEntityEvent() {
    }

    public static RenderEntityEvent get(Entity entity) {
        RenderEntityEvent.INSTANCE.entity = entity;
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

