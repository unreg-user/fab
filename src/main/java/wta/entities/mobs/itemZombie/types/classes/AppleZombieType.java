package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
;

public class AppleZombieType extends ItemZombieType {
    private final float modifierAttack;
    private final float modifierMovement;

    public AppleZombieType(float attack, float movement){
        modifierAttack=attack;
        modifierMovement=movement;
    }

    @Override
    public void onStart(ItemZombieEntity entity) {
        EntityAttributeInstance attack_speed=entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        if (attack_speed != null) {
            attack_speed.addTemporaryModifier(new EntityAttributeModifier(
                    modifiersName,
                    modifierAttack,
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
}
