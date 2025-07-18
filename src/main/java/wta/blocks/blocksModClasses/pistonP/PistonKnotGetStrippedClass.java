package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wta.Fun;

import java.util.Arrays;

public class PistonKnotGetStrippedClass extends PistonKnotGetClass {
    public PistonKnotGetStrippedClass(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item useItem=stack.getItem();
        if (useItem==Items.SHEARS) {
            if (!Arrays.equals(getBool(state), PistonFun.Knots.allFalse)) {
                EquipmentSlot handSlot=Fun.getSlotHand.apply(hand);
                world.setBlockState(pos, getBlockState(PistonFun.Knots.allFalse), 3);
                stack.damage(1, player, handSlot);
                playShears(player, world, pos);
                return ItemActionResult.CONSUME;
            }
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
