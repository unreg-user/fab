package wta.items;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.AllInit;
import wta.items.ItemsModClasses.BowDripstoneClass;

import static wta.Fab.MODID;

public class ItemsInit {
    public static Item shWheatI;
    public static Item shBreadI;
    public static Item bowDripstoneI;
    public static Item vnStickI;

    public static Item[] inMI;
    public static void init(){
        shWheatI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "sh_wheat"),
                new Item(new Item.Settings())
        );
        shBreadI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "sh_bread"),
                new Item(new Item.Settings().food(
                        (new FoodComponent.Builder())
                                .nutrition(2)
                                .saturationModifier(0.5F)
                                .build()
                ))
        );
        bowDripstoneI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "bow_dripstone"),
                new BowDripstoneClass(new Item.Settings()
                        .maxDamage(384)
                )
        );
        inMI=new Item[]{
                shWheatI,
                shBreadI,
                bowDripstoneI
        };


        AllInit.inMI.add(inMI);
    }
}
