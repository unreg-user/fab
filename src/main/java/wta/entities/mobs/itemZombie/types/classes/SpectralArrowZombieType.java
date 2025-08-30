package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.classes.abstracts.ThrowerZombieType;

public class SpectralArrowZombieType extends ThrowerZombieType {
	public SpectralArrowZombieType(int attackIntervalTicks) {
		super(attackIntervalTicks);
	}

	@Override
	public void throwRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EntityRenderDispatcher dispatcher= MinecraftClient.getInstance().getEntityRenderDispatcher();
		PersistentProjectileEntity arrow;
		if (entity.get_is_ulta()){
			arrow=new ArrowEntity(EntityType.ARROW, entity.getWorld());
		}else{
			arrow=new SpectralArrowEntity(EntityType.SPECTRAL_ARROW, entity.getWorld());
		}
		dispatcher.render(arrow, 0.0F, 0.0F, 0.0F, 90F, tickDelta, matrices, vertexConsumers, light);
	}

	@SuppressWarnings("DataFlowIssue")
	@Override
	public void onThrowStop(ItemZombieEntity entity) {
		super.onThrowStop(entity);

		World world=entity.getWorld();
		LivingEntity target=entity.getTarget();
		Vec3d motion;
		if (target!=null && entity.canSee(target)){
			motion=target.getPos().subtract(entity.getPos()).normalize().multiply(3);
		}else{
			motion=entity.getRotationVec(0).multiply(3);
		}
		PersistentProjectileEntity arrow;
		if (entity.get_is_ulta()){
			arrow=new ArrowEntity(world, entity, new ItemStack(Items.ARROW), new ItemStack(Items.BOW));
		}else{
			arrow=new SpectralArrowEntity(world, entity, new ItemStack(Items.SPECTRAL_ARROW), new ItemStack(Items.BOW));
		}
		arrow.setVelocity(motion);
		if (entity.get_is_ulta()){
			ArrowEntity arrowEntity=(ArrowEntity) arrow;
			arrowEntity.addEffect(new StatusEffectInstance(
				  StatusEffects.HUNGER,
				  200,
				  10
			));
			arrowEntity.addEffect(new StatusEffectInstance(
				  StatusEffects.WEAKNESS,
				  200,
				  10
			));
			arrowEntity.addEffect(new StatusEffectInstance(
				  StatusEffects.POISON,
				  200,
				  10
			));
			entity.set_is_ulta(false);
			entity.set_has_ulta(false);
		}
		world.spawnEntity(arrow);
	}
}
