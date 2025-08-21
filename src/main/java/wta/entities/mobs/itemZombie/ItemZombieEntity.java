package wta.entities.mobs.itemZombie;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wta.entities.mobs.MobsInit;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.ItemZombieTypeRegistry;
import wta.entities.mobs.itemZombie.types.classes.*;
import wta.entities.projectiles.ProjectilesInit;
import wta.items.ItemsInit;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

public class ItemZombieEntity extends HostileEntity implements LivingEntityFixerInterface{
    public static ItemZombieTypeRegistry types;
    private static final TrackedData<ItemStack> HEAD_ITEM=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Float> SET_HEAD_ITEM_ANIMATION_PROGRESS=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> THROW_PROGRESS=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> HAS_ULTA=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final float step_set_head_animation=0.2F;
    public static final float step_throw=0.075F;

    public ItemZombieType itemType=ItemZombieTypeRegistry.defaultType;

    public ItemZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        if (types==null){
            loadTypes();
        }
        setHeadItem(getHeadItem());
    }

    public ItemZombieEntity(World world){
        this(MobsInit.itemZombieE, world);
    }

    public static DefaultAttributeContainer createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0F)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 4.0F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0F)
                .add(EntityAttributes.GENERIC_ARMOR, 0.1F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D)
                .build();
    }

    public static ItemZombieEntity newForRegistry(EntityType<? extends HostileEntity> entityType, World world){
        if (types==null){
            loadTypes();
        }
        return new ItemZombieEntity(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder .add(HEAD_ITEM, getDefaultItemStack())
                .add(SET_HEAD_ITEM_ANIMATION_PROGRESS, -1F)
                .add(HAS_ULTA, true)
                .add(THROW_PROGRESS, -1F);
    }

    public float get_set_head_animation_progress(){
        return dataTracker.get(SET_HEAD_ITEM_ANIMATION_PROGRESS);
    }

    public void set_set_head_animation_progress(float value){
        dataTracker.set(SET_HEAD_ITEM_ANIMATION_PROGRESS, value);
    }

    public boolean get_has_ulta(){
        return  dataTracker.get(HAS_ULTA);
    }

    public void set_has_ulta(boolean value){
        dataTracker.set(HAS_ULTA, value);
    }

    public float get_throw_progress(){
        return dataTracker.get(THROW_PROGRESS);
    }

    public void set_throw_progress(float value){
        dataTracker.set(THROW_PROGRESS, value);
    }

    public ItemStack getHeadItem() {
        return dataTracker.get(HEAD_ITEM);
    }

    public void setHeadItem(ItemStack stack) {
        if (itemType!=null){
            itemType.onStop(this, stack);
        }
        dataTracker.set(HEAD_ITEM, stack);
        initHeadItem(stack);
    }

    public void setHeadItemDrop(ItemStack stack, Vec3d motion) {
        World world=this.getWorld();
        ItemEntity itemEntity=this.getDropedItemEntityAtHead(getHeadItem(), world);
        itemEntity.setVelocity(motion);
        world.spawnEntity(itemEntity);
        this.setHeadItem(stack);
    }

    private void initHeadItem(ItemStack stack){
        itemType=types.get(stack.getItem());
        goalSelector.clear(goal -> true);
        targetSelector.clear(goal -> true);
        itemType.initGoals(this, goalSelector, targetSelector);
        itemType.onStart(this);
    }

    public ItemZombieType getItemType(){
        return types.get(getHeadItem().getItem());
    }

    private static ItemStack getDefaultItemStack(){
        return new ItemStack(ItemsInit.undefinedItem);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return itemType.getAmbientSound(this);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return itemType.getHurtSound(this, source);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return itemType.getDeathSound(this);
    }

    @Override
    public boolean hasBurdockEffectTick() {
        return itemType.hasBurdockEffectTick(this);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        ItemStack head_item=getHeadItem();
        if (head_item!=null && head_item.isStackable()){
            nbt.put("head_item", head_item.encode(this.getRegistryManager()));
        }else{
            nbt.put("head_item", (new ItemStack(Items.AIR)).encodeAllowEmpty(this.getRegistryManager()));
        }
        nbt.putFloat("set_head_animation_progress", this.get_set_head_animation_progress());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        NbtCompound head_item=(NbtCompound) nbt.get("head_item");
        if (head_item!=null){
            setHeadItem(ItemStack.fromNbtOrEmpty(this.getRegistryManager(), head_item));
        }else{
            setHeadItem(getDefaultItemStack());
        }
        this.set_set_head_animation_progress(nbt.getFloat("set_head_animation_progress"));
    }

    @Override
    public void tick() {
        super.tick();

        float progress=this.get_set_head_animation_progress();
        if (progress > -0.5F){
            progress += step_set_head_animation;
            if (progress > 1F){
                ItemStack handStack=this.getEquippedStack(EquipmentSlot.MAINHAND);
                if (handStack != null && !handStack.isEmpty()){
                    float rad=(float) ((bodyYaw/180F-0.5F)*Math.PI);
                    this.setHeadItemDrop(handStack, new Vec3d(
                            3*Math.cos(rad),
                            0,
                            3*Math.sin(rad)
                    ));
                    this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
            if (progress > 2F){
                progress = -1F;
            }
            this.set_set_head_animation_progress(progress);
        }
        progress=this.get_throw_progress();
        if (progress > -0.5F){
            progress += step_throw;
            if (progress > 0.6F){
                itemType.onThrowStop(this);
                progress = -1F;
            }
            this.set_throw_progress(progress);
        }else{
            this.set_throw_progress(0F);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        boolean bl = this.isAffectedByDaylight();
        if (bl) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
            if (!itemStack.isEmpty()) {
                if (itemStack.isDamageable()) {
                    Item item = itemStack.getItem();
                    itemStack.setDamage(itemStack.getDamage() + this.random.nextInt(2));
                    if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                        this.sendEquipmentBreakStatus(item, EquipmentSlot.HEAD);
                        this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }
                bl = false;
            }
            if (bl) {
                if (itemType.fireUnderSun(this)) {
                    this.setOnFireFor(8.0F);
                }
            }
        }
    }

    public boolean pickupForHead(ItemEntity itemEntity){
        ItemStack stack=itemEntity.getStack();
        if (stack!=null && this.get_set_head_animation_progress() < -0.5F){
            itemEntity.discard();
            World world=this.getWorld();
            ItemStack oldStack=this.getEquippedStack(EquipmentSlot.MAINHAND);
            ItemEntity oldItemEntity=this.getDropedItemEntity(oldStack, world);
            world.spawnEntity(oldItemEntity);
            this.setStackInHand(Hand.MAIN_HAND, stack);
            this.set_set_head_animation_progress(0F);
            return true;
        }
        return false;
    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);

        if (target instanceof LivingEntity livingEntity){
            itemType.onAttacking(this, livingEntity);
        }
    }

    //functions

    private ItemEntity getDropedItemEntity(ItemStack stack, World world){
        return new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack);
    }
    private ItemEntity getDropedItemEntityAtHead(ItemStack stack, World world){
        return new ItemEntity(world, this.getX(), this.getEyeY(), this.getZ(), stack);
    }

    //loaders

    public static void loadTypes() {
        types=new ItemZombieTypeRegistry();
        types.register(Items.APPLE, new AppleZombieType(2));
        types.register(Items.GOLDEN_APPLE, new GoldenAppleZombieType(2, 3));
        types.register(Items.ENCHANTED_GOLDEN_APPLE, new EnchantedGoldenAppleZombieType(2, 3));
        types.register(Items.STICK, new StickZombieType());
        types.register(ProjectilesInit.burdockI, new BurdockZombieType());
    }
}