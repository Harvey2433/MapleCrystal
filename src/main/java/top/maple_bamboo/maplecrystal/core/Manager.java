/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
 */
package top.maple_bamboo.maplecrystal.core;

import net.minecraft.client.MinecraftClient;
import top.maple_bamboo.maplecrystal.Config;

import java.io.File;

public class Manager {
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static File getFile(String s) {
        File folder = Manager.getFolder();
        File file = new File(folder, s);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        return file;
    }

    public static File getFolder() {
        File folder = new File(Manager.mc.runDirectory.getPath() + File.separator + Config.NAME.toLowerCase());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}

