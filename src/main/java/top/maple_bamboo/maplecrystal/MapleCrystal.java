/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal;

import top.maple_bamboo.maplecrystal.api.events.eventbus.EventBus;
import top.maple_bamboo.maplecrystal.core.impl.*;

public class MapleCrystal {
    public static final String NAME = Config.NAME;
    public static final String VERSION = Config.VERSION;
    public static final EventBus EVENT_BUS = Config.EVENT_BUS;
    public static HoleManager HOLE = Config.HOLE;
    public static PlayerManager PLAYER = Config.PLAYER;
    public static TradeManager TRADE = Config.TRADE;
    public static CleanerManager CLEANER = Config.CLEANER;
    public static XrayManager XRAY = Config.XRAY;
    public static ModuleManager MODULE = Config.MODULE;
    public static CommandManager COMMAND = Config.COMMAND;
    public static ConfigManager CONFIG = Config.CONFIG;
    public static RotationManager ROTATION = Config.ROTATION;
    public static BreakManager BREAK = Config.BREAK;
    public static PopManager POP = Config.POP;
    public static FriendManager FRIEND = Config.FRIEND;
    public static TimerManager TIMER = Config.TIMER;
    public static ShaderManager SHADER = Config.SHADER;
    public static BlurManager BLUR = Config.BLUR;
    public static FPSManager FPS = Config.FPS;
    public static ServerManager SERVER = Config.SERVER;
    public static ThreadManager THREAD = Config.THREAD;
    public static boolean loaded = Config.loaded;
    public static long initTime = Config.initTime;

    public static String getPrefix() {
        return Config.getPrefix();
    }

    public static void save() {
        Config.save();
    }
}

