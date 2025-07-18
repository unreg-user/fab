package wta.mixinClasses.Items.ItemsVanillaClasses;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import wta.blocks.interfaces.StickUsed;

public class StickItem extends BlockItem {
    public StickItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world=context.getWorld();
        Block block=world.getBlockState(context.getBlockPos()).getBlock();
        if (block instanceof StickUsed stickUsed){
            if (stickUsed.useAnimation(context)){
                context.getPlayer().swingHand(context.getHand());
                return ActionResult.PASS;
            }
        }
        return super.useOnBlock(context);
    }
}
