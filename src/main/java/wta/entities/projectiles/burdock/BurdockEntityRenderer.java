package wta.entities.projectiles.burdock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import wta.entities.other.CustomProjectileEntityRenderer;

import static wta.Fab.MODID;

@Environment(EnvType.CLIENT)
public class BurdockEntityRenderer extends CustomProjectileEntityRenderer<BurdockEntity, BurdockEntityModel> {
    public BurdockEntityRenderer(EntityRendererFactory.Context context, BurdockEntityModel model, float f) {
        super(context, model, f);
    }

    @Override
    public Identifier getTexture(BurdockEntity entity) {
        int breakLevel=entity.getBreakGroupLevel();
        return switch (breakLevel){
            case 0 -> Identifier.of(MODID, "textures/entity/burdock/burdock.png");
            case 1 -> Identifier.of(MODID, "textures/entity/burdock/burdock1.png");
            case 2 -> Identifier.of(MODID, "textures/entity/burdock/burdock2.png");
            case 10 -> Identifier.of(MODID, "textures/entity/burdock/burdock10.png");
            default -> Identifier.of(MODID, "textures/entity/burdock/burdock3.png");
        };
    }

    @Override
    public void render(BurdockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        VertexConsumer vertexConsumer=vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity)));

        float headPith=entity.getPitch()+90;
        float headYaw=entity.getHeadYaw();
        matrices.translate(0.0D, 0.25F, 0.0D);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(headPith));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(headYaw));
        matrices.translate(0.0D, -1.5F, 0.0D); //-1.25F

        root.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }
}
