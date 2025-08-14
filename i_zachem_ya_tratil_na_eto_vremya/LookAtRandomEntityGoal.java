package wta.entities.goals;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.EntityPredicates;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class LookAtRandomEntityGoal extends Goal {
    public static final float DEFAULT_CHANCE = 0.02F;
    protected final MobEntity mob;
    @Nullable
    protected Entity target;
    protected final float range;
    private int lookTime;
    protected final float chance;
    private final boolean lookForward;
    protected final Class<? extends Entity> targetType;
    protected final TargetPredicate targetPredicate;

    public LookAtRandomEntityGoal(MobEntity mob, Class<? extends Entity> targetType, float range) {
        this(mob, targetType, range, 0.02F);
    }

    public LookAtRandomEntityGoal(MobEntity mob, Class<? extends Entity> targetType, float range, float chance) {
        this(mob, targetType, range, chance, false);
    }

    public LookAtRandomEntityGoal(MobEntity mob, Class<? extends Entity> targetType, float range, float chance, boolean lookForward) {
        this.mob = mob;
        this.targetType = targetType;
        this.range = range;
        this.chance = chance;
        this.lookForward = lookForward;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
        if (targetType == PlayerEntity.class) {
            this.targetPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(range).setPredicate(entity -> EntityPredicates.rides(mob).test(entity));
        } else {
            this.targetPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(range);
        }
    }

    @Override
    public boolean canStart() {
        if (this.mob.getRandom().nextFloat() >= this.chance) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.target = this.mob.getTarget();
            }
            if (this.targetType == PlayerEntity.class) {
                this.target = this.mob.getWorld().getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.target = this.mob
                        .getWorld()
                        .getOtherEntities(

                        );
            }
            return this.target != null;
        }
    }

    @Override
    public boolean shouldContinue() {
        if (!this.target.isAlive()) {
            return false;
        } else {
            return this.mob.squaredDistanceTo(this.target) > this.range * this.range ? false : this.lookTime > 0;
        }
    }

    @Override
    public void start() {
        this.lookTime = this.getTickCount(40 + this.mob.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.target.isAlive()) {
            double d = this.lookForward ? this.mob.getEyeY() : this.target.getEyeY();
            this.mob.getLookControl().lookAt(this.target.getX(), d, this.target.getZ());
            this.lookTime--;
        }
    }
}