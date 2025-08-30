package wta.entities.mobs.itemZombie.types.classes.abstracts;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

public abstract class ArrowThrowerZombieType extends ThrowerZombieType {
	public ArrowThrowerZombieType(int attackIntervalTicks) {
		super(attackIntervalTicks);
	}

	@Override
	public void throwRender(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemZombieEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EntityRenderDispatcher dispatcher=MinecraftClient.getInstance().getEntityRenderDispatcher();
		ArrowEntity arrow=new ArrowEntity(EntityType.ARROW, entity.getWorld());
		dispatcher.render(arrow, 0.0F, 0.0F, 0.0F, 90F, tickDelta, matrices, vertexConsumers, light);
	}

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
		ItemStack bow=getBow(entity);
		ArrowEntity arrowEntity=new ArrowEntity(world, entity, new ItemStack(Items.ARROW), bow);
		customizeArrow(entity, arrowEntity);
		bow.damage(getDamage(entity, bow), (ServerWorld) entity.getWorld(), null, item -> onBowBroke(entity));
		arrowEntity.setVelocity(motion);
		if (entity.get_is_ulta()){
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
		world.spawnEntity(arrowEntity);
	}

	protected ItemStack getBow(ItemZombieEntity entity){
		return new ItemStack(Items.BOW);
	}

	protected void onBowBroke(ItemZombieEntity entity){

	}

	protected void customizeArrow(ItemZombieEntity entity, ArrowEntity arrow){

	}

	protected int getDamage(ItemZombieEntity entity, ItemStack bow){
		return 1;
	}
}
