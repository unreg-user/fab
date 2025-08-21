package wta.mixins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityFixer extends LivingEntity implements LivingEntityFixerInterface {
    @Override
    public boolean hasBurdockDownTick() {
        PlayerEntity player=(PlayerEntity) (Object) this;
        return !player.isSpectator();
    }

    @Override
    public boolean hasBurdockEffectTick() {
        return hasBurdockDownTick();
    }

    //костыли
    private PlayerEntityFixer(EntityType<? extends LivingEntity> entityType, World world) {super(entityType, world);}
}
