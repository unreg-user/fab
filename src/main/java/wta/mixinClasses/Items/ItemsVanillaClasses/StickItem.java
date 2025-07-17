package wta.mixinClasses.Items.ItemsVanillaClasses;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import wta.blocks.interfaces.StickUsed;

public class StickItem extends Item {
    public StickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world=context.getWorld();
        Block block=world.getBlockState(context.getBlockPos()).getBlock();
        if (block instanceof StickUsed stickUsed){
            if (stickUsed.useAnimation()){
                context.getPlayer().swingHand(context.getHand());
            }
        }
        return ActionResult.PASS;
    }
}
