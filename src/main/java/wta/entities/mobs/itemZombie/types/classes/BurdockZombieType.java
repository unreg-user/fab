package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
import wta.entities.mobs.itemZombie.types.classes.goals.ThrowGoal;
import wta.entities.projectiles.burdock.BurdockEntity;

public class BurdockZombieType extends ItemZombieType {
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        EntityRenderDispatcher dispatcher=MinecraftClient.getInstance().getEntityRenderDispatcher();
        BurdockEntity burdock=BurdockEntity.createEntity(entity.getWorld());
        burdock.setPitch(entity.getPitch());
        burdock.setHeadYaw(entity.getHeadYaw());
        float scale=1.4f;
        matrices.scale(scale, scale, scale);
        dispatcher.render(burdock, 0.0F, -0.4F, 0.0F, headYaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }

    @Override
    public void throwRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        EntityRenderDispatcher dispatcher=MinecraftClient.getInstance().getEntityRenderDispatcher();
        BurdockEntity burdock=BurdockEntity.createEntity(entity.getWorld());
        if (entity.get_is_ulta()){
            burdock.setBreakGroupLevel(10);
        }
        dispatcher.render(burdock, 0.0F, 0.0F, 0.0F, 0.0F, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public void onThrowStop(ItemZombieEntity entity) {
        super.onThrowStop(entity);

        World world=entity.getWorld();
        LivingEntity target=entity.getTarget();
        Vec3d pos=entity.getEyePos();
        Vec3d motion;
        if (target!=null && entity.canSee(target)){
            motion=target.getPos().subtract(entity.getPos()).normalize().multiply(3);
        }else{
            motion=entity.getRotationVec(0).multiply(3);
        }
        BurdockEntity burdock=BurdockEntity.createEntity(world, pos, motion);
        if (entity.get_is_ulta()){
            burdock.setBreakGroupLevel(10);
            entity.set_is_ulta(false);
            entity.set_has_ulta(false);
        }
        world.spawnEntity(burdock);
    }

    @Override
    public boolean hasBurdockEffectTick(ItemZombieEntity entity) {
        return false;
    }

    @Override
    public void initTargetGoals(ItemZombieEntity entity, GoalSelector targetSelector) {
        targetSelector.add(0, new RevengeGoal(entity));
        targetSelector.add(1, new ActiveTargetGoal<>(entity, LivingEntity.class, true));
    }

    @Override
    public void initGoals(ItemZombieEntity entity, GoalSelector goalSelector) {
        goalSelector.add(0, new SwimGoal(entity));
        goalSelector.add(1, new EscapeSunlightGoal(entity, 1));
        goalSelector.add(2, new ThrowGoal(entity));
        goalSelector.add(3, new WanderAroundFarGoal(entity, 1));
        goalSelector.add(4, new LookAroundGoal(entity));
        goalSelector.add(4, new LookAtEntityGoal(entity, LivingEntity.class, 32));
    }
}
