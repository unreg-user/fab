package wta.entities.mobs.itemZombie.types.classes.goals;

import net.minecraft.entity.ItemEntity;
import wta.entities.goals.FindItemsGoal;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

import java.util.function.Predicate;

public class FindItemsForHeadGoal extends FindItemsGoal {
    public FindItemsForHeadGoal(ItemZombieEntity entity, double range, int rangeToItem, Predicate<ItemEntity> predicate) {
        super(entity, range, rangeToItem, predicate);
    }

    @Override
    protected void onFinish() {
        ItemZombieEntity itemZombie=(ItemZombieEntity) entity;
        if (target!=null){
            itemZombie.pickupForHead(target);
        }
        target=null;
    }
}
