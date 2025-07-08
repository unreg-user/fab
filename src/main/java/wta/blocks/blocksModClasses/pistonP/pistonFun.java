package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import wta.Fun;

import static wta.blocks.PropertiesMod.NBT_DO;

public class pistonFun {
    public static class knots{
        public static BlockState getBlockState(Block block, Boolean[] boolMap){
            return block.getDefaultState()
                    .with(NBT_DO.do0, boolMap[0])
                    .with(NBT_DO.do1, boolMap[1])
                    .with(NBT_DO.do2, boolMap[2])
                    .with(NBT_DO.do3, boolMap[3])
                    .with(NBT_DO.do4, boolMap[4])
                    .with(NBT_DO.do5, boolMap[5]);
        }
        public static Boolean[] getBool(BlockState state){
            Fun.BoolMap boolMap=new Fun.BoolMap(false);
            if (state.get(NBT_DO.do0)) boolMap.set(0);
            if (state.get(NBT_DO.do1)) boolMap.set(1);
            if (state.get(NBT_DO.do2)) boolMap.set(2);
            if (state.get(NBT_DO.do3)) boolMap.set(3);
            if (state.get(NBT_DO.do4)) boolMap.set(4);
            if (state.get(NBT_DO.do5)) boolMap.set(5);
            return boolMap.get();
        }
        public static Boolean[] allFalse={false, false, false, false, false, false};
        public static Boolean[] allTrue={true, true, true, true, true, true};
    }
}
