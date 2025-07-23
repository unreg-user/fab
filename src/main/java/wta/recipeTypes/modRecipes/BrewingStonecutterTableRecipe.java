package wta.recipeTypes.modRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import wta.recipeTypes.RecipeInit;

public class BrewingStonecutterTableRecipe implements Recipe<SingleStackRecipeInput> {
    public final ItemStack input;
    public final ItemStack output;

    public BrewingStonecutterTableRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return !input.item().isEmpty() &&
                input.item().getItem()==this.input.getItem();
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeInit.brewingStonecutterTableRTS;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.brewingStonecutterTableRT;
    }
}
