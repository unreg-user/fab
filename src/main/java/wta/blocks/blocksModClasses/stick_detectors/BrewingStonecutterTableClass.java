package wta.blocks.blocksModClasses.stick_detectors;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wta.blocks.blockEntitiesModClasses.BrewingStonecutterTableEClass;

public class BrewingStonecutterTableClass extends StickDetectorBlock implements BlockEntityProvider {
    public BrewingStonecutterTableClass(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingStonecutterTableEClass(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity be=world.getBlockEntity(pos);
            if (be instanceof NamedScreenHandlerFactory nbe){
                player.openHandledScreen(nbe);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //a magic string of code
        if (!(world.getBlockEntity(pos) instanceof BrewingStonecutterTableEClass blockEn)) {
            return ItemActionResult.FAIL;
        }
        Item item=stack.getItem();
        if (item==Items.STICK){
            int power=getStickPower(stack, rand);
            if (power<=0){
                return ItemActionResult.SUCCESS;
            }
            blockEn.onClick(power);
            world.setBlockState(pos, state.with(POWER, power), 3);
            world.scheduleBlockTick(pos, this, stopTicks);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
