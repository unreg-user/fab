package wta.recipe_types;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.recipe_types.modRecipes.BrewingStonecutterTableRecipe;
import wta.recipe_types.modSeriya.BrewingStonecutterTableRecipeSerializer;

import static wta.Fab.MODID;

public class RecipeInit {
    public static RecipeType<BrewingStonecutterTableRecipe> brewingStonecutterTableRT;
    public static RecipeSerializer<BrewingStonecutterTableRecipe> brewingStonecutterTableRTS;
    public static void init(){
        brewingStonecutterTableRT=Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.of(MODID, "b_stone"),
                new RecipeType<>() {
                    @Override
                    public String toString() {
                        return Identifier.of(MODID, "b_stone").toString();
                    }
                }
        );
        brewingStonecutterTableRTS=Registry.register(
                Registries.RECIPE_SERIALIZER,
                Identifier.of(MODID, "b_stone"),
                new BrewingStonecutterTableRecipeSerializer()
        );
    }
}
