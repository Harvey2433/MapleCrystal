/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 */
package top.maple_bamboo.maplecrystal.mod.commands.impl;

import net.minecraft.item.Items;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.core.impl.PlayerManager;
import top.maple_bamboo.maplecrystal.mod.commands.Command;
import top.maple_bamboo.maplecrystal.mod.gui.windows.WindowsScreen;
import top.maple_bamboo.maplecrystal.mod.gui.windows.impl.ItemSelectWindow;

import java.util.ArrayList;
import java.util.List;

public class TradeCommand
extends Command {
    public TradeCommand() {
        super("trade", "[\"\"/name/reset/clear/list] | [add/remove] [name]");
    }

    @Override
    public void runCommand(String[] parameters) {
        if (parameters.length == 0) {
            PlayerManager.screenToOpen = new WindowsScreen(new ItemSelectWindow(MapleCrystal.TRADE));
            return;
        }
        switch (parameters[0]) {
            case "reset": {
                MapleCrystal.TRADE.clear();
                MapleCrystal.TRADE.add(Items.ENCHANTED_BOOK.getTranslationKey());
                MapleCrystal.TRADE.add(Items.DIAMOND_BLOCK.getTranslationKey());
                this.sendChatMessage("\u00a7fItems list got reset");
                return;
            }
            case "clear": {
                MapleCrystal.TRADE.clear();
                this.sendChatMessage("\u00a7fItems list got clear");
                return;
            }
            case "list": {
                if (MapleCrystal.TRADE.getList().isEmpty()) {
                    this.sendChatMessage("\u00a7fItems list is empty");
                    return;
                }
                for (String name : MapleCrystal.TRADE.getList()) {
                    this.sendChatMessage("\u00a7a" + name);
                }
                return;
            }
            case "add": {
                if (parameters.length == 2) {
                    MapleCrystal.TRADE.add(parameters[1]);
                    this.sendChatMessage("\u00a7f" + parameters[1] + (MapleCrystal.TRADE.inWhitelist(parameters[1]) ? " \u00a7ahas been added" : " \u00a7chas been removed"));
                    return;
                }
                this.sendUsage();
                return;
            }
            case "remove": {
                if (parameters.length == 2) {
                    MapleCrystal.TRADE.remove(parameters[1]);
                    this.sendChatMessage("\u00a7f" + parameters[1] + (MapleCrystal.TRADE.inWhitelist(parameters[1]) ? " \u00a7ahas been added" : " \u00a7chas been removed"));
                    return;
                }
                this.sendUsage();
                return;
            }
        }
        if (parameters.length == 1) {
            this.sendChatMessage("\u00a7f" + parameters[0] + (MapleCrystal.TRADE.inWhitelist(parameters[0]) ? " \u00a7ais in whitelist" : " \u00a7cisn't in whitelist"));
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
                if (!input.equalsIgnoreCase(MapleCrystal.getPrefix() + "trade") && !x.toLowerCase().startsWith(input)) continue;
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

