package wta.entities.mobs.enderCubeP;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class EnderCubeEntityModel extends SinglePartEntityModel<EnderCubeEntity> {

	public final ModelPart root;
	public final ModelPart head;
	public final ModelPart head2;
	public final ModelPart eyes;

	public EnderCubeEntityModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.head2 = root.getChild("head2");
		this.eyes = root.getChild("eyes");
	}

	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	public void setAngles(EnderCubeEntity enderCube, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		root.traverse().forEach(ModelPart::resetTransform);

		Entity target = enderCube.getWorld().getEntityById(enderCube.getDataTracker().get(EnderCubeEntity.TARGET_ID));

		if (target == null) {
			float headYaw2=(float) Math.toRadians(headYaw);
			float headPitch2=(float) Math.toRadians(headPitch);

			head.yaw = headYaw2;
			head.pitch = headPitch2;

			head2.yaw = headYaw2;
			head2.pitch = headPitch2;

			eyes.yaw = headYaw2;
			eyes.pitch = headPitch2;
		} else {
			float headYaw2=(float) Math.toRadians(target.getHeadYaw());
			float headPitch2=(float) Math.toRadians(target.getPitch());

			head.yaw = headYaw2;
			head.pitch = headPitch2;

			head2.yaw = headYaw2;
			head2.pitch = headPitch2;

			eyes.yaw = headYaw2;
			eyes.pitch = headPitch2;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		//rendering
		head.render(matrices, vertices, light, overlay);
		head2.render(matrices, vertices, light, overlay);
		eyes.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();


		ModelPartBuilder headBuilder = ModelPartBuilder.create()
				.uv(0, 0)
				.cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F));
		root.addChild("head", headBuilder, ModelTransform.pivot(0.0F, 16F, 0.0F));

		ModelPartBuilder head2Builder = ModelPartBuilder.create()
				.uv(0, 32)
				.cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F));
		root.addChild("head2", head2Builder, ModelTransform.pivot(0.0F, 16F, 0.0F));

		ModelPartBuilder eyesBuilder = ModelPartBuilder.create()
				.uv(0, 64)
				.cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F));
		root.addChild("eyes", eyesBuilder, ModelTransform.pivot(0.0F, 16F, 0.0F));

		return TexturedModelData.of(data, 64, 96);
	}
}
