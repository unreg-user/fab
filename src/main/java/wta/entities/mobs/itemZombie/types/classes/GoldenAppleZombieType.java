package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

public class GoldenAppleZombieType extends AppleZombieType{
    public final float modifierDamage;

    public GoldenAppleZombieType(float movement, float damage) {
        super(movement);

        modifierDamage=damage-1;
    }

    @Override
    public void onStart(ItemZombieEntity entity) {
        super.onStart(entity);

        EntityAttributeInstance attack_damage=entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attack_damage != null) {
            attack_damage.addTemporaryModifier(new EntityAttributeModifier(
                    modifiersName,
                    modifierDamage,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ));
        }
    }

    @Override
    public void onStop(ItemZombieEntity entity, ItemStack stack) {
        super.onStop(entity, stack);

        EntityAttributeInstance attack_damage=entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attack_damage != null) {
            attack_damage.removeModifier(modifiersName);
        }
    }
}
