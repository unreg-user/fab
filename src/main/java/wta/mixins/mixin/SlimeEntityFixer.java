package wta.mixins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityFixer extends MobEntity implements Monster, LivingEntityFixerInterface {
    @Override
    public boolean hasBurdockDownTick() {
        return false;
    }

    //костыли
    private SlimeEntityFixer(EntityType<? extends MobEntity> entityType, World world) {super(entityType, world);}
}
