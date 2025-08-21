package wta.entities.mobs;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.AllInit;

import static wta.Fab.MODID;

public class SpawnEggsInit {
    //Items
    public static Item ItemZombieSpawnEggI;

    public static void init(){
        //Items
        ItemZombieSpawnEggI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "item_zombie_spawn_egg"),
                new SpawnEggItem(
                        MobsInit.itemZombieE,
                        44975,
                        7969893,
                        new Item.Settings()
                )
        );
        //other
        Item[] inMI=new Item[]{
                ItemZombieSpawnEggI
        };
        AllInit.inMI.add(inMI);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            for (Item item : inMI){
                content.add(item);
            }
        });
    }
}
