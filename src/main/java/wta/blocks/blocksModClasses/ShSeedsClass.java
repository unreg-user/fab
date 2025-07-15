package wta.blocks.blocksModClasses;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.HashMap;

public class ShSeedsClass extends PlantBlock implements Fertilizable {
    public static final MapCodec<ShSeedsClass> CODEC=createCodec(ShSeedsClass::new);
    public static final int MAX_AGE=2;
    public static final IntProperty AGE=Properties.AGE_2;
    private static final HashMap<Direction, VoxelShape[]> AGE_TO_SHAPE;
    private static DirectionProperty ROTATE=Properties.FACING;

    public MapCodec<? extends ShSeedsClass> getCodec() {
        return CODEC;
    }

    public ShSeedsClass(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0).with(ROTATE, Direction.UP));
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE.get(state.get(ROTATE))[this.getAge(state)];
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(BlocksInit.allGrass);
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty());
    }

    public Direction getRotate(BlockState state) {
        return state.get(ROTATE);
    }

    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public final boolean isMature(BlockState state) {
        return this.getAge(state) >= this.getMaxAge();
    }

    protected boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                if (random.nextInt(3) == 0) {
                    world.setBlockState(pos, state.with(AGE, i + 1), 2);
                }
            }
        }

    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        world.setBlockState(pos, this.withAge(i).with(ROTATE, this.getRotate(state)), 2);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 2);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        try {
            if (ctx.getWorld().getBlockState(Fun.getClickedBlockPos(ctx)).getBlock() == BlocksInit.allGrass) {
                return this.getDefaultState().with(ROTATE, ctx.getSide());
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos onPos=Fun.getBlockByF(pos, state.get(ROTATE).getOpposite());
        if (world.getBlockState(onPos).getBlock()==BlocksInit.allGrass){
            return true;
        }else{
            return false;
        }
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    protected ItemConvertible getSeedsItem() {
        return Items.WHEAT_SEEDS;
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !this.isMature(state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE).add(ROTATE);
    }

    static {
        AGE_TO_SHAPE = new HashMap<>(){{
            put(Direction.UP,    Fun.HalfBlocksGen(Direction.UP,    2, 1, 3));
            put(Direction.DOWN,  Fun.HalfBlocksGen(Direction.DOWN,  2, 1, 3));
            put(Direction.NORTH, Fun.HalfBlocksGen(Direction.NORTH, 2, 1, 3));
            put(Direction.SOUTH, Fun.HalfBlocksGen(Direction.SOUTH, 2, 1, 3));
            put(Direction.WEST,  Fun.HalfBlocksGen(Direction.WEST,  2, 1, 3));
            put(Direction.EAST,  Fun.HalfBlocksGen(Direction.EAST,  2, 1, 3));
        }};
    }
}

