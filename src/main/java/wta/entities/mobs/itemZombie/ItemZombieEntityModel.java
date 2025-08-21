package wta.entities.mobs.itemZombie;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class ItemZombieEntityModel extends BipedEntityModel<ItemZombieEntity> {
	public ItemZombieEntityModel(ModelPart root) {
		super(root);
	}

	@Override
	public void setAngles(ItemZombieEntity itemZombie, float f, float g, float h, float i, float j) {
		super.setAngles(itemZombie, f, g, h, i, j);
		float progress;
		if ( (progress = itemZombie.get_set_head_animation_progress()) > -0.5F){
			float an_progress=progress + MathHelper.fractionalPart(h)*ItemZombieEntity.step_set_head_animation;
			rightArm.pitch = (float) (-(an_progress)*Math.PI);
		} else if ((progress = itemZombie.get_throw_progress()) > -0.5F) {
			float an_progress=progress + MathHelper.fractionalPart(h)*ItemZombieEntity.step_throw;
			rightArm.pitch = (float) ((an_progress+0.7F)*Math.PI);
		}
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		root.addChild(
			  EntityModelPartNames.HEAD,
			  ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			  ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.HAT,
			  ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			  ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.BODY,
			  ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
			  ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.RIGHT_ARM,
			  ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			  ModelTransform.pivot(-5.0F, 2.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.LEFT_ARM,
			  ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			  ModelTransform.pivot(5.0F, 2.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.RIGHT_LEG,
			  ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			  ModelTransform.pivot(-1.9F, 12.0F, 0.0F)
		);
		root.addChild(
			  EntityModelPartNames.LEFT_LEG,
			  ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			  ModelTransform.pivot(1.9F, 12.0F, 0.0F)
		);
		return TexturedModelData.of(data, 64, 64);
	}
}
