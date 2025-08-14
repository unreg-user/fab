package wta.entities.mobs.itemZombie;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wta.entities.mobs.MobsInit;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.ItemZombieTypeRegistry;
import wta.entities.mobs.itemZombie.types.classes.AppleZombieType;
import wta.items.ItemsInit;

public class ItemZombieEntity extends HostileEntity {
    public static final ItemZombieTypeRegistry types=new ItemZombieTypeRegistry();
    private static final TrackedData<ItemStack> HEAD_ITEM=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Float> SET_HEAD_ITEM_ANIMATION_PROGRESS=DataTracker.registerData(ItemZombieEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final float step_animation=0.1F;

    public ItemZombieType itemType=ItemZombieTypeRegistry.defaultType;

    public ItemZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
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
        return new ItemZombieEntity(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(HEAD_ITEM, getDefaultItemStack());
        builder.add(SET_HEAD_ITEM_ANIMATION_PROGRESS, -1F);
    }

    public float get_set_head_animation_progress(){
        return dataTracker.get(SET_HEAD_ITEM_ANIMATION_PROGRESS);
    }

    public void set_set_head_animation_progress(float value){
        dataTracker.set(SET_HEAD_ITEM_ANIMATION_PROGRESS, value);
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

    public void setHeadItemDrop(ItemStack stack) {
        World world=this.getWorld();
        ItemEntity itemEntity=this.getDropedItemEntity(getHeadItem(), world);
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
            progress += step_animation;
            if (progress > 1F && progress < 1.2F){
                ItemStack handStack=this.getEquippedStack(EquipmentSlot.MAINHAND);
                if (handStack != null && !handStack.isEmpty()){
                    this.setHeadItemDrop(handStack);
                    this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
            if (progress > 2F){
                progress = -1F;
            }
            this.set_set_head_animation_progress(progress);
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

    private ItemEntity getDropedItemEntity(ItemStack stack, World world){
        return new ItemEntity(world, this.getX(), this.getY(), this.getZ(), stack);
    }

    static {
        types.register(Items.APPLE, new AppleZombieType(5F, 1.1F));
    }
}