/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl
 *  net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractBlockRenderContext
 *  net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package top.maple_bamboo.maplecrystal.asm.mixins;

import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractBlockRenderContext;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.maple_bamboo.maplecrystal.mod.modules.impl.render.Xray;

@Mixin(value={AbstractBlockRenderContext.class})
public abstract class MixinAbstractBlockRenderContext {
    @Final
    @Shadow(remap=false)
    protected BlockRenderInfo blockInfo;

    @Inject(method={"renderQuad"}, at={@At(value="INVOKE", target="Lnet/fabricmc/fabric/impl/client/indigo/renderer/render/AbstractBlockRenderContext;bufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lnet/minecraft/client/render/VertexConsumer;)V")}, cancellable=true)
    private void onBufferQuad(MutableQuadViewImpl quad, CallbackInfo ci) {
        if (Xray.shouldBlock(this.blockInfo.blockState)) {
            ci.cancel();
        }
    }
}

