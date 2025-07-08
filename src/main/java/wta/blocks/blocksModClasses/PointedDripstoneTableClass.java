package wta.blocks.blocksModClasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import wta.Fun;

import static wta.items.ItemsModClasses.BowDripstoneClass.dripstones;

public class PointedDripstoneTableClass extends Block {
    public PointedDripstoneTableClass(Settings settings) {
        super(settings);
    }

    protected void playUse(@Nullable PlayerEntity player, World world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, GameEvent.ITEM_INTERACT_FINISH, pos);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item=stack.getItem();
        if (item==Items.STICK) {
            Boolean doing=false;
            for (Direction dirI : Fun.h_dirs) {
                BlockPos pos2 = Fun.getBlockByF(pos, dirI);
                BlockState state2 = world.getBlockState(pos2);
                Item blockI2 = state2.getBlock().asItem();
                if (dripstones.contains(blockI2)) {
                    world.setBlockState(pos2, Fun.air, 3);
                    ItemStack stack2 = new ItemStack(blockI2);
                    BlockStateComponent bsc2 = Fun.bsToComponent(state2);
                    stack2.set(DataComponentTypes.BLOCK_STATE, bsc2);
                    ItemEntity loot2 = new ItemEntity(world, pos2.getX(), pos2.getY(), pos2.getZ(), stack2);
                    world.spawnEntity(loot2);
                    doing=true;
                }
            }
            if (doing) {
                playUse(player, world, pos);
                return ItemActionResult.SUCCESS;
            }
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
