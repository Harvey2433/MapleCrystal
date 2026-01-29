/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.entity.Entity;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class LookDirectionEvent
extends Event {
    private static final LookDirectionEvent instance = new LookDirectionEvent();
    private Entity entity;
    private double cursorDeltaX;
    private double cursorDeltaY;

    private LookDirectionEvent() {
    }

    public static LookDirectionEvent get(Entity entity, double cursorDeltaX, double cursorDeltaY) {
        LookDirectionEvent.instance.entity = entity;
        LookDirectionEvent.instance.cursorDeltaX = cursorDeltaX;
        LookDirectionEvent.instance.cursorDeltaY = cursorDeltaY;
        instance.setCancelled(false);
        return instance;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getCursorDeltaX() {
        return this.cursorDeltaX;
    }

    public double getCursorDeltaY() {
        return this.cursorDeltaY;
    }
}

