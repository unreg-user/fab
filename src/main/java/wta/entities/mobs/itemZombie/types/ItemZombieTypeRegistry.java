package wta.entities.mobs.itemZombie.types;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import wta.entities.mobs.itemZombie.types.classes.DefaultItemZombieType;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemZombieTypeRegistry {
    private final HashMap<Item, ItemZombieType> types=new HashMap<>();
    private ImmutableList<HierarchyB.Hierarchy> prioriry;
    private final ArrayList<Item> undefaultList=new ArrayList<>();
    public static final ItemZombieType defaultType=new DefaultItemZombieType();

    public void register(Item item, ItemZombieType type, boolean isUndefault){
        types.put(item, type);
        if (isUndefault) undefaultList.add(item);
    }
    public void register(TagKey<Item> tag, ItemZombieType type, boolean isDefault){
        for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(tag)){
            Item item=i.value();
            register(item, type, isDefault);
        }
    }

    public boolean isUndefault(Item item){
        return undefaultList.contains(item);
    }

    public Item getRandomUndefault(Random random){
        return undefaultList.get(random.nextInt(undefaultList.size()));
    }

    public ItemZombieType get(Item item){
        if (types.containsKey(item)){
            return types.get(item);
        }
        return defaultType;
    }

    public void createPriority(HierarchyB.Hierarchy... hierarchies){
        prioriry=ImmutableList.copyOf(hierarchies);
    }

    public ImmutableList<HierarchyB.Hierarchy> getPriorityList(){
        return prioriry;
    }

    public int getHierarchyPos(Item item){
        int size=prioriry.size();
        for (int i=0; i<size; i++) {
            if (prioriry.get(i).contains(item)){
                return i;
            }
        }
        return -1;
    }
}
