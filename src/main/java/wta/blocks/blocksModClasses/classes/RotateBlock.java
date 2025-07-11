package wta.blocks.blocksModClasses.classes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.Nullable;

public class RotateBlock extends Block {
    protected static final DirectionProperty ROTATE=Properties.FACING;

    public RotateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        if (getPlacementStateOpposite()){
            return this.getDefaultState().with(ROTATE, ctx.getSide().getOpposite());
        }else{
            return this.getDefaultState().with(ROTATE, ctx.getSide());
        }
    }

    protected Boolean getPlacementStateOpposite(){
        return false;
    }
}
