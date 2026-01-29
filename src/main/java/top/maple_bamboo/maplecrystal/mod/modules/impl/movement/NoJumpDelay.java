/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.modules.impl.movement;

import top.maple_bamboo.maplecrystal.api.events.eventbus.EventListener;
import top.maple_bamboo.maplecrystal.api.events.impl.UpdateEvent;
import top.maple_bamboo.maplecrystal.asm.accessors.ILivingEntity;
import top.maple_bamboo.maplecrystal.mod.modules.Module;

public class NoJumpDelay
extends Module {
    public static NoJumpDelay INSTANCE;

    public NoJumpDelay() {
        super("NoJumpDelay", Module.Category.Movement);
        this.setChinese("\u65e0\u8df3\u8dc3\u51b7\u5374");
        INSTANCE = this;
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        ((ILivingEntity)NoJumpDelay.mc.player).setLastJumpCooldown(0);
    }
}

