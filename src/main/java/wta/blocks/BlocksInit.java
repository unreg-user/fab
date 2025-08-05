package wta.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import wta.AllInit;
import wta.Fun;
import wta.blocks.blockEntitiesModClasses.BrewingStonecutterTableEClass;
import wta.blocks.blocksModClasses.*;
import wta.blocks.blocksModClasses.burdock.BurdockSaplingBlockClass;
import wta.blocks.blocksModClasses.pistonP.*;
import wta.blocks.blocksModClasses.stick_detectors.BrewingStonecutterTableClass;
import wta.blocks.blocksModClasses.stick_detectors.StickDetectorClass;

import java.util.ArrayList;
import java.util.List;

import static wta.Fab.MODID;

public class BlocksInit{
    //Blocks && Items
    public static Block usbb;
    public static Item usbbI;
    public static Block shSeeds;
    public static Item shSeedsI;
    public static Block allGrass;
    public static Item allGrassI;
    public static Block pistonSapling;
    public static Item pistonSaplingI;
    public static Block pistonUp;
    public static Block pistonIn;
    public static Block pistonKnot;
    public static Block pistonCompKnot;
    public static Item pistonCompKnotI;
    public static Block pistonFlower;
    public static Item pistonFlowerI;
    public static Block pistonUpStoped;
    public static Block pistonKnotGet;
    public static Item pistonKnotGetI;
    public static Block pistonKnotGetStripped;
    public static Item pistonKnotGetStrippedI;
    public static Block pointedDripstoneTable;
    public static Item pointedDripstoneTableI;
    public static Block stickDetector;
    public static Item stickDetectorI;
    public static Block burdockSapling;
    public static Block brewingStonecutterTable;
    public static Item brewingStonecutterTableI;
    public static BlockEntityType<BrewingStonecutterTableEClass> brewingStonecutterTableBE;

    //Settings
    public static AbstractBlock.Settings pistonSETTINGS;
    public static AbstractBlock.Settings pistonRandomlySETTINGS;

    //other
    public static ArrayList<Block> trapdoorDoors=new ArrayList<>();
    public static ArrayList<Item> trapdoorDoorsI=new ArrayList<>();

