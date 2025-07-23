package wta.gui.guiMod.brewingStonecutterTable;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class TorchSlot extends Slot {
    private final boolean can;

    public TorchSlot(Inventory inventory, boolean can, int index, int x, int y) {
        super(inventory, index, x, y);
        this.can=can;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        boolean isTorch=stack.getItem()==Items.TORCH;
        return !(can ^ isTorch);
    }
}
