package tfar.phantomtweaks.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;

public class PhantomNeutralAttackGoal extends Goal {
    private final TargetingConditions attackTargeting;
    private final Phantom phantom;
    private int nextScanTick = reducedTickDelay(20);

    public PhantomNeutralAttackGoal(Phantom phantom) {
        this.phantom = phantom;
        attackTargeting = TargetingConditions.forCombat().range(64.0).selector(living -> ((NeutralMob)phantom).isAngryAt(living));
    }


    @Override
    public boolean canUse() {
        if (this.nextScanTick > 0) {
            --this.nextScanTick;
        } else {
            this.nextScanTick = reducedTickDelay(60);
            List<Player> $$0 = phantom.level()
                    .getNearbyPlayers(this.attackTargeting, phantom, phantom.getBoundingBox().inflate(16.0, 64.0, 16.0));
            if (!$$0.isEmpty()) {
                $$0.sort(Comparator.<Player, Double>comparing(Entity::getY).reversed());

                for (Player $$1 : $$0) {
                    if (phantom.canAttack($$1, TargetingConditions.DEFAULT)) {
                        phantom.setTarget($$1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity $$0 = phantom.getTarget();
        return $$0 != null && phantom.canAttack($$0, TargetingConditions.DEFAULT);
    }
}
