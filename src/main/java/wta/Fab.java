package wta;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wta.blocks.BlocksInit;
import wta.entities.EntitiesInit;
import wta.gui.GuiInit;
import wta.items.ItemsInit;
import wta.recipeTypes.RecipeInit;

/**
 * ...ANY:
 * B (or often nothing) - block<br>
 * BE — blockEntity<br>
 * E — entity<br>
 * RT — recipe type<br>
 * I — item (or forI)<br>
 * RT — recipe type<br>
 * RTS — RT serializer<br>
 * -----<br>
 * any...:
 * vn — vanilla
 * -----<br>
 * other:<br>
 * dir — direction<br>
 */
public class Fab implements ModInitializer, ClientModInitializer {
	public static final String MODID = "fab";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	@Override
	public void onInitialize() {
		Fun.initFun();
		BlocksInit.init();
		ItemsInit.init();
		EntitiesInit.init();
		AllInit.init();
		RecipeInit.init();
		GuiInit.init();
		LOGGER.info("Hello Fabric world! Odnii!");
	}

	@Override
	public void onInitializeClient() {
		BlocksInit.clientInit();
		GuiInit.clientInit();
		EntitiesInit.clientInit();
		LOGGER.info("Hello Fabric client! Odnii!");
	}
}