package wta.items.ItemsModClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import wta.Fun;

import java.util.List;
import java.util.function.Function;

public class BowDripstoneClass extends Item{

    public static final List<Item> dripstones=List.of(
            Items.POINTED_DRIPSTONE
    );
    private static final Function<BlockState, BlockState> restater=(state) -> state
            .with(PointedDripstoneBlock.VERTICAL_DIRECTION, Direction.DOWN);

    public BowDripstoneClass(Settings settings) {
        super(settings);
    }

    @Nullable
    private ItemStack getPlayerItem(PlayerEntity player, List<Item> items){
        for(int i = 0; i < player.getInventory().size(); ++i) {
            ItemStack itemStack2 = player.getInventory().getStack(i);
            if (items.contains(itemStack2.getItem())){
                return itemStack2;
            }
        }
        return null;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        boolean creative = player.isCreative();
        ItemStack projectile = getPlayerItem(player, dripstones);

        if (projectile==null){
            if (creative){
                projectile=new ItemStack(Items.POINTED_DRIPSTONE);
            }else{
                return;
            }
        }

        int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
        float power = getPullProgress(useTime);

        if (power < 0.1F) return;

        if (!world.isClient) {
            // falling_block
            BlockState state = Fun.getStackState(projectile, restater);

            FallingBlockEntity fallingBlock = FallingBlockEntity.spawnFromBlock(world, player.getBlockPos(), state);
            fallingBlock.setHurtEntities(power * 2, 40);
            fallingBlock.setPosition(player.getX(), player.getY() + player.getEyeHeight(player.getPose()), player.getZ());

            Vec3d direction = player.getRotationVec(1.0F);

            double speed = power*1.5D;
            fallingBlock.setVelocity(direction.x * speed, direction.y * speed, direction.z * speed);
            fallingBlock.velocityModified = true;

            world.spawnEntity(fallingBlock);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS,
                1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        player.incrementStat(Stats.USED.getOrCreateStat(this));

        if (!creative) {
            stack.damage(2, player, Fun.getSlotHand.apply(player.getActiveHand()));
            projectile.decrement(1);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isCreative() || getPlayerItem(user, dripstones)!=null) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000; // vanilla
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }
}
