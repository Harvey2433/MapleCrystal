/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import top.maple_bamboo.maplecrystal.api.events.Event;

public class JumpEvent
extends Event {
    private static final JumpEvent instance = new JumpEvent();

    private JumpEvent() {
    }

    public static JumpEvent get(Event.Stage stage) {
        JumpEvent.instance.stage = stage;
        return instance;
    }
}

