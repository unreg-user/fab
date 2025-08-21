package wta.entities.mobs.itemZombie.types;

import net.minecraft.data.server.tag.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import wta.TagsMod;
import wta.entities.mobs.itemZombie.types.classes.DefaultItemZombieType;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemZombieTypeRegistry {
    private final HashMap<Item, ItemZombieType> types=new HashMap<>();
    private final ArrayList<Item> undefaultList=new ArrayList<>();
    public static final ItemZombieType defaultType=new DefaultItemZombieType();

    public void register(Item item, ItemZombieType type){
        this.registerDefault(item, type);
        undefaultList.add(item);
    }
    public void register(TagKey<Item> tag, ItemZombieType type){
        for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(tag)){
            Item item=i.value();
            register(item, type);
        }
    }

    public void registerDefault(Item item, ItemZombieType type){
        types.put(item, type);
    }
    public void registerDefault(TagKey<Item> tag, ItemZombieType type){
        for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(tag)){
            Item item=i.value();
            registerDefault(item, type);
        }
    }

    public boolean isUndefault(Item item){
        return undefaultList.contains(item);
    }

    public ItemZombieType get(Item item){
        if (types.containsKey(item)){
            return types.get(item);
        }
        return defaultType;
    }
}
