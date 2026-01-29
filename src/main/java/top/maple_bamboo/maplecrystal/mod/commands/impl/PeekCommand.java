/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import net.minecraft.item.ItemStack;
import top.maple_bamboo.maplecrystal.mod.commands.Command;
import top.maple_bamboo.maplecrystal.mod.modules.impl.misc.ShulkerViewer;

import java.util.List;

public class PeekCommand
extends Command {
    private static final ItemStack[] ITEMS = new ItemStack[27];

    public PeekCommand() {
        super("peek", "");
    }

    @Override
    public void runCommand(String[] parameters) {
        ShulkerViewer.openContainer(PeekCommand.mc.player.getMainHandStack(), ITEMS, true);
    }

    @Override
    public String[] getAutocorrect(int count, List<String> seperated) {
        return null;
    }
}

