/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class ClickBlockEvent
extends Event {
    private BlockPos pos;
    private Direction direction;
    private static final ClickBlockEvent INSTANCE = new ClickBlockEvent();

    private ClickBlockEvent() {
    }

    public Direction getDirection() {
        return this.direction;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public static ClickBlockEvent get(BlockPos pos, Direction direction) {
        ClickBlockEvent.INSTANCE.pos = pos;
        ClickBlockEvent.INSTANCE.direction = direction;
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }
}

