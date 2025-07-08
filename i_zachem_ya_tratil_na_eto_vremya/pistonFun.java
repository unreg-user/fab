package i_zachem_ya_tratil_na_eto_vremya;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.Arrays;
import java.util.HashMap;

public class pistonFun {
    public static HashMap<Direction, HashMap<Direction, BlockState>> normToCodeKnotBS1=new HashMap<>();
    ublic static HashMap<Direction, HashMap<Direction, BlockState>> normToCodeKnotBS2=new HashMap<>();
    public static void initFuns(){
        initQ1();
        initQ2();
    }
    private static void initQ1(){
        for (Direction rotate : Fun.dirs){
            normToCodeKnotBS1.put(rotate, new HashMap<>());
            for (Direction rotate2 : Fun.dirs){
                if (rotate==rotate2 || rotate==rotate2.getOpposite()){
                    continue;
                }
                Boolean[] boolMap={false, false, false, false, false, false};
                boolMap[Fun.dirs.indexOf(rotate.getOpposite())]=true;
                boolMap[Fun.dirs.indexOf(rotate2)]=true;
                HashMap<Direction, Boolean[]> writerGet=pistonKnot1Class.HITBOXES_RETS.get(rotate);
                for (Direction dirI : Fun.h_dirs){
                    Boolean[] boolMapI=writerGet.get(dirI);
                    if (Arrays.deepEquals(boolMap, boolMapI)){
                        normToCodeKnotBS1.get(rotate).put(rotate2, BlocksInit.pistonKnot1.getDefaultState()
                                .with(pistonKnot1Class.ROTATE, rotate)
                                .with(pistonKnot1Class.H_ROTATE, dirI)
                        );
                    }
                }
            }
        }
    }
    private static void initQ2(){
        for (Direction rotate : Fun.dirs){
            normToCodeKnotBS2.put(rotate, new HashMap<>());
            for (Direction rotate2 : Fun.dirs){
                if (rotate==rotate2 || rotate==rotate2.getOpposite()){
                    continue;
                }
                Boolean[] boolMap={false, false, false, false, false, false};
                boolMap[Fun.dirs.indexOf(rotate.getOpposite())]=true;
                boolMap[Fun.dirs.indexOf(rotate2)]=true;
                boolMap[Fun.dirs.indexOf(
                        Fun.h_dirs.get(
                                Fun.norm(Fun.h_dirs.indexOf(rotate2)+1, 4)
                        )
                )]=true;
                HashMap<Direction, Boolean[]> writerGet=pistonKnot1Class.HITBOXES_RETS.get(rotate);
                for (Direction dirI : Fun.h_dirs){
                    Boolean[] boolMapI=writerGet.get(dirI);
                    if (Arrays.deepEquals(boolMap, boolMapI)){
                        normToCodeKnotBS2.get(rotate).put(rotate2, BlocksInit.pistonKnot1.getDefaultState()
                                .with(pistonKnot1Class.ROTATE, rotate)
                                .with(pistonKnot1Class.H_ROTATE, dirI)
                        );
                    }
                }
            }
        }
    }
}
