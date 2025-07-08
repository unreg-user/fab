package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.ArrayList;
import java.util.HashMap;

import static wta.blocks.PropertiesMod.IN_TREE;

public class pistonUpClass extends PistonTreeBlock {
    private static DirectionProperty ROTATE=Properties.FACING;
    public static int LenghtIn=10;
    public static HashMap<Direction, VoxelShape> HITBOXES=new HashMap<>();
    public pistonUpClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATE, Direction.UP));
    }

    private static int forBack(BlockState state, ServerWorld world, BlockPos pos){
        int ret=0;
        BlockPos posI=pos;
        BlockState StateI;
        do{
            posI=Fun.getBlockByF(posI, state.get(ROTATE).getOpposite());
            StateI=world.getBlockState(posI);
            ret+=1;
        }while (StateI.getBlock()==BlocksInit.pistonIn && StateI.get(ROTATE)==state.get(ROTATE));
        return ret;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        try {
            BlockPos pos2=Fun.getBlockByF(pos, state.get(ROTATE));
            BlockState state2=world.getBlockState(pos2);
            Direction rotate=state.get(ROTATE);
            Direction un_rotate=rotate.getOpposite();
            if (random.nextInt(2)==0) {
                if (state2.getBlock() == Blocks.AIR) {
                    int LenghtInNow = forBack(state, world, pos);
                    if (LenghtInNow < LenghtIn) {
                        world.setBlockState(pos2, BlocksInit.pistonUp.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                    } else {
                        world.setBlockState(pos2, BlocksInit.pistonFlower.getDefaultState().with(IN_TREE, true), 3);
                    }
                    world.setBlockState(pos, BlocksInit.pistonIn.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                }else if(state2.getBlock()==BlocksInit.pistonKnot && random.nextInt(3)>0) {
                    Fun.BoolMap boolMap2=new Fun.BoolMap(pistonKnotClass.getBool(state2));
                    boolMap2.set(un_rotate);
                    world.setBlockState(pos2, pistonKnotClass.getBlockState(boolMap2.get()), 3);
                    world.setBlockState(pos, BlocksInit.pistonIn.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                }else if(state2.getBlock()==BlocksInit.pistonIn && random.nextInt(3)>0){
                    Direction rotate2 = state2.get(ROTATE);
                    Fun.BoolMap boolMap2 = new Fun.BoolMap(false);
                    boolMap2
                            .set(rotate2)
                            .set(rotate2.getOpposite())
                            .set(un_rotate, true);
                    world.setBlockState(pos2, pistonKnotClass.getBlockState(boolMap2.get()), 3);
                    world.setBlockState(pos, BlocksInit.pistonIn.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                }else if (BlocksInit.pistonBlocksForSet.contains(state2.getBlock())){
                    world.setBlockState(pos2, BlocksInit.pistonCompKnot.getDefaultState().with(IN_TREE, true), 3);
                    world.setBlockState(pos, BlocksInit.pistonIn.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                }else {
                    ArrayList<Direction> trys = (ArrayList<Direction>) Fun.AllTrys.clone();
                    trys.remove(rotate);
                    trys.remove(un_rotate);
                    ArrayList<Direction> retTrys = new ArrayList<>();
                    for (Direction dirI : trys) {
                        try {
                            BlockPos pos3 = Fun.getBlockByF(pos, dirI);
                            if (world.getBlockState(pos3).getBlock() == Blocks.AIR) {
                                world.setBlockState(pos3, BlocksInit.pistonUp.getDefaultState().with(ROTATE, dirI), 3);
                                retTrys.add(dirI);
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (!retTrys.isEmpty()){
                        Fun.BoolMap boolMap=new Fun.BoolMap(false);
                        boolMap.set(un_rotate, true);
                        for (Direction dirI : retTrys){
                            boolMap.set(dirI, true);
                        }
                        world.setBlockState(
                                pos,
                                pistonKnotClass.getBlockState(boolMap.get()),
                                3
                        );
                    }else{
                        world.setBlockState(pos, BlocksInit.pistonUpStoped.getDefaultState().with(ROTATE, state.get(ROTATE)));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos pos2=Fun.getBlockByF(pos, state.get(ROTATE).getOpposite());
        return BlocksInit.pistonBlocks.contains(world.getBlockState(pos2).getBlock());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return  HITBOXES.get(state.get(ROTATE));
    }

    static{
        for (int i = 0; i < 6; i++) {
            Direction dirI=Fun.dirs.get(i);
            HITBOXES.put(dirI,
                    VoxelShapes.union(
                            Fun.HalfBlockGen(dirI.getOpposite(), 4, 1),
                            pistonInClass.HITBOXES.get(dirI)
                    )
            );
        }
    }
}
