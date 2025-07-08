package wta.gui.Handled;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import wta.blocks.blockEntitiesModClasses.BrewingStonecutterTableEClass;
import wta.gui.guiMod.BrewingStonecutterTableGUIClass;

import static wta.Fab.MODID;

public class BrewingStonecutterTableGUIHClass extends HandledScreen<BrewingStonecutterTableGUIClass> {
    private static final Identifier BG=Identifier.of(MODID, "textures/gui/container/brewing_stonecutter_table.png");
    private static final Identifier burnProgressTexture=Identifier.of(MODID, "container/brewing_stonecutter_table/arrow");
    public BrewingStonecutterTableGUIHClass(BrewingStonecutterTableGUIClass handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x=(width-backgroundWidth)/2;
        int y=(height-backgroundHeight)/2;
        context.drawTexture(BG, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int lenArrow=(int) Math.ceil(((((double) handler.propertyDelegate.get(0)) / ((double) BrewingStonecutterTableEClass.maxClicks))*24D));
        context.drawGuiTexture(burnProgressTexture, 24, 16, 0, 0, x+77, y+35, lenArrow, 16);
    }
}
