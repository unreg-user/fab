package wta.mixins.mixinClasses.other;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import wta.entities.projectiles.burdock.BurdockEntity;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

@Environment(EnvType.CLIENT)
public class StuckBurdockFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends StuckFeatureForEntities<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public StuckBurdockFeatureRenderer(EntityRendererFactory.Context context, LivingEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
        this.dispatcher = context.getRenderDispatcher();
    }

    protected int getObjectCount(T entity) {
        if (entity instanceof LivingEntityFixerInterface livingEntityFixed){
            return livingEntityFixed.getStuckBurdockCount();
        }
        return 0;
    }

    protected void renderObject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float directionX, float directionY, float directionZ, float tickDelta) {
        float f = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);

        BurdockEntity burdock=BurdockEntity.createEntity(entity.getWorld(), entity.getPos());

        burdock.setYaw((float)(Math.atan2(directionX, directionZ) * (double)(180F / (float)Math.PI)));
        burdock.setPitch((float)(Math.atan2(directionY, f) * (double)(180F / (float)Math.PI)));

        burdock.prevYaw=burdock.getYaw();
        burdock.prevPitch=burdock.getPitch();

        this.dispatcher.render(burdock, 0.0F, 0.0F, 0.0F, 0.0F, tickDelta, matrices, vertexConsumers, light);
    }
}