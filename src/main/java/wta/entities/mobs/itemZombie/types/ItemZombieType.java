package wta.entities.mobs.itemZombie.types;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

import static wta.Fab.MODID;

public class ItemZombieType {
    protected static final Identifier modifiersName=Identifier.of(MODID, "item_zombie");

    public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector){
        goalSelector.add(0, new SwimGoal(entity));
        goalSelector.add(1, new MeleeAttackGoal(entity, 1, true));
        goalSelector.add(2, new EscapeSunlightGoal(entity, 1));
        goalSelector.add(3, new WanderAroundFarGoal(entity, 1));
        goalSelector.add(4, new LookAroundGoal(entity));
        goalSelector.add(4, new LookAtEntityGoal(entity, LivingEntity.class, 32));
    }

    public void initTargetGoals(ItemZombieEntity entity, GoalSelector targetSelector){
        targetSelector.add(0, new RevengeGoal(entity));
        targetSelector.add(1, new ActiveTargetGoal<>(entity, PlayerEntity.class, true));
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch){
        ItemStack stack=entity.getHeadItem();
        if (!stack.isEmpty()) {
            matrices.push();
            matrices.translate(0.0D, -0.25D, 0.0D);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180+headYaw));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPitch));
            float scale=0.7f;
            matrices.scale(scale, scale, scale);
            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack,
                    ModelTransformationMode.NONE,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    0
            );
            matrices.pop();
        }
    }

    public void throwRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch){

    }

    public void onThrowStop(ItemZombieEntity entity){
        playSoundThrow(entity);
    }

    protected void playSoundThrow(ItemZombieEntity entity){
        entity.getWorld().playSound(null, entity.getX(), entity.getEyeY(), entity.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public @Nullable SoundEvent getAmbientSound(ItemZombieEntity entity) {
        return null;
    }

    public SoundEvent getHurtSound(ItemZombieEntity entity, DamageSource source) {
        return SoundEvents.ENTITY_HOSTILE_HURT;
    }

    public SoundEvent getDeathSound(ItemZombieEntity entity) {
        return SoundEvents.ENTITY_HOSTILE_DEATH;
    }

    public boolean hasBurdockEffectTick(ItemZombieEntity entity){
        return true;
    }

    public boolean fireUnderSun(ItemZombieEntity entity){
        return true;
    }

    public void onStop(ItemZombieEntity entity, ItemStack stack){}

    public void onStart(ItemZombieEntity entity){}

    public void onAttacking(ItemZombieEntity entity, LivingEntity target){}

    //functions

    protected static void biRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch){
        ItemStack stack=entity.getHeadItem();
        long uuid=entity.getUuid().getLeastSignificantBits();
        if (!stack.isEmpty()) {
            matrices.push();
            matrices.translate(0.0D, -0.25D, 0.0D);
            if (uuid % 2 == 0){
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            }
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(headYaw));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPitch));
            float scale=0.7f;
            matrices.scale(scale, scale, scale);
            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    stack,
                    ModelTransformationMode.NONE,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    0
            );
            matrices.pop();
        }
    }
}
