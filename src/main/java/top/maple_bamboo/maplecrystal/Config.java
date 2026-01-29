/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by.radioegor146.nativeobfuscator.Native
 *  net.fabricmc.api.ModInitializer
 *  net.minecraft.client.MinecraftClient
 *  oshi.SystemInfo
 *  oshi.hardware.ComputerSystem
 *  oshi.hardware.HWDiskStore
 *  oshi.hardware.HardwareAbstractionLayer
 */
package top.maple_bamboo.maplecrystal;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import top.maple_bamboo.maplecrystal.api.events.eventbus.EventBus;
import top.maple_bamboo.maplecrystal.api.events.impl.InitEvent;
import top.maple_bamboo.maplecrystal.core.impl.*;
import top.maple_bamboo.maplecrystal.mod.modules.impl.client.ClientSetting;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class Config
implements ModInitializer {
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    private static final int PBKDF2_ITERATIONS = 600000;
    private static final int SALT_LENGTH = 16;
    public static final String NAME = "MapleCrystal";
    public static final String VERSION = "1.0.0";
    public static final EventBus EVENT_BUS = new EventBus();
    public static HoleManager HOLE;
    public static PlayerManager PLAYER;
    public static TradeManager TRADE;
    public static CleanerManager CLEANER;
    public static XrayManager XRAY;
    public static ModuleManager MODULE;
    public static CommandManager COMMAND;
    public static ConfigManager CONFIG;
    public static RotationManager ROTATION;
    public static BreakManager BREAK;
    public static PopManager POP;
    public static FriendManager FRIEND;
    public static TimerManager TIMER;
    public static ShaderManager SHADER;
    public static BlurManager BLUR;
    public static FPSManager FPS;
    public static ServerManager SERVER;
    public static ThreadManager THREAD;
    public static boolean loaded;
    public static long initTime;

    public static String getPrefix() {
        return ClientSetting.INSTANCE.prefix.getValue();
    }

    public static void save() {
        CONFIG.saveToFile();
        CLEANER.save();
        FRIEND.save();
        XRAY.save();
        TRADE.save();
        System.out.println("[MapleCrystal] Saved");
    }

    private void register() {
        EVENT_BUS.registerLambdaFactory((lookupInMethod, klass) -> (MethodHandles.Lookup)lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (loaded) {
                Config.save();
            }
        }));
    }

    public void onInitialize() {
        this.register();
        MODULE = new ModuleManager();
        MapleCrystal.MODULE = MODULE;
        CONFIG = new ConfigManager();
        HOLE = new HoleManager();
        COMMAND = new CommandManager();
        FRIEND = new FriendManager();
        XRAY = new XrayManager();
        CLEANER = new CleanerManager();
        TRADE = new TradeManager();
        ROTATION = new RotationManager();
        BREAK = new BreakManager();
        PLAYER = new PlayerManager();
        POP = new PopManager();
        TIMER = new TimerManager();
        SHADER = new ShaderManager();
        BLUR = new BlurManager();
        FPS = new FPSManager();
        SERVER = new ServerManager();
        MapleCrystal.CONFIG = CONFIG;
        MapleCrystal.HOLE = HOLE;
        MapleCrystal.MODULE = MODULE;
        MapleCrystal.COMMAND = COMMAND;
        MapleCrystal.FRIEND = FRIEND;
        MapleCrystal.XRAY = XRAY;
        MapleCrystal.CLEANER = CLEANER;
        MapleCrystal.TRADE = TRADE;
        MapleCrystal.ROTATION = ROTATION;
        MapleCrystal.BREAK = BREAK;
        MapleCrystal.PLAYER = PLAYER;
        MapleCrystal.POP = POP;
        MapleCrystal.TIMER = TIMER;
        MapleCrystal.SHADER = SHADER;
        MapleCrystal.BLUR = BLUR;
        MapleCrystal.FPS = FPS;
        MapleCrystal.SERVER = SERVER;
        CONFIG.load();
        THREAD = new ThreadManager();
        MapleCrystal.THREAD = THREAD;
        MapleCrystal.initTime = initTime = System.currentTimeMillis();
        MapleCrystal.loaded = loaded = true;
        EVENT_BUS.post(new InitEvent());
        File folder = new File(MinecraftClient.getInstance().runDirectory.getPath() + File.separator + NAME.toLowerCase() + File.separator + "cfg");
        if (!folder.exists()) {
            folder.mkdirs();
        }

    }
}
