package wta.entities.mobs.enderCubeP;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EnderCubeEntity extends HostileEntity {
    //private final TrackedData<Long> target2L1=DataTracker.registerData(EnderCubeEntity.class, TrackedDataHandlerRegistry.LONG);
    //private final TrackedData<Long> target2L2=DataTracker.registerData(EnderCubeEntity.class, TrackedDataHandlerRegistry.LONG); awd aWd
    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(EnderCubeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private LivingEntity target2;

    public EnderCubeEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4F)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.4F)
                .build();
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new AtHeadGoal(this, PlayerEntity.class, true));
    }

    @Override
    protected Box calculateBoundingBox() {
        double scale=this.getScale();
        return new Box(
                this.getX()-scale/2,
                this.getY(),
                this.getZ()-scale/2,
                this.getX()+scale/2,
                this.getY()+scale,
                this.getZ()+scale/2
        );
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (hasNoGravity() && source.isOf(DamageTypes.PLAYER_ATTACK)) {
            return false;
        }

        return super.damage(source, amount);
    }

    @Override
    public boolean isCollidable() {
        return !hasNoGravity();
    }

    @Override
    public boolean hasNoGravity() {
        return getTarget2()!=null;
    }

    boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack=player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        }else{
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            return e > 1.0 - 0.025 / d && player.canSee(this);
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public @Nullable LivingEntity getTarget2() {
        return (LivingEntity) getWorld().getEntityById(dataTracker.get(TARGET_ID));
    }

    public void setTarget2(@Nullable LivingEntity target) {
        if (target != null) {
            dataTracker.set(TARGET_ID, target.getId());
        } else
            dataTracker.set(TARGET_ID, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (getTarget2()!=null){
            long[] uuid=new long[2];
            uuid[0]= getTarget2().getUuid().getMostSignificantBits();
            uuid[1]= getTarget2().getUuid().getLeastSignificantBits();
            nbt.putLongArray("target2", uuid);
        }else{
            nbt.putLongArray("target2", new long[]{0L});
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        long[] uuid=nbt.getLongArray("target2");
        target2=null;
        if (uuid.length==2 && (this.getWorld() instanceof ServerWorld serverWorld)){
            if (serverWorld.getEntity(new UUID(uuid[0], uuid[1])) instanceof  LivingEntity livingEntity){
                setTarget2(livingEntity);
            }
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TARGET_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity targetEntity=getTarget2();
        if (targetEntity!=null){
            this.refreshPositionAndAngles(
                    targetEntity.getX(),
                    targetEntity.getY() + targetEntity.getEyeHeight(targetEntity.getPose()) - this.getScale()/2,
                    targetEntity.getZ(),
                    targetEntity.getYaw(),
                    targetEntity.getPitch()
            );
        }
    }
}
