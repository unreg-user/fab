package wta.gui.Slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Function;

public class SlotWithFunction extends Slot {
    private final Function<ItemStack, Boolean> function;
    public SlotWithFunction(Inventory inventory, Function<ItemStack, Boolean> function, int index, int x, int y) {
        super(inventory, index, x, y);
        this.function=function;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return function.apply(stack);
    }
}
