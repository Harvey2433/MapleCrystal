/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import top.maple_bamboo.maplecrystal.api.events.Event;

public class DoAttackEvent
extends Event {
    public static final DoAttackEvent INSTANCE = new DoAttackEvent();

    public static DoAttackEvent getPre() {
        DoAttackEvent.INSTANCE.stage = Event.Stage.Pre;
        return INSTANCE;
    }

    public static DoAttackEvent getPost() {
        DoAttackEvent.INSTANCE.stage = Event.Stage.Post;
        return INSTANCE;
    }
}

