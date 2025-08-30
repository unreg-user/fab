package wta.entities.mobs.itemZombie.types.classes.goals;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import wta.entities.goals.FindItemsGoal;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.HierarchyB;
import wta.entities.mobs.itemZombie.types.ItemZombieTypeRegistry;

import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FindItemsForHeadGoal extends FindItemsGoal {
    private final IntList priorityHierarchies;
    private final ItemZombieTypeRegistry registryTypes = ItemZombieEntity.types;
    private final ImmutableList<HierarchyB.Hierarchy> hierarchies = registryTypes.getPriorityList();

    public FindItemsForHeadGoal(ItemZombieEntity entity, double range, int rangeToItem) {
        super(entity, range, rangeToItem, null);
        int size=hierarchies.size();
        IntArrayList variants=new IntArrayList( IntStream.range(0, size).toArray() );
        int[] priority=new int[size];
        Random uuidRandom=Random.create(entity.getUuid().getLeastSignificantBits());
        for (int i=0; i<size; i++) {
            int pos=uuidRandom.nextInt(variants.size());
            priority[i]=variants.removeInt(pos);
        }
        priorityHierarchies=new IntArrayList(priority);
    }

    @Override
    protected void onFinish() {
        ItemZombieEntity itemZombie=(ItemZombieEntity) entity;
        if (target!=null){
            itemZombie.pickupForHead(target);
        }
        target=null;
    }

    @Override
    protected void findTarget() {
        ItemZombieEntity itemZombie=(ItemZombieEntity) entity;
        target=itemZombie.findItem(
              ItemEntity.class,
              e -> true,
              range,
              rangeToItem,
              priorityHierarchies
        );
    }
}
