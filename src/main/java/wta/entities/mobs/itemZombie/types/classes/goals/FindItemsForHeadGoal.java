package wta.entities.mobs.itemZombie.types.classes.goals;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import wta.entities.goals.FindItemsGoal;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.types.HierarchyB;
import wta.entities.mobs.itemZombie.types.ItemZombieTypeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
        target=null;
        World world=entity.getWorld();
        List<Entity> targets=world.getOtherEntities(
              entity,
              entity.getBoundingBox().expand(range),
              e  -> e instanceof ItemEntity itemEntity
                    && entity.canSee(e)
                    && entity.getNavigation().findPathTo(e, rangeToItem)!=null
        );
        if (targets.isEmpty()){
            return;
        }

        List<ArrayList<ItemEntity>> stackPriorities=Stream.generate(() -> new ArrayList<ItemEntity>())
              .limit(hierarchies.size())
              .collect(Collectors.toList());
        for (Entity i : targets){
            ItemEntity entityI=(ItemEntity) i;
            ItemStack stack=entityI.getStack();
            int hierarchy=registryTypes.getHierarchyPos(stack.getItem());
            if (hierarchy != -1){
                stackPriorities.get(hierarchy).add(entityI);
            }
        }

        ArrayList<ItemEntity> stackPriority=null;
        HierarchyB.Hierarchy pos=null;
        for (int i : priorityHierarchies){
            ArrayList<ItemEntity> hierarchyI=stackPriorities.get(i);
            if (!hierarchyI.isEmpty()){
                stackPriority=hierarchyI;
                pos=hierarchies.get(i);
            }
        }
        if (stackPriority==null){
            return;
        }
        int sizePriority = stackPriority.size();
        if (sizePriority==1){
            target=stackPriority.get(0);
            return;
        }

        ArrayList<ItemEntity> stackPriority2=null;
        IntArrayList priorities=new IntArrayList();
        for (int i=0; i < sizePriority; i++) {
            Item itemI = stackPriority.get(i).getStack().getItem();
            priorities.add(pos.get(itemI));
        }
        int max=priorities.stream().min(Comparator.naturalOrder()).get();
        int[] maxValues=IntStream.range(0, priorities.size())
              .filter(i -> priorities.get(i)==max)
              .toArray();
        ArrayList<ItemEntity> stackPriorityFinal=stackPriority;
        stackPriority2=new ArrayList<>(Arrays.asList(Arrays.stream(maxValues)
              .mapToObj(i -> stackPriorityFinal.get(i))
              .toArray(ItemEntity[]::new)));
        if (stackPriority2.size()==1){
            target=stackPriority2.get(0);
            return;
        }
        target=stackPriority2.get(0);
    }
}
