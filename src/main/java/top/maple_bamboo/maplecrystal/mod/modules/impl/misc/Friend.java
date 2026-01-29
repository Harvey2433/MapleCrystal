/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.util.hit.EntityHitResult
 *  net.minecraft.util.hit.HitResult
 */
package top.maple_bamboo.maplecrystal.mod.modules.impl.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.mod.modules.Module;

public class Friend
extends Module {
    public static Friend INSTANCE;

    public Friend() {
        super("Friend", Module.Category.Misc);
        this.setChinese("\u597d\u53cb");
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        EntityHitResult entityHitResult;
        Entity entity;
        if (Friend.nullCheck()) {
            this.disable();
            return;
        }
        HitResult target = Friend.mc.crosshairTarget;
        if (target instanceof EntityHitResult && (entity = (entityHitResult = (EntityHitResult)target).getEntity()) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            MapleCrystal.FRIEND.friend(player);
        }
        this.disable();
    }
}

