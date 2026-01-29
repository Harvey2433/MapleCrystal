/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.core.Manager;
import top.maple_bamboo.maplecrystal.core.impl.ConfigManager;
import top.maple_bamboo.maplecrystal.mod.commands.Command;

import java.io.File;
import java.util.List;

public class SaveCommand
extends Command {
    public SaveCommand() {
        super("save", "");
    }

    @Override
    public void runCommand(String[] parameters) {
        if (parameters.length == 1) {
            this.sendChatMessage("\u00a7fSaving config named " + parameters[0]);
            File originalOptions = ConfigManager.options;
            try {
                ConfigManager.options = Manager.getFile("cfg" + File.separator + parameters[0] + ".cfg");
                MapleCrystal.save();
                this.sendChatMessage("\u00a7fConfig saved as " + parameters[0]);
            } finally {
                ConfigManager.options = originalOptions;
            }
        } else {
            this.sendChatMessage("\u00a7fSaving..");
            MapleCrystal.save();
            this.sendChatMessage("\u00a7fConfig saved");
        }
    }

    @Override
    public String[] getAutocorrect(int count, List<String> seperated) {
        return null;
    }
}

