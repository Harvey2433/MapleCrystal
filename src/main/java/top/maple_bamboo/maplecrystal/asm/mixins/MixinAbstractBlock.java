/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.AbstractBlock
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.ShapeContext
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.util.shape.VoxelShapes
 *  net.minecraft.world.BlockView
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package top.maple_bamboo.maplecrystal.asm.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.maple_bamboo.maplecrystal.Config;
import top.maple_bamboo.maplecrystal.api.events.impl.AmbientOcclusionEvent;
import top.maple_bamboo.maplecrystal.mod.modules.impl.player.PacketMine;

@Mixin(value={AbstractBlock.class})
public abstract class MixinAbstractBlock {
    @Inject(method={"getAmbientOcclusionLightLevel"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        AmbientOcclusionEvent event = Config.EVENT_BUS.post(AmbientOcclusionEvent.get());
        if (event.lightLevel != -1.0f) {
            info.setReturnValue(Float.valueOf(event.lightLevel));
        }
    }

    @Inject(method={"getCollisionShape"}, at={@At(value="HEAD")}, cancellable=true)
    private void onComputeNextCollisionBox(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (PacketMine.INSTANCE != null && pos.equals((Object)PacketMine.getBreakPos()) && PacketMine.INSTANCE.noCollide.getValue() && PacketMine.ghost) {
            cir.setReturnValue(VoxelShapes.empty());
        }
    }
}

