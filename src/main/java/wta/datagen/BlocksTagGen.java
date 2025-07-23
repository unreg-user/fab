package wta.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import wta.Fun;

import java.util.concurrent.CompletableFuture;

import static wta.Fab.MODID;
import static wta.blocks.BlocksInit.*;

public class BlocksTagGen extends BlockTagProvider{
    public BlocksTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                allGrass
        );
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                brewingStonecutterTable,
                pointedDripstoneTable
        );
        //getOrCreateTagBuilder(BlockTags.AXE_MINEABLE);

        //functions
        for (String typeI : Fun.treeTrapdoorDoorSounds.keySet()){
            Block block=Fun.getBlockById(MODID, typeI+"_trapdoor_door");
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
        }

        for (String typeI : Fun.ironTrapdoorDoorSounds.keySet()){
            Block block=Fun.getBlockById(MODID, typeI+"_trapdoor_door");
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
        }
    }
}
