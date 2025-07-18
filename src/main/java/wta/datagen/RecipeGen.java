package wta.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import wta.Fun;

import java.util.concurrent.CompletableFuture;

import static wta.Fab.MODID;
import static wta.Fun.MC;

public class RecipeGen extends FabricRecipeProvider {
    public RecipeGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        for (String type : Fun.fullTrapdoorDoorSounds.keySet()) {
            String name=type+"_trapdoor_door";
            String trapdoor=type+"_door";
            String door=type+"_trapdoor";
            Block block=Fun.getBlockById(MODID, name);
            Block trapdoor_block=Fun.getBlockById(MC, trapdoor);
            Block door_block=Fun.getBlockById(MC, door);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, block, 2)
                    .input(Fun.getBlockById(MC, type+"_door"))
                    .input(Fun.getBlockById(MC, type+"_trapdoor"))
                    .criterion(FabricRecipeProvider.hasItem(trapdoor_block), FabricRecipeProvider.conditionsFromItem(trapdoor_block))
                    .criterion(FabricRecipeProvider.hasItem(door_block), FabricRecipeProvider.conditionsFromItem(door_block))
                    .offerTo(exporter, Identifier.of(MODID, name));
        }
    }
}
