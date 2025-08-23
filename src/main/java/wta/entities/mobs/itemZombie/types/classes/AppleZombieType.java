package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import wta.entities.goals.MeleeAttackWithConfigurableCooldownGoal;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
;

public class AppleZombieType extends ItemZombieType {
    private final float modifierMovement;

    public AppleZombieType(float movement){
        modifierMovement=movement-1F;
    }

    @Override
    public void onStart(ItemZombieEntity entity) {
        EntityAttributeInstance attack_speed=entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (attack_speed != null) {
            attack_speed.addTemporaryModifier(new EntityAttributeModifier(
                    modifiersName,
                    19,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ));
        }
        EntityAttributeInstance movement_speed=entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (movement_speed != null) {
            movement_speed.addTemporaryModifier(new EntityAttributeModifier(
                    modifiersName,
                    modifierMovement,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ));
        }
    }

    @Override
    public void onStop(ItemZombieEntity entity, ItemStack stack) {
        EntityAttributeInstance attack_speed=entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (attack_speed != null) {
            attack_speed.removeModifier(modifiersName);
        }
        EntityAttributeInstance movement_speed=entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (movement_speed != null) {
            movement_speed.removeModifier(modifiersName);
        }
    }

    @Override
    public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector) {
        goalSelector.add(0, new SwimGoal(entity));
        goalSelector.add(1, new MeleeAttackWithConfigurableCooldownGoal(entity, 1, true, 0));
        goalSelector.add(2, new EscapeSunlightGoal(entity, 1));
        goalSelector.add(3, new WanderAroundFarGoal(entity, 1));
        goalSelector.add(4, new LookAroundGoal(entity));
        goalSelector.add(4, new LookAtEntityGoal(entity, LivingEntity.class, 32));
    }
}
