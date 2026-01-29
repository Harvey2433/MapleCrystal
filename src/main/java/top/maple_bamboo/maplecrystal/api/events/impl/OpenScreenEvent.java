/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.client.gui.screen.Screen;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class OpenScreenEvent
extends Event {
    private static final OpenScreenEvent INSTANCE = new OpenScreenEvent();
    public Screen screen;

    private OpenScreenEvent() {
    }

    public static OpenScreenEvent get(Screen screen) {
        OpenScreenEvent.INSTANCE.screen = screen;
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }
}

