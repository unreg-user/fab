package wta.mixins.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import wta.mixins.mixinInterfaces.PersistentProjectileEntityFixerInterface;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityFixer extends ProjectileEntity implements PersistentProjectileEntityFixerInterface {
    @Override
    public void superTick() {
        super.tick();
    }

    @Override
    public void superOnBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    public PersistentProjectileEntityFixer(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}
