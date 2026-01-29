/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.entity.BlockEntity
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.block.entity.BlockEntity;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class RenderBlockEntityEvent
extends Event {
    private static final RenderBlockEntityEvent INSTANCE = new RenderBlockEntityEvent();
    public BlockEntity blockEntity;

    public static RenderBlockEntityEvent get(BlockEntity blockEntity) {
        INSTANCE.setCancelled(false);
        RenderBlockEntityEvent.INSTANCE.blockEntity = blockEntity;
        return INSTANCE;
    }
}

