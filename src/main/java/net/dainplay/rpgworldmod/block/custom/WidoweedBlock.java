package net.dainplay.rpgworldmod.block.custom;

import net.dainplay.rpgworldmod.item.ModItems;
import net.dainplay.rpgworldmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WidoweedBlock extends BushBlock {
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public WidoweedBlock(Properties properties) {
        super(properties);
    }
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
        // ItemShears#getDestroySpeed is really dumb and doesn't check IShearable so we have to do it this way to try to match the wool break speed with shears
        return (player.getMainHandItem().getItem() instanceof ShearsItem || player.getMainHandItem().getItem() instanceof SwordItem) ? 1.0F : super.getDestroyProgress(state, player, getter, pos);
    }
    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if ((pEntity instanceof LivingEntity && ((LivingEntity) pEntity).getMobType() == MobType.UNDEAD)) {
            pEntity.hurt((new DamageSource("widoweed")), 2.5F);
            pEntity.makeStuckInBlock(pState, new Vec3(0.25D, 1.0F, 0.25D));
            pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
        }
        else if (pEntity instanceof ItemEntity) {
            if (Registry.ITEM.getHolderOrThrow(Registry.ITEM.getResourceKey(((ItemEntity) pEntity).getItem().getItem()).get()).is(ModTags.Items.WIDOWEED_CONSUMABLE)) {
                pEntity.hurt((new DamageSource("widoweed")), 2.5F);
                pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(world, player, pos, state, te, stack);
        if (!(player.getMainHandItem().getItem() instanceof ShearsItem || player.getMainHandItem().getItem() instanceof SwordItem)) player.hurt(new DamageSource("widoweed"), 2.5F);
    }

    private boolean shouldDamage(Entity entity) {
        return (((LivingEntity) entity).getMobType() == MobType.UNDEAD);
    }
    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return entity != null && shouldDamage(entity) ? BlockPathTypes.DANGER_CACTUS : null;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XYZ;
    }
}
