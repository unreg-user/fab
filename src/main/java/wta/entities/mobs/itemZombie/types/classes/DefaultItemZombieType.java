package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.classes.goals.FindItemsForHeadGoal;

public class DefaultItemZombieType extends ItemZombieType {
    @Override
    public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector) {
        goalSelector.add(0, new SwimGoal(entity));
        goalSelector.add(1, new MeleeAttackGoal(entity, 1, true));
        goalSelector.add(2, new EscapeSunlightGoal(entity, 1));
        goalSelector.add(3, new FindItemsForHeadGoal(entity, 16, 0));
        goalSelector.add(4, new WanderAroundFarGoal(entity, 1));
        goalSelector.add(5, new LookAroundGoal(entity));
        goalSelector.add(5, new LookAtEntityGoal(entity, LivingEntity.class, 32));
    }
}
