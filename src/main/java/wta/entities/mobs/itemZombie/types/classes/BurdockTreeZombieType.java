package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.classes.abstracts.ThrowerZombieType;
import wta.entities.projectiles.burdock.BurdockEntity;

public class BurdockTreeZombieType extends ThrowerZombieType {
    public BurdockTreeZombieType(int attackIntervalTicks) {
        super(attackIntervalTicks);
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
        burdock.setOwner(entity);
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
}
