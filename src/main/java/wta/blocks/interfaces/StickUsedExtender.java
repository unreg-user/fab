package wta.blocks.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface StickUsedExtender extends StickUsed {
    @Override
    default boolean useAnimation(ItemUsageContext context){
        World world=context.getWorld();
        BlockPos pos=context.getBlockPos();
        return onUseWithStick(
                context.getStack(),
                world.getBlockState(pos),
                world,
                pos,
                context.getPlayer(),
                context.getHand(),
                context
        );
    };

    boolean onUseWithStick(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemUsageContext context);
}
