package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.random.Random;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

public class EnchantedGoldenAppleZombieType extends GoldenAppleZombieType {
    public EnchantedGoldenAppleZombieType(float movement, float damage) {
        super(movement, damage);
    }

    @Override
    public void onAttacking(ItemZombieEntity entity, LivingEntity target) {
        enchantAttackEffects(entity, target);
    }
}
