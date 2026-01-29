/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.modules.impl.client;

import top.maple_bamboo.maplecrystal.mod.modules.Module;
import top.maple_bamboo.maplecrystal.mod.modules.settings.impl.ColorSetting;

import java.awt.*;

public class ColorsModule
extends Module {
    public static ColorsModule INSTANCE;
    public final ColorSetting clientColor = this.add(new ColorSetting("Color", new Color(255, 0, 0)).allowClientColor(false));

    public ColorsModule() {
        super("Colors", Module.Category.Client);
        this.setChinese("\u989c\u8272");
        INSTANCE = this;
    }

    @Override
    public void enable() {
        this.state = true;
    }

    @Override
    public void disable() {
        this.state = true;
    }

    @Override
    public boolean isOn() {
        return true;
    }
}

