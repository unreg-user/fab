package wta.entities.projectiles.burdock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import wta.DamageTypesMod;
import wta.blocks.BlocksInit;
import wta.effects.EffectsInit;
import wta.entities.projectiles.ProjectilesInit;
import wta.mixins.mixin.PersistentProjectileEntityFixer;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;
import wta.mixins.mixinInterfaces.PersistentProjectileEntityFixerInterface;

import java.util.List;

public class BurdockEntity extends PersistentProjectileEntity {
    private static final TrackedData<Boolean> canPickup=DataTracker.registerData(BurdockEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> breakGroupLevel=DataTracker.registerData(BurdockEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> thisDamage=DataTracker.registerData(BurdockEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> thisCollection=DataTracker.registerData(BurdockEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int min_tick_to_physis=1;

    public static final float defaultDamage=0.1F;
    public static final int defaultCollection=4;

    public BurdockEntity(EntityType<? extends BurdockEntity> entityType, World world) {
        super(entityType, world);
    }

    public static BurdockEntity createEntity(World world){
        return new BurdockEntity(ProjectilesInit.burdockE, world);
    }
    public static BurdockEntity createEntity(World world, Vec3d pos, Vec3d motion){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        burdock.setVelocity(motion);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Vec3d pos){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Vec3d pos, Entity owner){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        burdock.setOwner(owner);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Entity owner){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(owner.getEyePos());
        burdock.setOwner(owner);
        return burdock;
    }

    @Override
    public ItemStack getDefaultItemStack() {
        return new ItemStack(ProjectilesInit.burdockI);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {}

    @Override
    protected boolean canHit(Entity entity) {
        return (age>min_tick_to_physis || getBreakGroupLevel()==0) &&
                (getOwner()!=entity || (age>10 && getCanPickup())) &&
                !((entity instanceof PlayerEntity player) && player.isSpectator());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity=entityHitResult.getEntity();
        World world=this.getWorld();
        float thisDamage=this.getThisDamage();
        int thisCollection=this.getThisCollection();
        if (entity instanceof LivingEntity livingEntity) {
            int breakLevel=getBreakGroupLevel();
            if (breakLevel>0){
                int breakLevel_=breakLevel;
                breakLevel--;
                for (int i=0; i<thisCollection; i++) {
                    Vec3d vec3dI=getRandomVector(2, 2, random);
                    BurdockEntity burdockEntity=createEntity(this.getWorld(), this.getPos());
                    burdockEntity.setOwner(this.getOwner());
                    burdockEntity.setBreakGroupLevel(breakLevel);
                    burdockEntity.setVelocity(vec3dI);
                    burdockEntity.setThisCollection(thisCollection);
                    burdockEntity.setThisDamage(thisDamage);
                    world.spawnEntity(burdockEntity);
                }
                onlyDamageEntity(this.getWorld(), livingEntity, (LivingEntity) this.getOwner(), thisDamage*breakLevel_);
                entityEffect(livingEntity, random, 0, breakLevel_, 50*(4+random.nextInt(breakLevel_)));
            }else if (entity instanceof PlayerEntity player && random.nextInt(5) == 0 && this.getVelocity().length()>0.2) {
                if (player.getInventory().insertStack(this.asItemStack())) {
                    player.sendPickup(this, 1);
                }
            }else{
                damageEntity(this.getWorld(), livingEntity, (LivingEntity) this.getOwner(), thisDamage);
            }
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        World world = this.getWorld();
        BlockPos pos=blockHitResult.getBlockPos();
        BlockPos posUp=pos.up();
        BlockState state=world.getBlockState(pos);
        BlockState stateUp=world.getBlockState(posUp);
        Direction direction=blockHitResult.getSide();
        if (direction==Direction.UP){
            if (stateUp.getBlock() instanceof PlantBlock && !stateUp.isOf(BlocksInit.burdockSapling)){
                stateUp=Blocks.AIR.getDefaultState();
                world.breakBlock(posUp, true, this);
            }
            if (state.getBlock()==Blocks.FARMLAND){
                state=Blocks.DIRT.getDefaultState();
                world.setBlockState(pos, state, 3);
            }
            if (state.isIn(BlockTags.DIRT) && stateUp.isIn(BlockTags.AIR)){
                this.discard();
                world.setBlockState(posUp, BlocksInit.burdockSapling.getDefaultState(), 3);
            }
        }
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {}

    @Override
    protected void age() {
        life++;
        if (life >= 1200) {
            World world=this.getWorld();
            ItemEntity itemEntity=new ItemEntity(
                    world,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    getDefaultItemStack()
            );
            world.spawnEntity(itemEntity);
            this.discard();
        }else if (!getCanPickup() && life >= 300){
            setCanPickup(true);
        }
    }

    @Override
    public void tick() {
        if (((Object) this) instanceof PersistentProjectileEntityFixerInterface projectileFixed){
            projectileFixed.superTick();
        }

        World world=this.getWorld();
        Vec3d vec3d2;
        VoxelShape voxelShape;
        boolean bl = this.isNoClip();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            double d = vec3d.horizontalLength();
            this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
            this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        if (!(blockState.isAir() || bl || (voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos)).isEmpty())) {
            vec3d2 = this.getPos();
            for (Box box : voxelShape.getBoundingBoxes()) {
                if (!box.offset(blockPos).contains(vec3d2)) continue;
                this.inGround = true;
                break;
            }
        }
        if (this.shake > 0) {
            --this.shake;
        }
        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }
        if (this.inGround && !bl) {
            if (this.inBlockState != blockState && this.shouldFall()) {
                this.fall();
            } else if (!this.getWorld().isClient) {
                this.age();
            }
            ++this.inGroundTime;
            List<Entity> entities=world.getOtherEntities(
                    this,
                    this.calculateBoundingBox(),
                    (en) -> en instanceof LivingEntity
            );
            if (entities.isEmpty()){
                return;
            }
            LivingEntity entity=(LivingEntity) entities.getFirst();
            if (canHit(entity)){
                this.onEntityHit(new EntityHitResult(entity));
            }
            return;
        }
        this.inGroundTime = 0;
        Vec3d vec3d3 = this.getPos();
        vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d2 = hitResult.getPos();
        }
        while (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }
            if (hitResult != null && !bl) {
                ProjectileDeflection projectileDeflection = this.hitOrDeflect(hitResult);
                this.velocityDirty = true;
                if (projectileDeflection != ProjectileDeflection.NONE) break;
            }
            if (entityHitResult == null || this.getPierceLevel() <= 0) break;
            hitResult = null;
        }
        vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        if (this.isCritical()) {
            for (int i = 0; i < 4; ++i) {
                this.getWorld().addParticle(ParticleTypes.CRIT, this.getX() + e * (double)i / 4.0, this.getY() + f * (double)i / 4.0, this.getZ() + g * (double)i / 4.0, -e, -f + 0.2, -g);
            }
        }
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalLength();
        if (bl) {
            this.setYaw((float)(MathHelper.atan2(-e, -g) * 57.2957763671875));
        } else {
            this.setYaw((float)(MathHelper.atan2(e, g) * 57.2957763671875));
        }
        this.setPitch((float)(MathHelper.atan2(f, l) * 57.2957763671875));
        this.setPitch(PersistentProjectileEntity.updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(PersistentProjectileEntity.updateRotation(this.prevYaw, this.getYaw()));
        float m = 0.99f;
        if (this.isTouchingWater()) {
            for (int n = 0; n < 4; ++n) {
                float o = 0.25f;
                this.getWorld().addParticle(ParticleTypes.BUBBLE, h - e * 0.25, j - f * 0.25, k - g * 0.25, e, f, g);
            }
            m = this.getDragInWater();
        }
        this.setVelocity(vec3d.multiply(m));
        if (!bl) {
            this.applyGravity();
        }
        this.setPosition(h, j, k);
        //if (age>min_tick_to_physis && getBreakGroupLevel()>0){
            this.checkBlockCollision();
        //}
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        setCanPickup(nbt.getBoolean("can_collision"));
        setBreakGroupLevel(nbt.getInt("break_group_level"));
        setThisDamage(nbt.getFloat("this_damage"));
        setThisCollection(nbt.getInt("this_collection"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("can_collision", getCanPickup());
        nbt.putInt("break_group_level", getBreakGroupLevel());
        nbt.putFloat("this_damage", getThisDamage());
        nbt.putInt("this_collection", getThisCollection());
    }

    public boolean getCanPickup(){
        return dataTracker.get(canPickup);
    }

    public void setCanPickup(boolean value){
        dataTracker.set(canPickup, value);
    }

    public int getBreakGroupLevel(){
        return dataTracker.get(breakGroupLevel);
    }

    public void setBreakGroupLevel(int value){
        dataTracker.set(breakGroupLevel, value);
    }

    public float getThisDamage(){
        return dataTracker.get(thisDamage);
    }

    public void setThisDamage(float value){
        dataTracker.set(thisDamage, value);
    }

    public int getThisCollection(){
        return dataTracker.get(thisCollection);
    }

    public void setThisCollection(int value){
        dataTracker.set(thisCollection, value);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(canPickup, true);
        builder.add(breakGroupLevel, 0);
        builder.add(thisDamage, defaultDamage);
        builder.add(thisCollection, defaultCollection);
    }

    public static void onlyDamageEntity(World world, LivingEntity entity, float damage){
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getOrThrow(DamageTypesMod.burdock)
        );
        entity.damage(damageSource, damage);
    }
    public static void onlyDamageEntity(World world, LivingEntity entity, LivingEntity owner, float damage){
        DamageSource damageSource = new DamageSource(
              world.getRegistryManager()
                    .getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)
                    .getOrThrow(DamageTypesMod.burdock),
              owner
        );
        entity.damage(damageSource, damage);
    }

    public static void damageEntityEffect(World world, LivingEntity entity, float damage, Random random, int min, int max){
        damageEntity(world, entity, damage, random);
        entityEffect(entity, random, 0, 4, 200);
    }
    public static void damageEntityEffect(World world, LivingEntity entity, Random random){
        BurdockEntity.damageEntityEffect(world, entity, defaultDamage, random, 0, 4);
    }

    public static void entityEffect(LivingEntity entity, Random random, int min, int max, int duraction){
        if (random.nextBoolean()){
            entity.addStatusEffect(new StatusEffectInstance(
                    EffectsInit.burdockinessEntry,
                    200,
                    random.nextInt(max-min+1)+min
            ));
        }
    }

    public static void damageEntity(World world, LivingEntity entity, LivingEntity owner, float damage){
        if (entity instanceof LivingEntityFixerInterface entityFixed) {
            entityFixed.setStuckBurdockCount(entityFixed.getStuckBurdockCount()+1);
            DamageSource damageSource = new DamageSource(
                  world.getRegistryManager()
                        .getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getOrThrow(DamageTypesMod.burdock),
                  owner
            );
            entity.damage(damageSource, damage);
        }
    }
    public static void damageEntity(World world, LivingEntity entity, float damage, Random random){
        if (entity instanceof LivingEntityFixerInterface entityFixed) {
            entityFixed.setStuckBurdockCount(entityFixed.getStuckBurdockCount()+1);
            DamageSource damageSource = new DamageSource(
                    world.getRegistryManager()
                            .getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)
                            .getOrThrow(DamageTypesMod.burdock)
            );
            entity.damage(damageSource, damage);
        }
    }
    public static void damageEntity(World world, LivingEntity entity, Random random){
        BurdockEntity.damageEntity(world, entity, defaultDamage, random);
    }

    public static void summonRandomBurdock(World world, Vec3d pos, double length, Random random, double yRandom){
        /*/double vecYR=random.nextDouble()*yRandom*Math.PI;
        double vecY=length*Math.sin(vecYR);
        double lengthXZ=Math.cos(vecYR);
        double vecXZR=random.nextDouble()*2D*Math.PI;
        double vecX=lengthXZ*Math.cos(vecXZR);
        double vecZ=lengthXZ*Math.sin(vecXZR);/*/

        BurdockEntity burdockEntity=BurdockEntity.createEntity(world, pos);
        Vec3d vec3d=getRandomVector(length, yRandom, random);
        burdockEntity.setVelocity(vec3d);
        world.spawnEntity(burdockEntity);
    }
    public static void summonRandomBurdock(World world, Vec3d pos, double length, Random random){
        summonRandomBurdock(world, pos, length, random, 0.5D);
    }
    public static void summonRandomBurdockR(World world, Vec3d pos, double length, Random random, double vecYR){
        BurdockEntity burdockEntity=BurdockEntity.createEntity(world, pos);
        Vec3d vec3d=getRandomVectorR(length, vecYR, random);
        burdockEntity.setVelocity(vec3d);
        world.spawnEntity(burdockEntity);
    }

    public static Vec3d getRandomVector(double length, double yRandom, Random random){
        double vecYR=random.nextDouble()*yRandom*Math.PI;
        double vecY=length*Math.sin(vecYR);
        double lengthXZ=Math.cos(vecYR);
        double vecXZR=random.nextDouble()*2D*Math.PI;
        double vecX=lengthXZ*Math.cos(vecXZR);
        double vecZ=lengthXZ*Math.sin(vecXZR);
        return new Vec3d(vecX, vecY, vecZ);
    }
    public static Vec3d getRandomVectorR(double length, double vecYR, Random random){
        double vecY=length*Math.sin(vecYR);
        double lengthXZ=Math.cos(vecYR);
        double vecXZR=random.nextDouble()*2D*Math.PI;
        double vecX=lengthXZ*Math.cos(vecXZR);
        double vecZ=lengthXZ*Math.sin(vecXZR);
        return new Vec3d(vecX, vecY, vecZ);
    }
}