package wta.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyStateLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import wta.blocks.PropertiesMod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static wta.blocks.BlocksInit.allGrass;
import static wta.blocks.BlocksInit.trapdoorDoors;

public class BlockLootGen extends FabricBlockLootTableProvider{
    public static ArrayList<Block> thisBlockDrop=new ArrayList<>(List.of(
            allGrass
    ));

    public BlockLootGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void trapdoorDoorLootGen(Block block){
        addDrop(block, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .conditionally(
                                BlockStatePropertyLootCondition
                                        .builder(block)
                                        .properties(
                                                StatePredicate.Builder.create()
                                                        .exactMatch(PropertiesMod.STATIC, false)
                                        )
                        )
                        .with(
                                ItemEntry.builder(block)
                        )
                )
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .conditionally(
                                BlockStatePropertyLootCondition
                                        .builder(block)
                                        .properties(
                                                StatePredicate.Builder.create()
                                                        .exactMatch(PropertiesMod.STATIC, true)
                                        )
                        )
                        .with(
                                ItemEntry
                                        .builder(block)
                                        .apply(
                                                CopyStateLootFunction
                                                        .builder(block)
                                                        .addProperty(PropertiesMod.STATIC)
                                        )
                        )
                )
        );
    }

    @Override
    public void generate() {
        for (Block block : thisBlockDrop) {
            addDrop(block);
        }
        for (Block block : trapdoorDoors){
            this.trapdoorDoorLootGen(block);
        }
    }
}
