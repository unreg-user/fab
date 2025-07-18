package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.Arrays;
import java.util.List;

import static wta.blocks.blocksModClasses.pistonP.PistonFun.Knots;

public class PistonKnotGetClass extends PistonKnotClass implements Fertilizable {
    public PistonKnotGetClass(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item useItem=stack.getItem();
        EquipmentSlot handSlot=Fun.getSlotHand.apply(hand);
        if (stack.isIn(ItemTags.AXES)) {
            world.setBlockState(pos, Knots.getBlockState(BlocksInit.pistonKnotGetStripped, Knots.getBool(state)),3);
            stack.damage(1, player, handSlot);
            playAxe(player, world, pos);
            return ItemActionResult.CONSUME;
        } else if (useItem==Items.SHEARS) {
            if (!Arrays.equals(getBool(state), Knots.allFalse)) {
                world.setBlockState(pos, getBlockState(Knots.allFalse), 3);
                stack.damage(1, player, handSlot);
                playShears(player, world, pos);
                return ItemActionResult.CONSUME;
            }
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected void playAxe(@Nullable PlayerEntity player, World world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    protected void playShears(@Nullable PlayerEntity player, World world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return !Arrays.equals(getBool(state), Knots.allTrue);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        List<Boolean> boolMap = List.of(Knots.getBool(state));
        int fbool = (int) boolMap.stream().filter(e -> e == false).count();
        int index = Fun.getIndexIndex(boolMap, false, world.getRandom().nextInt(fbool));
        world.setBlockState(pos, setBlockState(state, index, true), 3);
    }
}