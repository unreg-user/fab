package wta.gui.guiMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import wta.blocks.blockEntitiesModClasses.BrewingStonecutterTableEClass;
import wta.gui.GuiInit;
import wta.gui.Slots.OnlyNotSlot;
import wta.gui.Slots.OnlySlot;
import wta.gui.Slots.ResultSlot;

import java.util.List;

public class BrewingStonecutterTableGUIClass extends ScreenHandler {
    private Inventory inventory;
    private PlayerInventory playerInventory;
    private static final List<Item> tourhL=List.of(Items.TORCH);
    public PropertyDelegate propertyDelegate;
    public BrewingStonecutterTableGUIClass(int i, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(GuiInit.BrewingStonecutterTableGUI_T, i);
        this.inventory=inventory;
        this.playerInventory=playerInventory;
        this.propertyDelegate=propertyDelegate;
        this.addProperties(propertyDelegate);
        addSlots();
    }

    public void addSlots(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                this.addSlot(new OnlyNotSlot(inventory, tourhL, j+i*3, 15 + j * 18, 17 + i * 18));
            }
        }

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                this.addSlot(new ResultSlot(inventory, j+i*3+9, 109 + j * 18, 17 + i * 18));
            }
        }

        this.addSlot(new OnlySlot(inventory, tourhL, 18, 80, 11));

        for (int i=0; i<3; i++) {
            for (int j=0; j<9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i=0; i<9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public BrewingStonecutterTableGUIClass(int i, PlayerInventory playerInventory){
        this(i, playerInventory, new SimpleInventory(19), new ArrayPropertyDelegate(BrewingStonecutterTableEClass.propertySize));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack=ItemStack.EMPTY;
        Slot slotS=this.slots.get(slot);
        if (slotS.hasStack()) {
            stack=slotS.getStack();
            if (slot<19) {
                if (!this.insertItem(stack, 19, 55, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(stack, 0, 18, false)){
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
