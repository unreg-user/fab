package wta.entities.mobs.itemZombie.types.classes.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

import java.util.EnumSet;

public class ThrowGoal extends Goal {
	private final ItemZombieEntity entity;
	private LivingEntity target;
	private final int attackIntervalTicks;
	private final int backwardRange;
	private int cooldown=0;
	private boolean movingToLeft=true;

	public ThrowGoal(ItemZombieEntity entity, int attackIntervalTicks, int backwardRange){
		this.entity=entity;
		this.attackIntervalTicks=attackIntervalTicks;
		this.backwardRange=backwardRange;
		this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return has_target() && entity.get_throw_progress() < -0.5F;
	}

	@Override
	public boolean shouldContinue() {
		return has_target();
	}

	@Override
	public void tick() {
		entity.getLookControl().lookAt(target, 30.0F, 30.0F);
		boolean backward=target.getPos().subtract(entity.getPos()).length()<backwardRange;
		entity.getMoveControl().strafeTo(backward ? -1F : 0.75F, movingToLeft ? -1F : 1F);
		if (entity.get_throw_progress() > -0.5F){
			return;
		}if (cooldown>0){
			cooldown--;
		}else{
			if (entity.get_has_ulta()){
				entity.set_is_ulta(true);
			}
			entity.set_throw_progress(0);
			cooldown = attackIntervalTicks;
			if (entity.getRandom().nextInt(3)==0){
				movingToLeft = !movingToLeft;
			}
		}
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	private boolean has_target(){
		target=entity.getTarget();
		return target!=null && entity.canSee(target);
	}
}
