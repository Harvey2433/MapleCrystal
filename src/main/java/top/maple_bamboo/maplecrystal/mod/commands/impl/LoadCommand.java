/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.core.Manager;
import top.maple_bamboo.maplecrystal.core.impl.ConfigManager;
import top.maple_bamboo.maplecrystal.mod.commands.Command;
import top.maple_bamboo.maplecrystal.mod.modules.impl.client.Fonts;

import java.io.File;
import java.util.List;

public class LoadCommand
extends Command {
    public LoadCommand() {
        super("load", "[config]");
    }

    @Override
    public void runCommand(String[] parameters) {
        if (parameters.length == 0) {
            this.sendUsage();
            return;
        }
        File configFile = Manager.getFile("cfg" + File.separator + parameters[0] + ".cfg");
        if (!configFile.exists()) {
            this.sendChatMessage("\u00a7cConfig file not found: " + parameters[0]);
            return;
        }
        this.sendChatMessage("\u00a7fLoading config " + parameters[0] + "..");
        File originalOptions = ConfigManager.options;
        try {
            ConfigManager.options = configFile;
            MapleCrystal.CONFIG = new ConfigManager();
            MapleCrystal.CONFIG.load();
            this.sendChatMessage("\u00a7fConfig loaded successfully: " + parameters[0]);
        } catch (Exception e) {
            this.sendChatMessage("\u00a7cFailed to load config: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConfigManager.options = originalOptions;
        }
        Fonts.INSTANCE.refresh();
    }

    @Override
    public String[] getAutocorrect(int count, List<String> seperated) {
        return null;
    }
}

