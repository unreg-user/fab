package wta.entities.projectiles.burdock;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wta.blocks.BlocksInit;
import wta.entities.projectiles.ProjectilesInit;
import wta.mixins.mixinInterfaces.LivingEntityFixerInterface;

import java.util.Arrays;

public class BurdockEntity extends PersistentProjectileEntity {
    public BurdockEntity(EntityType<? extends BurdockEntity> entityType, World world) {
        super(entityType, world);
    }

    public static BurdockEntity createEntity(World world, Vec3d pos, Vec3d motion){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        burdock.setVelocity(motion);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Vec3d pos){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Vec3d pos, Entity owner){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(pos);
        burdock.setOwner(owner);
        return burdock;
    }
    public static BurdockEntity createEntity(World world, Entity owner){
        BurdockEntity burdock=new BurdockEntity(ProjectilesInit.burdockE, world);
        burdock.setPosition(owner.getEyePos());
        burdock.setOwner(owner);
        return burdock;
    }

    @Override
    public ItemStack getDefaultItemStack() {
        return new ItemStack(ProjectilesInit.burdockI);
    }

    @Override
    protected boolean canHit(Entity entity) {
        return true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity=entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity){
            if (livingEntity instanceof LivingEntityFixerInterface livingEntityFixed){
                livingEntityFixed.setStuckBurdockCount(livingEntityFixed.getStuckBurdockCount()+1);
                this.discard();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        World world=this.getWorld();
        BlockPos pos=blockHitResult.getBlockPos();
        BlockPos posUp=pos.up();
        BlockState state=world.getBlockState(pos);
        BlockState stateUp=world.getBlockState(posUp);
        Direction direction=blockHitResult.getSide();
        if (direction==Direction.UP){
            if (stateUp.getBlock() instanceof PlantBlock){
                stateUp=Blocks.AIR.getDefaultState();
                world.breakBlock(posUp, true, this);
            }
            if (state.getBlock()==Blocks.FARMLAND){
                state=Blocks.DIRT.getDefaultState();
                world.setBlockState(pos, state, 3);
            }
            if (state.isIn(BlockTags.DIRT) && stateUp.isIn(BlockTags.AIR)){
                this.discard();
                world.setBlockState(posUp, BlocksInit.burdockSapling.getDefaultState(), 3);
            }
        }
    }
}
