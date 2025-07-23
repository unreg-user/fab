package wta.gui.guiMod.brewingStonecutterTable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import wta.blocks.blockEntitiesModClasses.BrewingStonecutterTableEClass;
import wta.gui.GuiInit;
import wta.gui.Slots.ResultSlot;

public class BrewingStonecutterTableGUIClass extends ScreenHandler {
    private final Inventory inventory;
    private final PlayerInventory playerInventory;
    public PropertyDelegate propertyDelegate;

    public BrewingStonecutterTableGUIClass(int i, PlayerInventory playerInventory) {
        this(i, playerInventory, new SimpleInventory(19), new ArrayPropertyDelegate(BrewingStonecutterTableEClass.propertySize));
    }

    public BrewingStonecutterTableGUIClass(int i, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(GuiInit.BrewingStonecutterTableGUI_T, i);
        this.inventory=inventory;
        this.playerInventory=playerInventory;
        this.propertyDelegate=propertyDelegate;
        this.addProperties(propertyDelegate);
        addSlots();
    }

    public void addSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new TorchSlot(inventory, false, j + i * 3, 15 + j * 18, 17 + i * 18));
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new ResultSlot(inventory, j + i * 3 + 9, 109 + j * 18, 17 + i * 18));
            }
        }

        this.addSlot(new TorchSlot(inventory, true, 18, 80, 11));

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        /*/ItemStack stack=ItemStack.EMPTY;
        Slot actualSlot=slots.get(slot);

        if (actualSlot.hasStack()) {
            ItemStack stack1 = actualSlot.getStack();
            stack = stack1.copy();
            if (slot<9) {
                if (!insertItem(stack1, 19, 55, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot<18) {
                if (!insertItem(stack1, 19, 55, true)){
                    return ItemStack.EMPTY;
                }
            } else if (slot == 18) {
                if (!insertItem(stack1, 19, 55, false)){
                    return ItemStack.EMPTY;
                }
            } else if (slot < 46) {
                if (!insertItem(stack1, 0, 9, false) && !insertItem(stack1, 18, 19, false)){
                    return ItemStack.EMPTY;
                }
            } else if (slot < 56 && !insertItem(stack1, 19, 46, false))
                return ItemStack.EMPTY;

            if (stack1.isEmpty())
                actualSlot.setStack(ItemStack.EMPTY);

            actualSlot.markDirty();

            if (stack1.getCount() == stack.getCount())
                return ItemStack.EMPTY;

            actualSlot.onTakeItem(player, stack1);
            sendContentUpdates();
        }

        return stack;/*/
        ItemStack stack=ItemStack.EMPTY;
        Slot slotS=this.slots.get(slot);
        if (slotS.hasStack()) {
            stack=slotS.getStack();
            if (slot<19) {
                if (!this.insertItem(stack, 19, 55, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(stack, 0, 19, false)){
                    return ItemStack.EMPTY;
                }
            }
        }
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
