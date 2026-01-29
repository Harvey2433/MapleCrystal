/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.util.Hand;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class InteractBlockEvent
extends Event {
    private static final InteractBlockEvent INSTANCE = new InteractBlockEvent();
    public Hand hand;

    private InteractBlockEvent() {
    }

    public static InteractBlockEvent getPre(Hand hand) {
        InteractBlockEvent.INSTANCE.hand = hand;
        InteractBlockEvent.INSTANCE.stage = Event.Stage.Pre;
        INSTANCE.setCancelled(false);
        return INSTANCE;
    }

    public static InteractBlockEvent getPost(Hand hand) {
        InteractBlockEvent.INSTANCE.hand = hand;
        InteractBlockEvent.INSTANCE.stage = Event.Stage.Post;
        return INSTANCE;
    }
}

