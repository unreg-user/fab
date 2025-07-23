package wta.blocks.blocksModClasses.stick_detectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import wta.blocks.PropertiesMod;
import wta.blocks.interfaces.StickUsed;

public abstract class StickDetectorBlock extends Block implements StickUsed {
    protected static final IntProperty POWER=PropertiesMod.STICK_POWER;
    protected int stopTicks=3;
    protected Random rand=Random.create();

    public StickDetectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(POWER, 0)
        );
    }

    public StickDetectorBlock(Settings settings, int stopTicks) {
        super(settings);
        this.stopTicks=stopTicks;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    public boolean useAnimation(ItemUsageContext context) {
        return true;
    }

    public int getStickLevel(ItemStack stack){
        int level=0;
        if (stack.getItem()!= Items.STICK){
            return -1;
        }
        if (stack.getCount()==1){
            level+=1;
        }
        if (stack.get(DataComponentTypes.CUSTOM_NAME)!=null){
            level+=1;
        }
        return level;
    }

    public int getStickLevel02(ItemStack stack){
        int level=0;
        if (stack.getItem()!=Items.STICK){
            return 0;
        }
        if (stack.getCount()==1){
            level+=1;
        }
        if (stack.get(DataComponentTypes.CUSTOM_NAME)!=null){
            level+=1;
        }
        return level;
    }

    public int getStickPower(ItemStack stack, Random random){
        int level=getStickLevel(stack);
        if (level==-1){
            return 0;
        }else if (level==0){
            return random.nextInt(2);
        }else if (level==1){
            return 1;
        }else{
            return random.nextInt(2)+1;
        }
    }

    public int getStickRedstonePower(int level){
        if (level==0){
            return 0;
        }
        return 8*level-1;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(POWER, 0), 3);
    }
}
