package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import wta.CommandsInit;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.ItemZombieType;
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
        float scale=0.25f;
        matrices.scale(scale, scale, scale);
        dispatcher.render(burdock, 0.0F, 0.0F, 0.0F, 0.0F, tickDelta, matrices, vertexConsumers, light);
        scale=0.75f;
        matrices.scale(scale, scale, scale);
        dispatcher.render(burdock, 0.0F, 0.0F, 0.0F, 0.0F, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean hasBurdockEffectTick(ItemZombieEntity entity) {
        return false;
    }
}
