package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.classes.abstracts.ArrowThrowerZombieType;

public class ArrowZombieType extends ArrowThrowerZombieType {
	public ArrowZombieType(int attackIntervalTicks) {
		super(attackIntervalTicks);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		fourRender(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
	}
}
