package wta.entities.mobs.itemZombie.types;

import net.minecraft.item.Item;
import wta.entities.mobs.itemZombie.types.classes.DefaultItemZombieType;

import java.util.HashMap;

public class ItemZombieTypeRegistry {
    private final HashMap<Item, ItemZombieType> types=new HashMap<>();
    public static final ItemZombieType defaultType=new DefaultItemZombieType();

    public void register(Item item, ItemZombieType type){
        types.put(item, type);
    }

    public ItemZombieType get(Item item){
        if (types.containsKey(item)){
            return types.get(item);
        }
        return defaultType;
    }
}
