package wta.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import wta.gui.Handled.BrewingStonecutterTableGUIHClass;
import wta.gui.guiMod.brewingStonecutterTable.BrewingStonecutterTableGUIClass;

import static wta.Fab.MODID;

public class GuiInit {
    public static ScreenHandlerType<BrewingStonecutterTableGUIClass> BrewingStonecutterTableGUI_T;
    public static void init(){
        BrewingStonecutterTableGUI_T=Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(MODID, "b_stone"),
                new ScreenHandlerType<>(BrewingStonecutterTableGUIClass::new, FeatureSet.empty())
        );
    }
    public static void clientInit(){
        HandledScreens.register(BrewingStonecutterTableGUI_T, BrewingStonecutterTableGUIHClass::new);
    }
}
