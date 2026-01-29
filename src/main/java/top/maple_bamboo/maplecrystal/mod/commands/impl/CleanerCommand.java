/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import net.minecraft.item.Items;
import top.maple_bamboo.maplecrystal.Config;
import top.maple_bamboo.maplecrystal.core.impl.PlayerManager;
import top.maple_bamboo.maplecrystal.mod.commands.Command;
import top.maple_bamboo.maplecrystal.mod.gui.windows.WindowsScreen;
import top.maple_bamboo.maplecrystal.mod.gui.windows.impl.ItemSelectWindow;

import java.util.ArrayList;
import java.util.List;

public class CleanerCommand
extends Command {
    public CleanerCommand() {
        super("cleaner", "[\"\"/name/reset/clear/list] | [add/remove] [name]");
    }

    @Override
    public void runCommand(String[] parameters) {
        if (parameters.length == 0) {
            PlayerManager.screenToOpen = new WindowsScreen(new ItemSelectWindow(Config.CLEANER));
            return;
        }
        switch (parameters[0]) {
            case "reset": {
                Config.CLEANER.clear();
                Config.CLEANER.add(Items.NETHERITE_SWORD.getTranslationKey());
                Config.CLEANER.add(Items.NETHERITE_PICKAXE.getTranslationKey());
                Config.CLEANER.add(Items.NETHERITE_HELMET.getTranslationKey());
                Config.CLEANER.add(Items.NETHERITE_CHESTPLATE.getTranslationKey());
                Config.CLEANER.add(Items.NETHERITE_LEGGINGS.getTranslationKey());
                Config.CLEANER.add(Items.NETHERITE_BOOTS.getTranslationKey());
                Config.CLEANER.add(Items.OBSIDIAN.getTranslationKey());
                Config.CLEANER.add(Items.ENDER_CHEST.getTranslationKey());
                Config.CLEANER.add(Items.ENDER_PEARL.getTranslationKey());
                Config.CLEANER.add(Items.ENCHANTED_GOLDEN_APPLE.getTranslationKey());
                Config.CLEANER.add(Items.EXPERIENCE_BOTTLE.getTranslationKey());
                Config.CLEANER.add(Items.COBWEB.getTranslationKey());
                Config.CLEANER.add(Items.POTION.getTranslationKey());
                Config.CLEANER.add(Items.SPLASH_POTION.getTranslationKey());
                Config.CLEANER.add(Items.TOTEM_OF_UNDYING.getTranslationKey());
                Config.CLEANER.add(Items.END_CRYSTAL.getTranslationKey());
                Config.CLEANER.add(Items.ELYTRA.getTranslationKey());
                Config.CLEANER.add(Items.FLINT_AND_STEEL.getTranslationKey());
                Config.CLEANER.add(Items.PISTON.getTranslationKey());
                Config.CLEANER.add(Items.STICKY_PISTON.getTranslationKey());
                Config.CLEANER.add(Items.REDSTONE_BLOCK.getTranslationKey());
                Config.CLEANER.add(Items.GLOWSTONE.getTranslationKey());
                Config.CLEANER.add(Items.RESPAWN_ANCHOR.getTranslationKey());
                Config.CLEANER.add(Items.ANVIL.getTranslationKey());
                this.sendChatMessage("\u00a7fItems list got reset");
                return;
            }
            case "clear": {
                Config.CLEANER.getList().clear();
                this.sendChatMessage("\u00a7fItems list got clear");
                return;
            }
            case "list": {
                if (Config.CLEANER.getList().isEmpty()) {
                    this.sendChatMessage("\u00a7fItems list is empty");
                    return;
                }
                for (String name : Config.CLEANER.getList()) {
                    this.sendChatMessage("\u00a7a" + name);
                }
                return;
            }
            case "add": {
                if (parameters.length == 2) {
                    Config.CLEANER.add(parameters[1]);
                    this.sendChatMessage("\u00a7f" + parameters[1] + (Config.CLEANER.inList(parameters[1]) ? " \u00a7ahas been added" : " \u00a7chas been removed"));
                    return;
                }
                this.sendUsage();
                return;
            }
            case "remove": {
                if (parameters.length == 2) {
                    Config.CLEANER.remove(parameters[1]);
                    this.sendChatMessage("\u00a7f" + parameters[1] + (Config.CLEANER.inList(parameters[1]) ? " \u00a7ahas been added" : " \u00a7chas been removed"));
                    return;
                }
                this.sendUsage();
                return;
            }
        }
        if (parameters.length == 1) {
            this.sendChatMessage("\u00a7f" + parameters[0] + (Config.CLEANER.inList(parameters[0]) ? " \u00a7ais in whitelist" : " \u00a7cisn't in whitelist"));
            return;
        }
        this.sendUsage();
    }

    @Override
    public String[] getAutocorrect(int count, List<String> seperated) {
        if (count == 1) {
            String input = seperated.getLast().toLowerCase();
            ArrayList<String> correct = new ArrayList<String>();
            List<String> list = List.of("add", "remove", "list", "reset", "clear");
            for (String x : list) {
                if (!input.equalsIgnoreCase(Config.getPrefix() + "cleaner") && !x.toLowerCase().startsWith(input)) continue;
                correct.add(x);
            }
            int numCmds = correct.size();
            String[] commands = new String[numCmds];
            int i = 0;
            for (String x : correct) {
                commands[i++] = x;
            }
            return commands;
        }
        return null;
    }
}

