package wta.blocks.blockEntitiesModClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
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
import wta.gui.guiMod.brewingStonecutterTable.BrewingStonecutterTableGUIClass;
import wta.recipeTypes.RecipeInit;
import wta.recipeTypes.modRecipes.BrewingStonecutterTableRecipe;

import java.util.Optional;

public class BrewingStonecutterTableEClass extends LootableContainerBlockEntity implements NamedScreenHandlerFactory{
    private int clicks=0;
    private int maxClicks=5;
    private final PropertyDelegate properties;
    public static final int propertySize=2;
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
                    case 1 -> {
                        return maxClicks;
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
                    case 1 -> {
                        maxClicks=value;
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
        nbt.putInt("max_clicks", maxClicks);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        clicks=nbt.getInt("clicks");
        maxClicks=nbt.getInt("max_clicks");
        inventory=DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public void onClick(int count){
        clicks+=count;
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
        if (slot>-1 && slot<9) {
            int slot2=slot+9;
            ItemStack stack=this.getStack(slot);
            if (stack!=ItemStack.EMPTY) {
                ItemStack stack2=this.getStack(slot2);
                SingleStackRecipeInput stackInput=new SingleStackRecipeInput(stack);
                Optional<RecipeEntry<BrewingStonecutterTableRecipe>> recipe=world.getRecipeManager()
                        .getFirstMatch(RecipeInit.brewingStonecutterTableRT, stackInput, world);
                if (recipe.isPresent()){
                    ItemStack result=recipe.get().value().craft(stackInput, world.getRegistryManager());
                    Item resultI=result.getItem();
                    if (result!=ItemStack.EMPTY){
                        boolean ret=false;
                        if (stack2.isEmpty()){
                            this.setStack(slot2, result);
                            ret=true;
                        }else if (stack2.getItem()==resultI){
                            int countN=stack2.getCount()+1;
                            if (countN<=stack2.getMaxCount()){
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
