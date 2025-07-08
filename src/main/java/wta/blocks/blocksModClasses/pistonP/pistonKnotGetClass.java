package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static wta.blocks.blocksModClasses.pistonP.pistonFun.knots;

public class pistonKnotGetClass extends pistonKnotClass{
    public pistonKnotGetClass(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item useItem=stack.getItem();
        EquipmentSlot hand2 = hand==Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        if (stack.isIn(ItemTags.AXES)){
            world.setBlockState(pos, knots.getBlockState(BlocksInit.pistonKnotGetStripped, knots.getBool(state)),3);
            stack.damage(1, player, hand2);
            return ItemActionResult.CONSUME;
        }else if (useItem==Items.SHEARS) {
            if (!Arrays.equals(knots.getBool(state), knots.allFalse)) {
                world.setBlockState(pos, knots.getBlockState(BlocksInit.pistonKnotGet, knots.allFalse), 3);
                stack.damage(1, player, hand2);
                return ItemActionResult.CONSUME;
            }else{
                return ItemActionResult.FAIL;
            }
        }else if (useItem==Items.BONE_MEAL){
            ArrayList<Boolean> boolMap=new ArrayList<>(List.of(knots.getBool(state)));
            int fbool=(int) boolMap.stream().filter(e -> e==false).count();
            if (fbool!=0){
                boolMap.set(
                        Fun.getIndexIndex(boolMap, false, world.getRandom().nextInt(fbool)),
                        true
                );
                world.setBlockState(pos, knots.getBlockState(BlocksInit.pistonKnotGet, boolMap.toArray(Boolean[]::new)));
                stack.decrementUnlessCreative(1, player);
                return ItemActionResult.CONSUME;
            }else{
                return ItemActionResult.FAIL;
            }
        }
        return ItemActionResult.FAIL;
    }
}