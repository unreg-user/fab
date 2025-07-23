package wta.recipeTypes.modSeriya;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import wta.recipeTypes.modRecipes.BrewingStonecutterTableRecipe;

public class BrewingStonecutterTableRecipeSerializer implements RecipeSerializer<BrewingStonecutterTableRecipe> {
    public static final MapCodec<BrewingStonecutterTableRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.VALIDATED_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
            ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
    ).apply(instance, BrewingStonecutterTableRecipe::new));
    public static final PacketCodec<RegistryByteBuf, BrewingStonecutterTableRecipe> PACKET_CODEC = PacketCodec.ofStatic(BrewingStonecutterTableRecipeSerializer::write, BrewingStonecutterTableRecipeSerializer::read);

    @Override
    public MapCodec<BrewingStonecutterTableRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, BrewingStonecutterTableRecipe> packetCodec() {
        return PACKET_CODEC;
    }

    private static BrewingStonecutterTableRecipe read(RegistryByteBuf buf) {
        ItemStack input = ItemStack.PACKET_CODEC.decode(buf);
        ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
        return new BrewingStonecutterTableRecipe(input, output);
    }

    private static void write(RegistryByteBuf buf, BrewingStonecutterTableRecipe recipe) {
        ItemStack.PACKET_CODEC.encode(buf, recipe.input);
        ItemStack.PACKET_CODEC.encode(buf, recipe.output);
    }
}
