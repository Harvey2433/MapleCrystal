/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import top.maple_bamboo.maplecrystal.api.events.Event;

public class ChunkOcclusionEvent
extends Event {
    private static final ChunkOcclusionEvent INSTANCE = new ChunkOcclusionEvent();

    private ChunkOcclusionEvent() {
    }

    public static ChunkOcclusionEvent get() {
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }
}

