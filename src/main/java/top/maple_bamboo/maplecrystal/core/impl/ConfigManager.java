/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  by.radioegor146.nativeobfuscator.Native
 *  com.google.common.base.Splitter
 *  org.apache.commons.io.IOUtils
 */
package top.maple_bamboo.maplecrystal.core.impl;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.core.Manager;
import top.maple_bamboo.maplecrystal.mod.modules.Module;
import top.maple_bamboo.maplecrystal.mod.modules.impl.client.HUD;
import top.maple_bamboo.maplecrystal.mod.modules.settings.Setting;
import top.maple_bamboo.maplecrystal.mod.modules.settings.impl.*;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ConfigManager
extends Manager {
    public static File options = ConfigManager.getFile("options.txt");
    private final Hashtable<String, String> settings = new Hashtable();

    public ConfigManager() {
        this.loadFromFile();
    }

    public void load() {
        for (Module module : MapleCrystal.MODULE.getModules()) {
            for (Setting setting : module.getSettings()) {
                String line = module.getName() + "_" + setting.getName();
                Objects.requireNonNull(setting);
                if (setting instanceof BooleanSetting) {
                    BooleanSetting s = (BooleanSetting)setting;
                    s.setValueWithoutTask(MapleCrystal.CONFIG.getBoolean(line, s.getDefaultValue()));
                } else if (setting instanceof SliderSetting) {
                    SliderSetting s = (SliderSetting)setting;
                    s.setValue(MapleCrystal.CONFIG.getFloat(line, (float)s.getDefaultValue()));
                } else if (setting instanceof BindSetting) {
                    BindSetting s = (BindSetting)setting;
                    s.setValue(MapleCrystal.CONFIG.getInt(line, s.getDefaultValue()));
                    s.setHoldEnable(MapleCrystal.CONFIG.getBoolean(line + "_hold"));
                } else if (setting instanceof EnumSetting) {
                    EnumSetting s = (EnumSetting)setting;
                    s.loadSetting(MapleCrystal.CONFIG.getString(line));
                } else if (setting instanceof ColorSetting) {
                    ColorSetting s = (ColorSetting)setting;
                    s.setValue(new Color(MapleCrystal.CONFIG.getInt(line, s.getDefaultValue().getRGB()), true));
                    s.setSync(MapleCrystal.CONFIG.getBoolean(line + "Sync", s.getDefaultSync()));
                    if (s.injectBoolean) {
                        s.booleanValue = MapleCrystal.CONFIG.getBoolean(line + "Boolean", s.getDefaultBooleanValue());
                    }
                } else if (setting instanceof StringSetting) {
                    StringSetting s = (StringSetting)setting;
                    s.setValue(MapleCrystal.CONFIG.getString(line, s.getDefaultValue()));
                }
            }
            module.setState(MapleCrystal.CONFIG.getBoolean(module.getName() + "_state", module instanceof HUD));
        }
    }

    public void saveToFile() {
        PrintWriter printwriter = null;
        try {
            printwriter = new PrintWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(options), StandardCharsets.UTF_8));
            for (Module module : MapleCrystal.MODULE.getModules()) {
                for (Setting setting : module.getSettings()) {
                    String line = module.getName() + "_" + setting.getName();
                    Objects.requireNonNull(setting);
                    if (setting instanceof BooleanSetting) {
                        BooleanSetting s = (BooleanSetting)setting;
                        printwriter.println(line + ":" + s.getValue());
                    } else if (setting instanceof SliderSetting) {
                        SliderSetting s = (SliderSetting)setting;
                        printwriter.println(line + ":" + s.getValue());
                    } else if (setting instanceof BindSetting) {
                        BindSetting s = (BindSetting)setting;
                        printwriter.println(line + ":" + s.getValue());
                        printwriter.println(line + "_hold:" + s.isHoldEnable());
                    } else if (setting instanceof EnumSetting) {
                        EnumSetting s = (EnumSetting)setting;
                        printwriter.println(line + ":" + ((Enum)s.getValue()).name());
                    } else if (setting instanceof ColorSetting) {
                        ColorSetting s = (ColorSetting)setting;
                        printwriter.println(line + ":" + s.getValue().getRGB());
                        printwriter.println(line + "Sync:" + s.sync);
                        if (s.injectBoolean) {
                            printwriter.println(line + "Boolean:" + s.booleanValue);
                        }
                    } else if (setting instanceof StringSetting) {
                        StringSetting s = (StringSetting)setting;
                        printwriter.println(line + ":" + s.getValue());
                    }
                }
                printwriter.println(module.getName() + "_state:" + module.isOn());
            }
            IOUtils.closeQuietly((Writer)printwriter);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("[MapleCrystal] Failed to save settings");
        }
        finally {
            IOUtils.closeQuietly(printwriter);
        }
    }

    public void loadFromFile() {
        Splitter COLON_SPLITTER = Splitter.on((char)':');
        try {
            if (!options.exists()) {
                return;
            }
            List<String> list = IOUtils.readLines((InputStream)new FileInputStream(options), (Charset)StandardCharsets.UTF_8);
            for (String s : list) {
                try {
                    Iterator iterator = COLON_SPLITTER.limit(2).split((CharSequence)s).iterator();
                    this.settings.put((String)iterator.next(), (String)iterator.next());
                }
                catch (Exception var10) {
                    System.out.println("Skipping bad option: " + s);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("[MapleCrystal] Failed to load settings");
        }
    }

    public int getInt(String setting, int defaultValue) {
        String s = this.settings.get(setting);
        if (s == null || !this.isInteger(s)) {
            return defaultValue;
        }
        return Integer.parseInt(s);
    }

    public float getFloat(String setting, float defaultValue) {
        String s = this.settings.get(setting);
        if (s == null || !this.isFloat(s)) {
            return defaultValue;
        }
        return Float.parseFloat(s);
    }

    public boolean getBoolean(String setting) {
        String s = this.settings.get(setting);
        return Boolean.parseBoolean(s);
    }

    public boolean getBoolean(String setting, boolean defaultValue) {
        if (this.settings.get(setting) != null) {
            String s = this.settings.get(setting);
            return Boolean.parseBoolean(s);
        }
        return defaultValue;
    }

    public String getString(String setting) {
        return this.settings.get(setting);
    }

    public String getString(String setting, String defaultValue) {
        if (this.settings.get(setting) == null) {
            return defaultValue;
        }
        return this.settings.get(setting);
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean isFloat(String str) {
        String pattern = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
        return str.matches(pattern);
    }
}

