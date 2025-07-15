package wta;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static wta.Fab.MODID;

public class Fun {
    /*/public static Boolean[] toBoolMap(Direction[] trues){
        Boolean[] boolMap={false, false, false, false, false, false};
        for (Direction dirI : trues){
            boolMap[dirs.indexOf(dirI)]=true;
        }
        return boolMap;
    }
    public static Boolean[] toBoolMapF(Direction[] trues){
        Boolean[] boolMap={true, true, true, true, true, true};
        for (Direction dirI : trues){
            boolMap[dirs.indexOf(dirI)]=false;
        }
        return boolMap;
    }/*/

    public static <T> T[] combArrays(T[]... elems){
        return (T[]) (Arrays.stream(elems)
                .flatMap(Stream::of)
                .toArray(Object[]::new));
    }

    public static class BoolMap{
        public Boolean[] bools;

        public BoolMap(boolean inner){
            bools=new Boolean[6];
            Arrays.fill(bools, inner);
        }

        public BoolMap(Boolean[] inner){
            bools=inner;
        }

        public BoolMap set(Direction pos){
            bools[dirs.indexOf(pos)]=!bools[dirs.indexOf(pos)];
            return this;
        }

        public BoolMap set(Direction pos, boolean newB){
            bools[dirs.indexOf(pos)]=newB;
            return this;
        }

        public BoolMap set(int pos){
            bools[pos]=!bools[pos];
            return this;
        }

        public BoolMap set(int pos, boolean newB){
            bools[pos]=newB;
            return this;
        }

        public Boolean[] get(){
            return bools;
        }
    }

    public static List<Direction> dirs=List.of(
            Direction.EAST,     //x+
            Direction.WEST,     //x-
            Direction.UP,       //y+
            Direction.DOWN,     //y-
            Direction.SOUTH,    //z+
            Direction.NORTH     //z-
    );

    public static List<Direction> h_dirs=List.of(
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.NORTH
    );

    public static List<Integer> getOppositeDir=List.of(1, 0, 3, 2, 5, 4);

