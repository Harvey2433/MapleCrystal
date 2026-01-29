/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.DownloadingTerrainScreen
 *  net.minecraft.client.gui.screen.ProgressScreen
 */
package top.maple_bamboo.maplecrystal.mod.modules.impl.misc;

import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ProgressScreen;
import top.maple_bamboo.maplecrystal.api.events.eventbus.EventListener;
import top.maple_bamboo.maplecrystal.api.events.impl.ClientTickEvent;
import top.maple_bamboo.maplecrystal.mod.modules.Module;

public class NoTerrainScreen
extends Module {
    public NoTerrainScreen() {
        super("NoTerrainScreen", Module.Category.Misc);
        this.setChinese("\u6ca1\u6709\u52a0\u8f7d\u754c\u9762");
    }

    @EventListener
    public void onEvent(ClientTickEvent event) {
        if (NoTerrainScreen.nullCheck()) {
            return;
        }
        if (NoTerrainScreen.mc.currentScreen instanceof DownloadingTerrainScreen || NoTerrainScreen.mc.currentScreen instanceof ProgressScreen) {
            NoTerrainScreen.mc.currentScreen = null;
        }
    }
}

