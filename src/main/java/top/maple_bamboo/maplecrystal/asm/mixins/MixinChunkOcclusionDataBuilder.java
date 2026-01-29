/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder
 *  net.minecraft.util.math.BlockPos
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package top.maple_bamboo.maplecrystal.asm.mixins;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.maple_bamboo.maplecrystal.Config;
import top.maple_bamboo.maplecrystal.api.events.impl.ChunkOcclusionEvent;

@Mixin(value={ChunkOcclusionDataBuilder.class})
public abstract class MixinChunkOcclusionDataBuilder {
    @Inject(method={"markClosed"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMarkClosed(BlockPos pos, CallbackInfo info) {
        ChunkOcclusionEvent event = Config.EVENT_BUS.post(ChunkOcclusionEvent.get());
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}

