package wta.entities.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public abstract class FindItemsGoal extends Goal {
    protected final MobEntity entity;
    protected final double range;
    protected final int rangeToItem;
    protected final Predicate<ItemEntity> predicate;
    @Nullable
    protected ItemEntity target;

    public FindItemsGoal(MobEntity entity, double range, int rangeToItem, Predicate<ItemEntity> predicate){
        this.entity=entity;
        this.range=range;
        this.rangeToItem=rangeToItem;
        this.predicate=predicate;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        findTarget();
        return canNext();
    }

    protected void findTarget(){
        World world=entity.getWorld();
        List<Entity> targets=world.getOtherEntities(
              entity,
              entity.getBoundingBox().expand(range),
              e  -> e instanceof ItemEntity itemEntity && predicate.test(itemEntity) && entity.canSee(e) && entity.getNavigation().findPathTo(e, rangeToItem)!=null
        );
        this.target=(ItemEntity) targets.stream()
              .min(Comparator.comparing(entity::distanceTo))
              .orElse(null);
    }

    @Override
    public void tick() {
        if (target != null) {
            entity.getLookControl().lookAt(target, 30.0F, 30.0F);
            entity.getNavigation().startMovingTo(target, 1);
            if (entity.distanceTo(target) < 2F){
                this.onFinish();
            }
        }
    }

    abstract protected void onFinish();

    @Override
    public boolean shouldContinue() {
        return canNext();
    }

    protected boolean canNext(){
        if (target!=null && target.isAlive() && entity.canSee(target) && entity.getNavigation().findPathTo(target, rangeToItem)!=null){
            return true;
        }
        target=null;
        return false;
    }

    @Override
    public void stop() {
        target=null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
}
