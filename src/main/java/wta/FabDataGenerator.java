package wta;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import wta.datagen.BlockLootGen;
import wta.datagen.BlocksTagGen;
import wta.datagen.ModelsGen;
import wta.datagen.RecipeGen;

public class FabDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack=fabricDataGenerator.createPack();
		pack.addProvider(ModelsGen::new);
		pack.addProvider(BlockLootGen::new);
		pack.addProvider(BlocksTagGen::new);
		pack.addProvider(RecipeGen::new);
	}
}
