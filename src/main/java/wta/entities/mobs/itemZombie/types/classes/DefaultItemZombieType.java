package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.classes.goals.FindItemsForHeadGoal;

public class DefaultItemZombieType extends ItemZombieType {
    @Override
    public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector, GoalSelector targetSelector) {
        goalSelector.add(0, new SwimGoal(entity));
        goalSelector.add(1, new MeleeAttackGoal(entity, 1, true));
        goalSelector.add(2, new FindItemsForHeadGoal(entity, 16, 0, ie -> ItemZombieEntity.types.isUndefault(ie.getStack().getItem())));
        goalSelector.add(3, new WanderAroundFarGoal(entity, 1));
        goalSelector.add(4, new LookAroundGoal(entity));
        goalSelector.add(4, new LookAtEntityGoal(entity, LivingEntity.class, 32));
        targetSelector.add(0, new ActiveTargetGoal<>(entity, PlayerEntity.class, true));
    }
}
