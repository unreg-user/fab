package wta.entities.mobs.itemZombie;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import wta.CommandsInit;
import wta.entities.mobs.itemZombie.types.ItemZombieType;

public class ThrowFeatureRender extends FeatureRenderer<ItemZombieEntity, ItemZombieEntityModel> {
	public ThrowFeatureRender(FeatureRendererContext<ItemZombieEntity, ItemZombieEntityModel> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float progress=entity.get_throw_progress();
		if (progress < -0.5F){
			return;
		}
		ItemZombieType itemType=entity.getItemType();

		matrices.push();
		ModelPart hand=this.getContextModel().rightArm;
		matrices.multiply(RotationAxis.POSITIVE_X.rotation(hand.pitch));
		matrices.multiply(RotationAxis.POSITIVE_Z.rotation(hand.yaw));
		matrices.translate(-0.325F, 0.5F, 0.025F); //-0.375, 0.625, 0
		itemType.throwRender(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
		matrices.pop();
	}
}
