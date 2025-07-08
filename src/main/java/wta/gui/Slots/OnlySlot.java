package wta.gui.Slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class OnlySlot extends Slot {
    private final List<Item> items;
    public OnlySlot(Inventory inventory, List<Item> items, int index, int x, int y) {
        super(inventory, index, x, y);
        this.items=items;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (items.contains(stack.getItem())){
            return true;
        }
        return false;
    }
}
