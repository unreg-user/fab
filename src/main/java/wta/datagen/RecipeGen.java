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
        for (String type : Fun.doorTrapdoorToSound.keySet()) {
            String name=type+"_trapdoor_door";
            Block block=Fun.getBlockById(MODID, name);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, block, 2)
                    .input(Fun.getBlockById(MC, type+"_door"))
                    .input(Fun.getBlockById(MC, type+"_trapdoor"))
                    .criterion(FabricRecipeProvider.hasItem(block), FabricRecipeProvider.conditionsFromItem(block))
                    .offerTo(exporter, Identifier.of(MODID, name));
        }
    }
}
