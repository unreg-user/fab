package wta.mixins.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wta.effects.EffectsInit;
import wta.entities.projectiles.burdock.BurdockEntity;
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

    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    public void tick_(CallbackInfo ci){
        int count=getStuckBurdockCount();
        if (count>0 && random.nextInt(300)==0){
            count--;
            setStuckBurdockCount(count);
            World world=this.getWorld();
            BurdockEntity burdockEntity=BurdockEntity.createEntity(world, this);
            burdockEntity.setCanPickup(false);
            world.spawnEntity(burdockEntity);
        }
        if ((Object) this instanceof LivingEntity livingEntity){
            StatusEffectInstance burdockEffect=livingEntity.getStatusEffect(EffectsInit.burdockinessEntry);
            if (burdockEffect!=null){
                int level=burdockEffect.getAmplifier();
                if (random.nextInt(256)<=level){
                    count++;
                    setStuckBurdockCount(count);
                }
                float damage=0;
                for (int i=0; i<count; i++) {
                    if (random.nextInt(256)<=level) {
                        damage += BurdockEntity.defaultDamage;
                    }
                }
                if (damage>0){
                    BurdockEntity.onlyDamageEntity(this.getWorld(), livingEntity, damage);
                }
            }
        }
    }

    @Inject(
            method = "onDeath",
            at = @At("RETURN")
    )
    public void onDeath_(DamageSource damageSource, CallbackInfo ci) {
        int count=getStuckBurdockCount();
        for (int i=0; i<count; i++) {
            BurdockEntity.summonRandomBurdock(
                    this.getWorld(),
                    this.getPos(),
                    1D,
                    random
            );
        }
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
