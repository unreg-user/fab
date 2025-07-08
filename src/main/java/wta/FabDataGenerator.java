package wta;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import wta.datagen.blockLootGen;
import wta.datagen.blocksTagGen;
import wta.datagen.modelsGen;
import wta.datagen.recipeGen;

public class FabDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack=fabricDataGenerator.createPack();
		pack.addProvider(modelsGen::new);
		pack.addProvider(blockLootGen::new);
		pack.addProvider(blocksTagGen::new);
		pack.addProvider(recipeGen::new);
	}
}
