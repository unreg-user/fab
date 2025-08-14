package wta.recipe_types.modRecipes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import wta.recipe_types.RecipeInit;

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
                ItemStack.areItemsEqual(input.item().copyWithCount(1), this.input.copyWithCount(1)) &&
                input.item().getCount()>=this.input.getCount();
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        input.item().decrement(this.input.getCount());
        return output.copy();
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
