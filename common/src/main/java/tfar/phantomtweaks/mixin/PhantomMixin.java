package tfar.phantomtweaks.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.phantomtweaks.entity.PhantomHurtByTargetGoal;
import tfar.phantomtweaks.entity.PhantomNeutralAttackGoal;

import java.util.UUID;

@Mixin(Phantom.class)
public abstract class PhantomMixin extends Mob implements NeutralMob {

    protected PhantomMixin(EntityType<? extends Mob> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Redirect(method = "registerGoals",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",ordinal = 3))
    private void disableGoal(GoalSelector instance, int $$0, Goal $$1) {

    }


    @Inject(method = "registerGoals",at = @At("RETURN"))
    private void addModGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(1, new PhantomNeutralAttackGoal((Phantom) (Object)this));
        this.targetSelector.addGoal(1, new PhantomHurtByTargetGoal((Phantom) (Object)this).setAlertOthers());
    }

    @Inject(method = "tick",at = @At("RETURN"))
    private void onTickEvent(CallbackInfo ci) {
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
    }

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    @Override
    public int getRemainingPersistentAngerTime() {
        return remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int var1) {
        remainingPersistentAngerTime = var1;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID var1) {
        persistentAngerTarget = var1;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
