/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.input.KeyboardInput
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package top.maple_bamboo.maplecrystal.asm.mixins;

import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.api.events.impl.KeyboardInputEvent;

@Mixin(value={KeyboardInput.class})
public class MixinKeyboardInput {
    @Inject(method={"tick"}, at={@At(value="FIELD", target="Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", shift=At.Shift.AFTER)}, cancellable=true)
    private void keyInput(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        KeyboardInputEvent event = KeyboardInputEvent.get();
        MapleCrystal.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}

