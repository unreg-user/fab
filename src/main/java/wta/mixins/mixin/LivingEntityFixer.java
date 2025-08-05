package wta.mixins.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFixer extends Entity implements LivingEntityFixerInterface {
    @Unique
    private static final TrackedData<Integer> STUCK_BURDOCK_COUNT=DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(
            method = "initDataTracker",
            at = @At("RETURN")
    )
    protected void initDataTracker(DataTracker.Builder builder, CallbackInfo ci){
        builder.add(STUCK_BURDOCK_COUNT, 0);
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("RETURN")
    )
    public void readNBT(NbtCompound nbt, CallbackInfo ci) {
        setStuckBurdockCount(nbt.getInt("stuck_burdock_count"));
    }

    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("RETURN")
    )
    public void writeNBT(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("stuck_burdock_count", getStuckBurdockCount());
    }

    @Override
    public int getStuckBurdockCount() {
        return dataTracker.get(STUCK_BURDOCK_COUNT);
    }

    @Override
    public void setStuckBurdockCount(int count) {
        dataTracker.set(STUCK_BURDOCK_COUNT, count);
    }

    //костыли
    public LivingEntityFixer(EntityType<?> type, World world) {
        super(type, world);
    }
}
