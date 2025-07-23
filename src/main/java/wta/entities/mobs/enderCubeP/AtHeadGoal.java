package wta.entities.mobs.enderCubeP;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class AtHeadGoal extends ActiveTargetGoal<PlayerEntity> {
    private final EnderCubeEntity endercube;

    public AtHeadGoal(MobEntity mob, Class<PlayerEntity> targetClass, boolean checkVisibility) { //this, PlayerEntity.class, true
        super(mob, targetClass, checkVisibility);
        this.endercube=(EnderCubeEntity) mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        this.findClosestTarget();
        return !canStop();
    }

    @Override
    protected void findClosestTarget() {
        LivingEntity target2=endercube.getTarget2();
        if (target2!=null && target2 instanceof PlayerEntity playerEntity){
            targetEntity=target2;
        }else{
            targetEntity=mob
                    .getWorld()
                    .getClosestEntity(
                            mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), endercube::isPlayerStaring),
                            targetPredicate,
                            mob,
                            mob.getX(),
                            mob.getEyeY(),
                            mob.getZ()
                    );
        }
    }

    @Override
    public void stop() {
        endercube.setTarget2(null);
    }

    @Override
    public void start() {
        target=targetEntity;
        endercube.setTarget2(targetEntity);
    }

    @Override
    public void tick() {
        if (canStop()) return;
        if (!(targetEntity instanceof PlayerEntity playerEntity)) return;
        /*/endercube.refreshPositionAndAngles(
                targetEntity.getX(),
                targetEntity.getY() + targetEntity.getEyeHeight(targetEntity.getPose()) - endercube.getScale()/2,
                targetEntity.getZ(),
                targetEntity.getYaw(),
                targetEntity.getPitch()
        );/*/

        /*/endercube.setAngles(
                targetEntity.getYaw(),
                targetEntity.getPitch()
        );/*/
        ItemStack headStack=playerEntity.getInventory().armor.get(3);
        if (!headStack.isEmpty()){
            playerEntity.getInventory().armor.set(3, ItemStack.EMPTY);
            ItemEntity drop=new ItemEntity(
                    targetEntity.getWorld(),
                    targetEntity.getX(),
                    targetEntity.getY(),
                    targetEntity.getZ(),
                    headStack
            );
            targetEntity.getWorld().spawnEntity(drop);
        }
    }

    @Override
    public boolean canStop() {
        if (targetEntity==null || !(targetEntity instanceof PlayerEntity playerEntity)) return true;
        return targetEntity.isRemoved() || playerEntity.isInCreativeMode() || playerEntity.isSpectator();
    }

    @Override
    protected boolean canTrack(@Nullable LivingEntity target, TargetPredicate targetPredicate) {
        return !canStop();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public boolean shouldContinue() {
        boolean bool=canStop();
        if (bool){
            stop();
        }
        return !bool;
    }
}
