package wta.blocks.blockEntitiesModClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import wta.blocks.BlocksInit;
import wta.gui.guiMod.BrewingStonecutterTableGUIClass;
import wta.recipe_types.RecipeInit;

public class BrewingStonecutterTableEClass extends LootableContainerBlockEntity implements NamedScreenHandlerFactory{
    private int clicks=0;
    private PropertyDelegate properties;
    public static final int propertySize=1;
    public static final int maxClicks=5;
    //Items.TORCH
    /**
     * 0-8 is START slots<br>
     * 9-17 is FINISH slots<br>
     * 18 is for torch
    */
    private DefaultedList<ItemStack> inventory=DefaultedList.ofSize(19, ItemStack.EMPTY);

    public BrewingStonecutterTableEClass(BlockPos pos, BlockState state) {
        super(BlocksInit.brewingStonecutterTableBE, pos, state);
        this.properties=new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0 -> {
                        return clicks;
                    }
                    default -> {
                        return 0;
                    }
                }
            }
            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> {
                        clicks=value;
                    }
                }
            }
            @Override
            public int size() {
                return propertySize;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putInt("clicks", clicks);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        clicks=nbt.getInt("clicks");
        inventory=DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public void onClick(){
        clicks++;
        if (clicks>=maxClicks){
            clicks=0;
            onMaxClick();
        }
    }

    public void onMaxClick() {
        boolean result=false;
        ItemStack torchs=this.getStack(18);
        if (torchs != ItemStack.EMPTY){
            for (int i = 0; i < 9; i++) {
                if (tryCraft(i)){
                    result=true;
                }
            }
            if (result){
                torchs.decrement(1);
            }
        }
    }

    protected Boolean tryCraft(int slot){
        if (slot>0 && slot<9) {
            int slot2=slot+9;
            ItemStack stack=this.getStack(slot);
            if (stack!=ItemStack.EMPTY) {
                ItemStack stack2=this.getStack(slot2);
                ItemStack result=RecipeManager.createCachedMatchGetter(RecipeInit.brewingStonecutterTableRT).getFirstMatch(new SingleStackRecipeInput(stack), this.getWorld())
                        .map(recipeEntry -> recipeEntry.value().getResult(world.getRegistryManager()))
                        .orElse(ItemStack.EMPTY).copy();
                Item resultI=result.getItem();
                if (result!=ItemStack.EMPTY){
                    boolean ret=false;
                    if (stack2==ItemStack.EMPTY){
                        this.setStack(slot2, result);
                        ret=true;
                    }else if (stack2.getItem()==resultI){
                        int countN=stack2.getCount()+result.getCount();
                        if (countN<=resultI.getMaxCount()){
                            stack2.setCount(countN);
                            ret=true;
                        }
                    }
                    if (ret){
                        stack.decrement(1);
                    }
                    return ret;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return 19;
    }

    @Override
    protected Text getContainerName() {
        return Text.literal("")
                .append(Text.translatable("container.furnace"))
                .append(", ")
                .append(Text.translatable("block.minecraft.torch"));
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory=inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BrewingStonecutterTableGUIClass(syncId, playerInventory, this, properties);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BrewingStonecutterTableGUIClass(i, playerInventory, this, properties);
    }
}
