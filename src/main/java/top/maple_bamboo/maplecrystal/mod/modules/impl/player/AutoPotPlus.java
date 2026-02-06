package top.maple_bamboo.maplecrystal.mod.modules.impl.player;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import top.maple_bamboo.maplecrystal.MapleCrystal;
import top.maple_bamboo.maplecrystal.api.events.eventbus.EventListener;
import top.maple_bamboo.maplecrystal.api.events.impl.Render3DEvent;
import top.maple_bamboo.maplecrystal.api.events.impl.UpdateEvent;
import top.maple_bamboo.maplecrystal.api.utils.math.Timer;
import top.maple_bamboo.maplecrystal.api.utils.player.EntityUtil;
import top.maple_bamboo.maplecrystal.api.utils.player.InventoryUtil;
import top.maple_bamboo.maplecrystal.api.utils.render.Render3DUtil;
import top.maple_bamboo.maplecrystal.api.utils.world.BlockPosX;
import top.maple_bamboo.maplecrystal.mod.modules.Module;
import top.maple_bamboo.maplecrystal.mod.modules.settings.impl.BooleanSetting;
import top.maple_bamboo.maplecrystal.mod.modules.settings.impl.SliderSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AutoPotPlus extends Module {
    public static AutoPotPlus INSTANCE;

    // Settings
    private final BooleanSetting rotate = add(new BooleanSetting("Rotate", true));
    private final SliderSetting pitch = add(new SliderSetting("Pitch", 86.0, 80.0, 90.0, 0.1));
    private final BooleanSetting inventory = add(new BooleanSetting("InventorySwap", true));
    private final SliderSetting delay = add(new SliderSetting("Delay", 1050.0, 0.0, 2000.0, 10.0).setSuffix("ms"));

    private final BooleanSetting hCheck = add(new BooleanSetting("HealthCheck", false));
    private final SliderSetting health = add(new SliderSetting("Health", 20.0, 0.0, 36.0, 0.5).setSuffix("HP"));

    private final SliderSetting effectRange = add(new SliderSetting("EffectRange", 3.0, 0.0, 6.0, 0.1));
    private final SliderSetting predictTicks = add(new SliderSetting("Predict", 2.0, 0.0, 10.0, 1.0).setSuffix("ticks"));
    private final BooleanSetting debug = add(new BooleanSetting("Debug", false));

    private final Timer timer = new Timer();
    private boolean firstRun = true;

    public AutoPotPlus() {
        super("AutoPot+", Category.Player);
        this.setChinese("自动药水+");
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // 开启时作为"首次运行"，如果没BUFF则无视延迟直接喷
        firstRun = true;
        timer.reset();
    }

    @Override
    public String getInfo() {
        // 修复：正确获取原始 StatusEffect 进行比较
        int count = getPotCount((StatusEffect) StatusEffects.RESISTANCE.value());
        return String.valueOf(count);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null || mc.world == null) return;

        // 1. 血量检测 (如果血量健康，直接忽略一切逻辑)
        if (hCheck.getValue() && (mc.player.getHealth() + mc.player.getAbsorptionAmount()) >= health.getValue()) {
            return;
        }

        // 2. 药水效果检测
        boolean hasEffect = false;
        List<StatusEffectInstance> effects = new ArrayList<>(mc.player.getStatusEffects());

        // 获取目标效果的原始对象
        StatusEffect targetEffect = (StatusEffect) StatusEffects.RESISTANCE.value();

        for (StatusEffectInstance potionEffect : effects) {
            // 关键修复：
            // 必须都调用 .value() 获取原始 StatusEffect 对象进行比较，否则 RegistryEntry 对象可能不相等
            // Amplifier 2 = Resistance III (抗性提升3)
            // Amplifier 3 = Resistance IV (抗性提升4)
            // 这里设置为 >= 2，确保 抗性3 和 抗性4 都会被识别为"有BUFF"
            if (potionEffect.getEffectType().value() == targetEffect && potionEffect.getAmplifier() >= 2) {
                hasEffect = true;
                break;
            }
        }

        // 3. 状态机逻辑

        // 情况 A: 玩家身上有抗性3或更高BUFF
        if (hasEffect) {
            // 只要有效果，就一直重置计时器
            // 这样 timer.passedMs() 永远是从 0 开始算
            // 也就实现了 "有效果就不喷，等待"
            timer.reset();
            firstRun = false; // 既然检测到了BUFF，首次运行标记失效
            return;
        }

        // 情况 B: 玩家身上没有BUFF
        // 此时程序会走到这里
        // 计时器从 "上次 hasEffect 为 true 的最后时刻" 开始自动累计时间

        // 触发条件：(首次开启且没BUFF) 或者 (BUFF消失后过了 Delay 设置的时间)
        if (firstRun || timer.passedMs(delay.getValue())) {

            // 物理预测 + 投掷
            if (checkPhysicsAndThrow()) {
                // 投掷成功后：
                // 关闭首次运行标记
                firstRun = false;
                // 重置计时器，进入下一个循环等待
                timer.reset();
            }
        }
    }

    private boolean checkPhysicsAndThrow() {
        Vec3d motionVec = mc.player.getVelocity().multiply(predictTicks.getValue());
        Vec3d predictedPos = mc.player.getPos().add(motionVec);

        Vec3d impactPos = calcTrajectory(Items.SPLASH_POTION, MapleCrystal.ROTATION.rotationYaw, (float) pitch.getValue());

        // 物理检查：如果扔出去会砸墙上或者离自己太远，就不扔
        if (impactPos == null || predictedPos.squaredDistanceTo(impactPos) > effectRange.getValue()) {
            return false;
        }

        doPot();
        return true;
    }


    private void doPot() {
        int oldSlot = mc.player.getInventory().selectedSlot;
        int slot = findPotSlot((StatusEffect) StatusEffects.RESISTANCE.value());

        if (slot == -1) return;

        doSwap(slot);

        if (this.rotate.getValue()) {
            MapleCrystal.ROTATION.snapAt(MapleCrystal.ROTATION.rotationYaw, (float) pitch.getValue());
        }

        sendSequencedPacket(id -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, id, MapleCrystal.ROTATION.getLastYaw(), MapleCrystal.ROTATION.getLastPitch()));

        if (inventory.getValue()) {
            doSwap(slot);
            EntityUtil.syncInventory();
        } else {
            doSwap(oldSlot);
        }

        if (this.rotate.getValue()) {
            MapleCrystal.ROTATION.snapBack();
        }
    }

    private void doSwap(int slot) {
        if (inventory.getValue()) {
            InventoryUtil.inventorySwap(slot, mc.player.getInventory().selectedSlot);
        } else {
            InventoryUtil.switchToSlot(slot);
        }
    }

    private int findPotSlot(StatusEffect targetEffect) {
        if (inventory.getValue()) {
            for (int i = 0; i < 36; ++i) {
                if (checkStack(mc.player.getInventory().getStack(i), targetEffect)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < 9; ++i) {
                if (checkStack(mc.player.getInventory().getStack(i), targetEffect)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getPotCount(StatusEffect targetEffect) {
        int count = 0;
        for (int i = 0; i < 36; ++i) {
            if (checkStack(mc.player.getInventory().getStack(i), targetEffect)) {
                count++;
            }
        }
        return count;
    }

    private boolean checkStack(ItemStack itemStack, StatusEffect targetEffect) {
        if (itemStack.getItem() != Items.SPLASH_POTION) return false;

        PotionContentsComponent contents = itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
        for (StatusEffectInstance effect : contents.getEffects()) {
            // 修复：这里同样需要拆包比较
            if (effect.getEffectType().value() == targetEffect) return true;
        }
        return false;
    }

    private Vec3d calcTrajectory(Item item, float yaw, float pitch) {
        float tickDelta = mc.getRenderTickCounter().getTickDelta(true);

        double x = MathHelper.lerp(tickDelta, mc.player.prevX, mc.player.getX());
        double y = MathHelper.lerp(tickDelta, mc.player.prevY, mc.player.getY());
        double z = MathHelper.lerp(tickDelta, mc.player.prevZ, mc.player.getZ());

        y = y + mc.player.getEyeHeight(mc.player.getPose()) - 0.1000000014901161;

        x = x - MathHelper.cos(yaw / 180.0f * 3.1415927f) * 0.16f;
        z = z - MathHelper.sin(yaw / 180.0f * 3.1415927f) * 0.16f;

        final float maxDist = getDistance(item);
        double motionX = -MathHelper.sin(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * maxDist;
        double motionY = -MathHelper.sin((pitch - getThrowPitch(item)) / 180.0f * 3.141593f) * maxDist;
        double motionZ = MathHelper.cos(yaw / 180.0f * 3.1415927f) * MathHelper.cos(pitch / 180.0f * 3.1415927f) * maxDist;

        float power = mc.player.getItemUseTime() / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;
        if (power > 1.0f) {
            power = 1.0f;
        }

        final float distance = MathHelper.sqrt((float) (motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;

        final float pow = (item instanceof BowItem ? (power * 2.0f) : item instanceof CrossbowItem ? (2.2f) : 1.0f) * getThrowVelocity(item);

        motionX *= pow;
        motionY *= pow;
        motionZ *= pow;

        if (!mc.player.isOnGround()) {
            motionY += mc.player.getVelocity().getY();
        }

        Vec3d lastPos;
        for (int i = 0; i < 300; i++) {
            lastPos = new Vec3d(x, y, z);
            x += motionX;
            y += motionY;
            z += motionZ;

            if (mc.world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).getBlock() == Blocks.WATER) {
                motionX *= 0.8;
                motionY *= 0.8;
                motionZ *= 0.8;
            } else {
                motionX *= 0.99;
                motionY *= 0.99;
                motionZ *= 0.99;
            }

            if (item instanceof BowItem) motionY -= 0.05000000074505806;
            else if (mc.player.getMainHandStack().getItem() instanceof CrossbowItem) motionY -= 0.05000000074505806;
            else motionY -= 0.03f;

            Vec3d pos = new Vec3d(x, y, z);

            BlockHitResult bhr = mc.world.raycast(new RaycastContext(lastPos, pos, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, mc.player));
            if (bhr != null && bhr.getType() == HitResult.Type.BLOCK) {
                return bhr.getPos();
            }
        }
        return null;
    }

    private float getDistance(Item item) {
        return item instanceof BowItem ? 1.0f : 0.4f;
    }

    private float getThrowVelocity(Item item) {
        if (item instanceof SplashPotionItem || item instanceof LingeringPotionItem) return 0.5f;
        if (item instanceof ExperienceBottleItem) return 0.59f;
        if (item instanceof TridentItem) return 2f;
        return 1.5f;
    }

    private int getThrowPitch(Item item) {
        if (item instanceof SplashPotionItem || item instanceof LingeringPotionItem || item instanceof ExperienceBottleItem)
            return 20;
        return 0;
    }
}