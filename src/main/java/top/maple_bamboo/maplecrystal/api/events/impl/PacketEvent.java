/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.packet.Packet
 */
package top.maple_bamboo.maplecrystal.api.events.impl;

import net.minecraft.network.packet.Packet;
import top.maple_bamboo.maplecrystal.api.events.Event;

public class PacketEvent
extends Event {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet, Event.Stage stage) {
        super(stage);
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public static class Receive
    extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet, Event.Stage.Pre);
        }
    }

    public static class Sent
    extends PacketEvent {
        public Sent(Packet<?> packet) {
            super(packet, Event.Stage.Post);
        }
    }

    public static class Send
    extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet, Event.Stage.Pre);
        }
    }
}

