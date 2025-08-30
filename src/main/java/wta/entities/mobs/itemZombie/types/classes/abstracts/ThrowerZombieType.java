package wta.entities.mobs.itemZombie.types.classes.abstracts;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.classes.goals.ThrowGoal;

public abstract class ThrowerZombieType extends ItemZombieType {
	int attackIntervalTicks;

	public ThrowerZombieType(int attackIntervalTicks){
		this.attackIntervalTicks=attackIntervalTicks;
	}

	@Override
	public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector) {
		goalSelector.add(0, new SwimGoal(entity));
		goalSelector.add(1, new EscapeSunlightGoal(entity, 1));
		goalSelector.add(2, new ThrowGoal(entity, attackIntervalTicks, 16));
		goalSelector.add(3, new WanderAroundFarGoal(entity, 1));
		goalSelector.add(4, new LookAroundGoal(entity));
		goalSelector.add(4, new LookAtEntityGoal(entity, LivingEntity.class, 32));
	}
}