    public static <T> List<Integer> getIndeices(List<T> list, T target) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i), target)) {
                indices.add(i);
            }
        }
        return indices;
    }

    public static <T> int getIndexIndex(List<T> list, T target, int pos){
        return getIndeices(list, target).get(pos);
    }

    public static int norm(int pos, int max){
        if (pos >= max){
            return 0;
        }else{
            return pos;
        }
    }

    public static HashMap<Direction, VoxelShape> knotHit=new HashMap<>(){{
        //put(Direction, Block.createCuboidShape(6, 6, 6, 10, 10, 10));
        put(Direction.UP, Block.createCuboidShape(6, 6, 6, 10, 16, 10));
        put(Direction.DOWN, Block.createCuboidShape(6, 0, 6, 10, 10, 10));// Направлено на север (вглубь экрана)
        put(Direction.NORTH, Block.createCuboidShape(6, 6, 0, 10, 10, 10));
        put(Direction.SOUTH, Block.createCuboidShape(6, 6, 6, 10, 10, 16));
        put(Direction.WEST,  Block.createCuboidShape(0, 6, 6, 10, 10, 10));
        put(Direction.EAST,  Block.createCuboidShape(6, 6, 6, 16, 10, 10));
    }};

    public static VoxelShape getKnotHit(boolean east, boolean west, boolean up, boolean down, boolean south, boolean north) {
        VoxelShape shape = Block.createCuboidShape(6, 6, 6, 10, 10, 10);
        if (up) shape = VoxelShapes.union(shape, knotHit.get(Direction.UP));
        if (down) shape = VoxelShapes.union(shape, knotHit.get(Direction.DOWN));
        if (north) shape = VoxelShapes.union(shape, knotHit.get(Direction.NORTH));
        if (south) shape = VoxelShapes.union(shape, knotHit.get(Direction.SOUTH));
        if (west) shape = VoxelShapes.union(shape, knotHit.get(Direction.WEST));
        if (east) shape = VoxelShapes.union(shape, knotHit.get(Direction.EAST));
        return shape;
    }

    public static VoxelShape getKnotHitGen(boolean east, boolean west, boolean up, boolean down, boolean south, boolean north){
        return knotHitGen.get(east).get(west).get(up).get(down).get(south).get(north);
    }

    public static VoxelShape getKnotHitGen(Boolean[] all){
        return knotHitGen
                .get(all[0])
                .get(all[1])
                .get(all[2])
                .get(all[3])
                .get(all[4])
                .get(all[5]);
    }

    public static HashMap<
                Boolean, HashMap<
                    Boolean, HashMap<
                        Boolean, HashMap<
                            Boolean, HashMap<
                                Boolean, HashMap<
                                    Boolean, VoxelShape
                                >
                            >
                        >
                    >
                >
            > knotHitGen=new HashMap<>();

    public static boolean[] bools=new boolean[]{true, false};

    public static void initFun(){
        for (boolean i1 : bools){
            knotHitGen.put(i1, new HashMap<>());
            for (boolean i2 : bools){
                knotHitGen.get(i1).put(i2, new HashMap<>());
                for (boolean i3 : bools){
                    knotHitGen.get(i1).get(i2).put(i3, new HashMap<>());
                    for (boolean i4 : bools){
                        knotHitGen.get(i1).get(i2).get(i3).put(i4, new HashMap<>());
                        for (boolean i5 : bools){
                            knotHitGen.get(i1).get(i2).get(i3).get(i4).put(i5, new HashMap<>());
                            for (boolean i6 : bools){
                                knotHitGen.get(i1).get(i2).get(i3).get(i4).get(i5).put(i6, getKnotHit(i1, i2, i3, i4, i5, i6));
                            }
                        }
                    }
                }
            }
        }
    }

    public static Function<Hand, EquipmentSlot> getSlotHand= (hand) -> hand==Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

    public static ArrayList<Direction> AllTrys=new ArrayList<>(List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.SOUTH,
            Direction.NORTH,
            Direction.WEST,
            Direction.EAST
    ));

    public static BlockPos getBlockByF(BlockPos pos, Direction rotate){
        return switch (rotate) {
            case UP -> pos.up();
            case DOWN -> pos.down();
            case EAST -> pos.east();
            case WEST -> pos.west();
            case NORTH -> pos.north();
            case SOUTH -> pos.south();
        };
    }

    public static BlockPos getClickedBlockPos(ItemPlacementContext ctx){
        BlockPos pos=ctx.getBlockPos();
        Direction depos=ctx.getSide().getOpposite();
        return getBlockByF(pos, depos);
    }

    public static HashMap<BlockHalf, Integer> halfToInt =new HashMap<>(){{
        put(BlockHalf.TOP, 1);
        put(BlockHalf.BOTTOM, -1);
    }};

    public static HashMap<BlockHalf, BlockHalf> halfOpposite =new HashMap<>(){{
        put(BlockHalf.TOP, BlockHalf.BOTTOM);
        put(BlockHalf.BOTTOM, BlockHalf.TOP);
    }};

    public static VoxelShape HalfBlockGen(Direction rotate, float mul, float state){
        return switch (rotate) {
            case UP -> Block.createCuboidShape(0F, 0F, 0F, 16F, mul * state, 16F);
            case DOWN -> Block.createCuboidShape(0F, 16F - mul * state, 0F, 16F, 16F, 16F);
            case SOUTH -> Block.createCuboidShape(0F, 0F, 0F, 16F, 16F, mul * state);
            case NORTH -> Block.createCuboidShape(0F, 0F, 16F - mul * state, 16F, 16F, 16F);
            case EAST -> Block.createCuboidShape(0F, 0F, 0F, mul * state, 16F, 16F);
            case WEST -> Block.createCuboidShape(16F - mul * state, 0F, 0F, 16F, 16F, 16F);
        };
    }

    public static VoxelShape[] HalfBlocksGen(Direction rotate, int mul, int start, int stop){
        List<VoxelShape> ret= new ArrayList<>();
        for (int i = start; i <= stop; i++) {
            ret.add(HalfBlockGen(rotate, mul, i));
        }
        return ret.toArray(new VoxelShape[0]);
    }

    public static Boolean equalsBlockStateGroup(BlockState state1, BlockState state2){
        if (state1.getBlock().getClass()==state2.getBlock().getClass()){
            for (Property pI : state1.getProperties()){
                if (state1.get(pI)!=state2.get(pI)){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }

    public static HashMap<String, BlockSetType> treeToSound=new HashMap<>(){{
        put("birch", BlockSetType.BIRCH);
        put("oak", BlockSetType.OAK);
        put("acacia", BlockSetType.ACACIA);
        put("jungle", BlockSetType.JUNGLE);
        put("bamboo", BlockSetType.BAMBOO);
        put("cherry", BlockSetType.CHERRY);
        put("copper", BlockSetType.COPPER);
        put("crimson", BlockSetType.CRIMSON);
        put("dark_oak", BlockSetType.DARK_OAK);
        put("mangrove", BlockSetType.MANGROVE);
        put("warped", BlockSetType.WARPED);
        put("spruce", BlockSetType.SPRUCE);
    }};

    public static HashMap<String, BlockSetType> doorTrapdoorToSound =new HashMap<>(){{
        put("birch", BlockSetType.BIRCH);
        put("oak", BlockSetType.OAK);
        put("acacia", BlockSetType.ACACIA);
        put("jungle", BlockSetType.JUNGLE);
        put("bamboo", BlockSetType.BAMBOO);
        put("cherry", BlockSetType.CHERRY);
        put("copper", BlockSetType.COPPER);
        put("crimson", BlockSetType.CRIMSON);
        put("dark_oak", BlockSetType.DARK_OAK);
        put("mangrove", BlockSetType.MANGROVE);
        put("warped", BlockSetType.WARPED);
        put("spruce", BlockSetType.SPRUCE);
        put("iron", BlockSetType.IRON);
    }};

    public static Boolean equalsBlockStateGroup(BlockState state1, BlockState state2, Property[] check_list){
        if (state1.getBlock().getClass()==state2.getBlock().getClass()){
            for (Property pI : check_list){
                if (state1.get(pI)!=state2.get(pI)){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }

    public static Block getBlockById(String type, String name){
        return Registries.BLOCK.get(Identifier.of(type, name));
    }

    @Nullable
    public static BlockState getStackState(ItemStack stack){
        Item item=stack.getItem();
        if (!(item instanceof BlockItem blockI)){
            return null;
        }
        Block block=blockI.getBlock();
        BlockState defaultBS=block.getDefaultState();
        BlockStateComponent stack_state=stack.get(DataComponentTypes.BLOCK_STATE);
        if (stack_state!=null){
            return stack_state.applyToState(defaultBS);
        }
        return defaultBS;
    }

    @Nullable
    public static BlockState getStackState(ItemStack stack, Function<BlockState, BlockState> defaultRule){
        Item item=stack.getItem();
        if (!(item instanceof BlockItem blockI)){
            return null;
        }
        Block block=blockI.getBlock();
        BlockState defaultBS=defaultRule.apply(block.getDefaultState());
        BlockStateComponent stack_state=stack.get(DataComponentTypes.BLOCK_STATE);
        if (stack_state!=null){
            return stack_state.applyToState(defaultBS);
        }
        return defaultBS;
    }

    public static BlockStateComponent bsToComponent(BlockState state){
        HashMap<String, String> stateString=new HashMap<>();
        for (Property pI : state.getProperties()){
            stateString.put(pI.getName(), state.get(pI).toString());
        }
        return new BlockStateComponent(stateString);
    }

    public static void testP(@Nullable Integer number){
        if (number!=null){
            System.out.println("Odnii!@"+number.toString());
        }else{
            System.out.println("Odnii!@");
        }
    }

    public static <T> ArrayList<ArrayList<T>> deepcopy2D(ArrayList<ArrayList<T>> list){
        ArrayList<ArrayList<T>> ret=new ArrayList<>();
        for (ArrayList<T> list2 : list){
            ret.add(new ArrayList<>(list2));
        }
        return ret;
    }

    public static class VoxelShapeR{
        private static final double converter=0.5D;
        private static final double mconverter=-converter;
        private ArrayList<ArrayList<Double>> voxels;
        private VoxelShapeR(ArrayList<ArrayList<Double>> voxels, boolean bMove){
            this.voxels=voxels;
            if (bMove) {
                move(mconverter);
            }
        }
        public VoxelShapeR(ArrayList<ArrayList<Double>> voxels){
            this(voxels, true);
        }
        public VoxelShapeR(List<List<Double>> voxels){
            this(listToArrayList2D(voxels));
        }
        public VoxelShapeR(VoxelShape shape){
            this(ShapesR.getVoxelsA(shape));
        }
        public VoxelShapeR copy(){
            return new VoxelShapeR(deepcopy2D(this.voxels), false);
        }
        public void move(double xyz){
            voxels=new  ArrayList<>(
                    voxels.stream().map(
                            (e) -> new ArrayList<>(
                                    e.stream().map(
                                            (e2) -> e2+xyz
                                    ).toList()
                            )
                    ).toList()
            );
        }
        public VoxelShapeR mirror(Direction.Axis axis){
            return mirror(axisToInt(axis));
        }
        public VoxelShapeR mirror(int axis){
            for (List<Double> voxel : voxels){
                voxel.set(axis, -voxel.get(axis));
                voxel.set(axis+3, -voxel.get(axis+3));

            }
            return this;
        }
        private void rotate(int axis, int axis2){
            int axisN=axis+3;
            int axisN2=axis2+3;
            for (List<Double> voxel : voxels){
                rotateOp(voxel, axis, axis2);
                rotateOp(voxel, axisN, axisN2);
            }
        }
        private void rotateOp(List<Double> list, int pos, int pos2){
            double[] listOp={list.get(pos2), -list.get(pos)};
            list.set(pos, listOp[0]);
            list.set(pos2, listOp[1]);
        }
        private void normMinMax(List<Double> list, int pos, int pos2){
            double p=list.get(pos);
            double p2=list.get(pos2);
            list.set(pos, Math.min(p, p2));
            list.set(pos2, Math.max(p, p2));
        }
        public VoxelShapeR rotate(int deg, int axis, int axis2){
            if (axis!=axis2) {
                if (deg > 0) {
                    for (int i = 0; i < deg; i++) {
                        rotate(axis, axis2);
                    }
                } else {
                    for (int i = 0; i > deg; i--) {
                        rotate(axis2, axis);
                    }
                }
            }
            return this;
        }
        public VoxelShapeR rotate(int deg, Direction.Axis axis, Direction.Axis axis2){
            return rotate(deg, axisToInt(axis), axisToInt(axis2));
        }
        public VoxelShapeR rotate(int deg, Direction dir, Direction dir2){
            int mul=-dir.getDirection().offset()*dir2.getDirection().offset();
            return rotate(deg*mul, dir.getAxis(), dir2.getAxis());
        }
        private void allFix(){
            for (List<Double> voxel : voxels){
                normMinMax(voxel, 0, 3);
                normMinMax(voxel, 1, 4);
                normMinMax(voxel, 2, 5);
            }
        }
        public VoxelShape getShape(){
            move(converter);
            allFix();
            return ShapesR.getVoxelShape(voxels);
        }
        public static HashMap<Direction, VoxelShape> rotateMap(Direction dir, VoxelShape shape){
            return rotateMap(dir, new VoxelShapeR(shape), shape);
        }
        public static HashMap<Direction, VoxelShape> rotateMap(Direction dir, VoxelShapeR shape){
            return rotateMap(dir, shape, shape.getShape());
        }
        private static HashMap<Direction, VoxelShape> rotateMap(Direction dir, VoxelShapeR shapeR, VoxelShape shape){
            ArrayList<Direction> dirs=new ArrayList<>(Fun.dirs);
            HashMap<Direction, VoxelShape> ret=new HashMap<>();
            dirs.remove(dir);
            dirs.remove(dir.getOpposite());
            ret.put(dir, shape);
            ret.put(dir.getOpposite(), shapeR.copy().mirror(dir.getAxis()).getShape());
            for (Direction dirI : dirs){
                ret.put(dirI, shapeR.copy().rotate(1, dir, dirI).getShape());
            }
            return ret;
        }
        public static final class ShapesR {
            public static List<List<Double>> getVoxels(VoxelShape shape) {
                ArrayList<List<Double>> voxels = new ArrayList<>();
                shape.forEachBox(new VoxelShapes.BoxConsumer() {
                    @Override
                    public void consume(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
                        voxels.add(List.of(minX, minY, minZ, maxX, maxY, maxZ));
                    }
                });
                return voxels;
            }

            public static VoxelShape getVoxelShape(List<List<Double>> voxels){
                VoxelShape shape=VoxelShapes.empty();
                for (List<Double> voxel : voxels){
                    shape=VoxelShapes.union(shape, VoxelShapes.cuboid(
                            voxel.get(0),
                            voxel.get(1),
                            voxel.get(2),
                            voxel.get(3),
                            voxel.get(4),
                            voxel.get(5)
                    ));
                }
                return shape;
            }

            public static ArrayList<ArrayList<Double>> getVoxelsA(VoxelShape shape) {
                ArrayList<ArrayList<Double>> voxels = new ArrayList<>();
                shape.forEachBox(new VoxelShapes.BoxConsumer() {
                    @Override
                    public void consume(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
                        voxels.add(new ArrayList<>(List.of(minX, minY, minZ, maxX, maxY, maxZ)));
                    }
                });
                return voxels;
            }

            public static VoxelShape getVoxelShape(ArrayList<ArrayList<Double>> voxels){
                VoxelShape shape=VoxelShapes.empty();
                for (List<Double> voxel : voxels){
                    shape=VoxelShapes.union(shape, VoxelShapes.cuboid(
                            voxel.get(0),
                            voxel.get(1),
                            voxel.get(2),
                            voxel.get(3),
                            voxel.get(4),
                            voxel.get(5)
                    ));
                }
                return shape;
            }
        }
    }

    public static <T> ArrayList<ArrayList<T>> listToArrayList2D(List<List<T>> list){
        ArrayList<ArrayList<T>> list2=new ArrayList<>();
        for (List<T> e: list){
            list2.add(new ArrayList(e));
        }
        return list2;
    }

    public static int axisToInt(Direction.Axis axis){
        return switch (axis){
            case X -> 0;
            case Y -> 1;
            case Z -> 2;
        };
    }

    public static void print(Object obj, Object... objs){
        System.out.println(obj);
        for (Object objI : objs){
            System.out.print(objI);
        }
    }

    public static void print(Object obj){
        System.out.println(obj);
    }

    public static class Default {
        public static class DInventoty implements Inventory {

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public ItemStack getStack(int slot) {
                return null;
            }

            @Override
            public ItemStack removeStack(int slot, int amount) {
                return null;
            }

            @Override
            public ItemStack removeStack(int slot) {
                return null;
            }

            @Override
            public void setStack(int slot, ItemStack stack) {

            }

            @Override
            public void markDirty() {

            }

            @Override
            public boolean canPlayerUse(PlayerEntity player) {
                return false;
            }

            @Override
            public void clear() {

            }
        }
        public static Inventory inventotyWith(int size, @NotNull ItemStack stack){
            return new Inventory() {
                @Override
                public int size() {
                    return size;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public ItemStack getStack(int slot) {
                    return stack;
                }

                @Override
                public ItemStack removeStack(int slot, int amount) {
                    return stack;
                }

                @Override
                public ItemStack removeStack(int slot) {
                    return stack;
                }

                @Override
                public void setStack(int slot, ItemStack stack) {

                }

                @Override
                public void markDirty() {

                }

                @Override
                public boolean canPlayerUse(PlayerEntity player) {
                    return true;
                }

                @Override
                public void clear() {

                }
            };
        }
    }

    public static void testP(){
        testP(null);
    }

    private static <T> TagKey<T> tagMod(RegistryKey<Registry<T>> reg, String id){
        return TagKey.of(reg, Identifier.of(MODID, id));
    }

    public static final String MC="minecraft";
    public static final BlockState air=Blocks.AIR.getDefaultState();
}
