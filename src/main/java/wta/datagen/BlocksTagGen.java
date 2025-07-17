package wta.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static wta.blocks.BlocksInit.allGrass;
import static wta.blocks.BlocksInit.trapdoorDoors;

public class BlocksTagGen extends BlockTagProvider{
    public BlocksTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                allGrass
        );
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(trapdoorDoors.toArray(new Block[]{}));
    }
}
