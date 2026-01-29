/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.ClientPlayerEntity
 *  net.minecraft.client.network.ClientPlayerInteractionManager
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.Hand
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Constant
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyConstant
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package top.maple_bamboo.maplecrystal.asm.mixins;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.api.events.impl.ClickBlockEvent;
import top.maple_bamboo.maplecrystal.api.events.impl.InteractBlockEvent;
import top.maple_bamboo.maplecrystal.api.events.impl.InteractItemEvent;
import top.maple_bamboo.maplecrystal.mod.modules.impl.player.InteractTweaks;

@Mixin(value={ClientPlayerInteractionManager.class})
public class MixinClientPlayerInteractionManager {
    @Shadow
    private ItemStack selectedStack;

    @ModifyVariable(method={"isCurrentlyBreaking"}, at=@At(value="STORE"))
    private ItemStack stack(ItemStack stack) {
        return InteractTweaks.INSTANCE.noReset() ? this.selectedStack : stack;
    }

    @ModifyConstant(method={"updateBlockBreakingProgress"}, constant={@Constant(intValue=5)})
    private int MiningCooldownFix(int value) {
        return InteractTweaks.INSTANCE.noDelay() ? 0 : value;
    }

    @Inject(method={"interactItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        InteractItemEvent event = InteractItemEvent.getPre(hand);
        MapleCrystal.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method={"interactItem"}, at={@At(value="RETURN")})
    private void hookInteractItemReturn(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        MapleCrystal.EVENT_BUS.post(InteractItemEvent.getPost(hand));
    }

    @Inject(method={"interactBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        InteractBlockEvent event = InteractBlockEvent.getPre(hand);
        MapleCrystal.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method={"interactBlock"}, at={@At(value="RETURN")})
    private void hookInteractBlockReturn(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        MapleCrystal.EVENT_BUS.post(InteractBlockEvent.getPost(hand));
    }

    @Inject(method={"cancelBlockBreaking"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookCancelBlockBreaking(CallbackInfo callbackInfo) {
        if (InteractTweaks.INSTANCE.noAbort()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"attackBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAttackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        ClickBlockEvent event = ClickBlockEvent.get(pos, direction);
        MapleCrystal.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}

