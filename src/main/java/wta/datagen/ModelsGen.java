package wta.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import wta.Fun;
import wta.blocks.PropertiesMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static wta.Fab.MODID;
import static wta.blocks.BlocksInit.*;
import static wta.items.ItemsInit.shBreadI;
import static wta.items.ItemsInit.shWheatI;

public class ModelsGen extends FabricModelProvider {

    public static HashMap<Item, String> blocksParent=new HashMap<>(){{
        put(pistonFlowerI, "piston_flower");
        put(pistonCompKnotI, "piston_comp_knot");
    }};
    public static ArrayList<Block> defultBlocks=new ArrayList<>(List.of(
            allGrass
    ));
    public static ArrayList<Item> handheldItems=new ArrayList<>(List.of(
            shSeedsI,
            shBreadI,
            shWheatI

    ));
    private static Model blockParentM(String parent) {
        return new Model(
                Optional.of(
                        Identifier.of(MODID, "block/"+parent)
                ),
                Optional.empty()
        );
    }

    public ModelsGen(FabricDataOutput output) {
        super(output);
    }

    private static BlockStateSupplier getBSS_trapdoorDoor(String type) {
        Block block=Fun.getBlockById(MODID, type+"_trapdoor_door");
        BlockStateVariantMap.QuadrupleProperty map=BlockStateVariantMap.create(PropertiesMod.H_ROTATE, PropertiesMod.TRAP, Properties.BLOCK_HALF, Properties.OPEN);
        map
                .register(Direction.WEST, true, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_bottom"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=west,half=bottom,open=true,trap=true
                .register(Direction.WEST, true, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=west,half=top,open=false,trap=true
                .register(Direction.WEST, true, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_top"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=west,half=top,open=true,trap=true
                .register(Direction.WEST, true, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.X, VariantSettings.Rotation.R180)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=south,half=bottom,open=false,trap=true
                .register(Direction.SOUTH, true, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_bottom")))

                // facing=south,half=bottom,open=true,trap=true
                .register(Direction.SOUTH, true, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open")))

                // facing=south,half=top,open=false,trap=true
                .register(Direction.SOUTH, true, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_top")))

                // facing=south,half=top,open=true,trap=true
                .register(Direction.SOUTH, true, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.X, VariantSettings.Rotation.R180)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=north,half=bottom,open=false,trap=true
                .register(Direction.NORTH, true, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_bottom"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=north,half=bottom,open=true,trap=true
                .register(Direction.NORTH, true, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=north,half=top,open=false,trap=true
                .register(Direction.NORTH, true, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_top"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=north,half=top,open=true,trap=true
                .register(Direction.NORTH, true, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.X, VariantSettings.Rotation.R180))

                // facing=east,half=bottom,open=false,trap=true
                .register(Direction.EAST, true, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_bottom"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=east,half=bottom,open=true,trap=true
                .register(Direction.EAST, true, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=east,half=top,open=false,trap=true
                .register(Direction.EAST, true, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_top"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=east,half=top,open=true,trap=true
                .register(Direction.EAST, true, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_trapdoor_open"))
                                .put(VariantSettings.X, VariantSettings.Rotation.R180)
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // trap=false variants (doors)

                // facing=west,half=bottom,open=false,trap=false
                .register(Direction.WEST, false, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left")))

                // facing=west,half=bottom,open=true,trap=false
                .register(Direction.WEST, false, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=west,half=top,open=false,trap=false
                .register(Direction.WEST, false, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left")))

                // facing=west,half=top,open=true,trap=false
                .register(Direction.WEST, false, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=south,half=bottom,open=false,trap=false
                .register(Direction.SOUTH, false, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=south,half=bottom,open=true,trap=false
                .register(Direction.SOUTH, false, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left_open")))

                // facing=south,half=top,open=false,trap=false
                .register(Direction.SOUTH, false, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=south,half=top,open=true,trap=false
                .register(Direction.SOUTH, false, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left_open")))

                // facing=north,half=bottom,open=false,trap=false
                .register(Direction.NORTH, false, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=north,half=bottom,open=true,trap=false
                .register(Direction.NORTH, false, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=north,half=top,open=false,trap=false
                .register(Direction.NORTH, false, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R90))

                // facing=north,half=top,open=true,trap=false
                .register(Direction.NORTH, false, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=east,half=bottom,open=false,trap=false
                .register(Direction.EAST, false, BlockHalf.BOTTOM, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=east,half=bottom,open=true,trap=false
                .register(Direction.EAST, false, BlockHalf.BOTTOM, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_bottom_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270))

                // facing=east,half=top,open=false,trap=false
                .register(Direction.EAST, false, BlockHalf.TOP, false,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R180))

                // facing=east,half=top,open=true,trap=false
                .register(Direction.EAST, false, BlockHalf.TOP, true,
                        BlockStateVariant.create()
                                .put(VariantSettings.MODEL, Identifier.of("minecraft", "block/"+type+"_door_top_left_open"))
                                .put(VariantSettings.Y, VariantSettings.Rotation.R270));
        return VariantsBlockStateSupplier.create(block).coordinate(map);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (Block block : defultBlocks){
            blockStateModelGenerator.registerSimpleCubeAll(block);
            //blockStateModelGenerator.registerSimpleState(block);
        }
        for (String type : Fun.doorTrapdoorToSound.keySet()){
            blockStateModelGenerator.blockStateCollector.accept(getBSS_trapdoorDoor(type));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item block : blocksParent.keySet()){
            itemModelGenerator.register(block, blockParentM(blocksParent.get(block)));
        }
        for (Block block : defultBlocks){
            itemModelGenerator.register(new ItemStack(block).getItem(), blockParentM(Registries.BLOCK.getId(block).getPath()));
        }
        for (Item item : handheldItems){
            itemModelGenerator.register(item, Models.HANDHELD);
        }
    }
}
