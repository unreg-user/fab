package wta.mixins.mixinInterfaces;

import net.minecraft.util.hit.BlockHitResult;

public interface PersistentProjectileEntityFixerInterface {
    void superTick();
    void superOnBlockHit(BlockHitResult blockHitResult);
}
