package wta;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.stream.Stream;

import static wta.Fab.MODID;

public class AllInit {
    public static ItemGroup modItems;
    public static ArrayList<Item[]> inMI=new ArrayList<>();
    public static void init(){
        Item[] inMI2=inMI.stream()
                .flatMap(Stream::of)
                .toArray(Item[]::new);
        modItems=Registry.register(Registries.ITEM_GROUP,
                Identifier.of(MODID, "fab_items"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("Fab items"))
                        .icon(() -> new ItemStack(Blocks.BIRCH_FENCE))
                        .entries((displayContext, entries) -> {
                            for (Item item : inMI2){
                                entries.add(item);
                            }
                            entries.add(Items.BIRCH_FENCE);
                        }).build());
    }
}
