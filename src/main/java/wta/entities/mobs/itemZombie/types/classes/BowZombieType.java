package wta.entities.mobs.itemZombie.types.classes;

import net.minecraft.item.ItemStack;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

public class BowZombieType extends ArrowZombieType {
	public BowZombieType(int attackIntervalTicks) {
		super(attackIntervalTicks);
	}

	@Override
	protected ItemStack getBow(ItemZombieEntity entity) {
		return entity.getHeadItem();
	}

	@Override
	protected void onBowBroke(ItemZombieEntity entity) {
		entity.setHeadItem(ItemStack.EMPTY);
	}
}
