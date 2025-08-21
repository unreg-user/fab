package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

public class EnchantedGoldenAppleZombieType extends GoldenAppleZombieType {
    public EnchantedGoldenAppleZombieType(float movement, float damage) {
        super(movement, damage);
    }

    @Override
    public void onAttacking(ItemZombieEntity entity, LivingEntity target) {
        StatusEffectInstance instance=new StatusEffectInstance(
                StatusEffects.DARKNESS,
                200,
                10
        );
        StatusEffectInstance instance2=new StatusEffectInstance(
                StatusEffects.WEAKNESS,
                100,
                10
        );
        target.addStatusEffect(instance);
        target.addStatusEffect(instance2);
    }
}