    public static void init(){
        //Blocks && Items
        usbb=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "up_sweet_berry_bush"),
                new UpBushClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DARK_GREEN)
                                .ticksRandomly()
                                .noCollision()
                                .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                                .pistonBehavior(PistonBehavior.DESTROY)
                                .luminance(state -> state.get(UpBushClass.AGE)==3 ? 10 : 0)
                )
        );
        usbbI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "up_sweet_berry_bush"),
                new BlockItem(usbb, new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.25f).snack().build()))
        );

        shSeeds=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "sh_seeds"),
                new ShSeedsClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DARK_GREEN)
                                .noCollision()
                                .ticksRandomly()
                                .breakInstantly()
                                .sounds(BlockSoundGroup.CROP)
                                .pistonBehavior(PistonBehavior.DESTROY)
                )
        );
        shSeedsI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "sh_seeds"),
                new BlockItem(shSeeds, new Item.Settings())
        );

        allGrass=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "all_grass"),
                new AllGrassClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.GREEN)
                                .strength(0.5F)
                                .sounds(BlockSoundGroup.GRAVEL)
                                .ticksRandomly()
                )
        );
        allGrassI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "all_grass"),
                new BlockItem(allGrass, new Item.Settings())
        );

        pistonSETTINGS=AbstractBlock.Settings.create()
                .mapColor(MapColor.STONE_GRAY)
                .strength(1.5F)
                .pistonBehavior(PistonBehavior.BLOCK);
        pistonRandomlySETTINGS=AbstractBlock.Settings.create()
                .mapColor(MapColor.STONE_GRAY)
                .strength(1.5F)
                .pistonBehavior(PistonBehavior.BLOCK)
                .ticksRandomly();

        pistonKnot=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_knot"),
                new PistonKnotClass(pistonSETTINGS)
        );

        pistonSapling=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_sapling"),
                new PistonTreeSaplingClass(pistonRandomlySETTINGS)
        );
        pistonSaplingI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "piston_sapling"),
                new BlockItem(pistonSapling, new Item.Settings())
        );

        pistonUp=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_tree_up"),
                new PistonUpClass(pistonRandomlySETTINGS)
        );

        pistonIn=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_tree_inner"),
                new PistonInClass(pistonSETTINGS)
        );

        pistonUpStoped=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_tree_up_stoped"),
                new PistonUpStopedClass(pistonSETTINGS)
        );

        pistonCompKnot=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_comp_knot"),
                new PistonFlowerClass(pistonSETTINGS)
        );
        pistonCompKnotI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "piston_comp_knot"),
                new BlockItem(pistonCompKnot,
                        new Item.Settings().food(new FoodComponent.Builder()
                                .nutrition(5)
                                .saturationModifier(2)
                                .build()
                        )
                )
        );

        pistonFlower=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_flower"),
                new PistonFlowerClass(pistonSETTINGS)
        );
        pistonFlowerI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "piston_flower"),
                new BlockItem(pistonFlower,
                        new Item.Settings().food(new FoodComponent.Builder()
                                .nutrition(5)
                                .saturationModifier(2)
                                .build()
                        )
                )
        );

        pistonKnotGet=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_knot_get"),
                new PistonKnotGetClass(pistonSETTINGS)
        );
        pistonKnotGetI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "piston_knot_get"),
                new BlockItem(pistonKnotGet,
                        new Item.Settings().food(new FoodComponent.Builder()
                                .nutrition(2)
                                .saturationModifier(2)
                                .snack()
                                .build()
                        )
                )
        );

        pistonKnotGetStripped=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "piston_knot_get_stripped"),
                new PistonKnotGetStrippedClass(pistonSETTINGS)
        );
        pistonKnotGetStrippedI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "piston_knot_get_stripped"),
                new BlockItem(pistonKnotGetStripped,
                        new Item.Settings().food(new FoodComponent.Builder()
                                .nutrition(1)
                                .saturationModifier(2)
                                .snack()
                                .build()
                        )
                )
        );

        for (String type : Fun.treeTrapdoorDoorSounds.keySet()){
            String name=type+"_trapdoor_door";
            trapdoorDoors.add(
                    Registry.register(
                            Registries.BLOCK,
                            Identifier.of(MODID, name),
                            new TrapdoorDoorClass(
                                    Fun.treeTrapdoorDoorSounds.get(type),
                                    AbstractBlock.Settings.create()
                                            .mapColor(MapColor.OAK_TAN)
                                            .strength(3.0F)
                                            .nonOpaque()
                                            .allowsSpawning(Blocks::never)
                                            .burnable()
                            )
                    )
            );
            trapdoorDoorsI.add(
                    Registry.register(
                            Registries.ITEM,
                            Identifier.of(MODID, name),
                            new BlockItem(trapdoorDoors.getLast(),
                                    new Item.Settings()
                            )
                    )
            );
        }
        for (String type : Fun.ironTrapdoorDoorSounds.keySet()){
            String name=type+"_trapdoor_door";
            trapdoorDoors.add(
                    Registry.register(
                            Registries.BLOCK,
                            Identifier.of(MODID, name),
                            new TrapdoorDoorClass(
                                    Fun.ironTrapdoorDoorSounds.get(type),
                                    AbstractBlock.Settings.create()
                                            .mapColor(MapColor.IRON_GRAY)
                                            .strength(5.0F)
                                            .nonOpaque()
                                            .allowsSpawning(Blocks::never)
                                            .requiresTool()
                                            .sounds(BlockSoundGroup.COPPER)
                            )
                    )
            );
            trapdoorDoorsI.add(
                    Registry.register(
                            Registries.ITEM,
                            Identifier.of(MODID, name),
                            new BlockItem(trapdoorDoors.getLast(),
                                    new Item.Settings()
                            )
                    )
            );
        }

        pointedDripstoneTable=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "pointed_dripstone_table"),
                new PointedDripstoneTableClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.OAK_TAN)
                                .instrument(NoteBlockInstrument.BASS)
                                .strength(2.5F)
                                .sounds(BlockSoundGroup.ANVIL)
                )
        );
        pointedDripstoneTableI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "pointed_dripstone_table"),
                new BlockItem(pointedDripstoneTable, new Item.Settings())
        );

        brewingStonecutterTable=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "brewing_stonecutter_table"),
                new BrewingStonecutterTableClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DIRT_BROWN)
                                .sounds(BlockSoundGroup.WOOD)
                                .strength(3.5F)
                )
        );
        brewingStonecutterTableI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "brewing_stonecutter_table"),
                new BlockItem(brewingStonecutterTable, new Item.Settings())
        );
        brewingStonecutterTableBE=Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MODID, "brewing_stonecutter_table"),
                BlockEntityType.Builder.create(BrewingStonecutterTableEClass::new, brewingStonecutterTable).build()
        );

        stickDetector=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "stick_detector"),
                new StickDetectorClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DIRT_BROWN)
                                .sounds(BlockSoundGroup.GRASS)
                                .nonOpaque()
                                .allowsSpawning(Blocks::never)
                )
        );
        stickDetectorI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "stick_detector"),
                new BlockItem(stickDetector,
                        new Item.Settings()
                )
        );

        burdockSapling=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "burdock_sapling"),
                new BurdockSaplingBlockClass(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DIRT_BROWN)
                                .sounds(BlockSoundGroup.GRASS)
                                .nonOpaque()
                                .allowsSpawning(Blocks::never)
                                .noCollision()
                                .ticksRandomly()
                )
        );

        //other
        ArrayList<Item> inMI=new ArrayList<>(List.of(
                usbbI,
                shSeedsI,
                allGrassI,
                pistonSaplingI,
                pistonCompKnotI,
                pistonFlowerI,
                pistonKnotGetI,
                pistonKnotGetStrippedI,
                pointedDripstoneTableI,
                stickDetectorI,
                brewingStonecutterTableI
        ));
        inMI.addAll(trapdoorDoorsI);
        AllInit.inMI.add(inMI.toArray(new Item[0]));
    }

    public static void clientInit(){
        //Blocks && Items
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                usbb,
                shSeeds,
                burdockSapling
        );

        for (Block block : trapdoorDoors){
            BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), block);
        }
    }
}
