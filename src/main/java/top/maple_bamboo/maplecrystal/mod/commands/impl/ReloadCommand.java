/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import top.maple_bamboo.maplecrystal.Config;
import top.maple_bamboo.maplecrystal.core.impl.ConfigManager;
import top.maple_bamboo.maplecrystal.mod.commands.Command;

import java.util.List;

public class ReloadCommand
extends Command {
    public ReloadCommand() {
        super("reload", "");
    }

    @Override
    public void runCommand(String[] parameters) {
        this.sendChatMessage("\u00a7fReloading..");
        Config.CONFIG = new ConfigManager();
        Config.CONFIG.load();
        Config.CLEANER.read();
        Config.XRAY.read();
        Config.TRADE.read();
        Config.FRIEND.read();
    }

    @Override
    public String[] getAutocorrect(int count, List<String> seperated) {
        return null;
    }
}

