/*
 * Decompiled with CFR 0.152.
 */
package top.maple_bamboo.maplecrystal.mod.modules.impl.misc;

import top.maple_bamboo.maplecrystal.api.events.eventbus.EventListener;
import top.maple_bamboo.maplecrystal.api.events.impl.SendMessageEvent;
import top.maple_bamboo.maplecrystal.mod.modules.Module;
import top.maple_bamboo.maplecrystal.mod.modules.settings.impl.StringSetting;

public class ChatAppend
extends Module {
    public static ChatAppend INSTANCE;
    private final StringSetting message = this.add(new StringSetting("Text", "MapleCrystal"));

    public ChatAppend() {
        super("ChatAppend", Module.Category.Misc);
        this.setChinese("\u6d88\u606f\u540e\u7f00");
        INSTANCE = this;
    }

    @EventListener
    public void onSendMessage(SendMessageEvent event) {
        if (ChatAppend.nullCheck() || event.isCancelled() || AutoReconnect.inQueueServer) {
            return;
        }
        Object message = event.message;
        if (((String)message).startsWith("/") || ((String)message).startsWith("!") || ((String)message).startsWith("$") || ((String)message).startsWith("#") || ((String)message).endsWith(this.message.getValue())) {
            return;
        }
        String suffix = this.message.getValue();
        event.message = (String)message + " " + suffix;
    }
}

