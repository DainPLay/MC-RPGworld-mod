package net.dainplay.rpgworldmod.block.custom;

import net.dainplay.rpgworldmod.block.entity.custom.TreeHollowBlockEntity;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class TreeHollowBlock extends BaseEntityBlock {
    public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public TreeHollowBlock(BlockBehaviour.Properties p_54257_) {
        super(p_54257_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_RECORD, false));
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
        if (compoundtag != null) {
            pLevel.setBlock(pPos, pState.setValue(HAS_RECORD, true), 2);
        }

    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHit.getDirection() == pState.getValue(FACING))
        if (pState.getValue(HAS_RECORD)) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
            if(TreeHollowBlockEntity.getRecord().getItem() instanceof SpawnEggItem) {
                dispenseMob(pLevel, pPos, pState,  pPlayer,  pHit);
            }
            else {
                dispenseRecording(pLevel, pPos, pState);
            }
            pLevel.playSound((Player) null, pPos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            pState = pState.setValue(HAS_RECORD, false);
            pLevel.setBlock(pPos, pState, 2);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (!pPlayer.getItemInHand(pHand).isEmpty()) {
                pLevel.playSound((Player)null, pPos, SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!pLevel.isClientSide) {
                    this.setRecord(pLevel, pPos, pState, itemstack);
                    pLevel.levelEvent((Player) null, 1010, pPos, Item.getId(itemstack.getItem()));
                    itemstack.setCount(0);
                }

                BlockEntity blockentity = pLevel.getBlockEntity(pPos);
                TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
                TreeHollowBlockEntity.setChanged();
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
            else return InteractionResult.PASS;
        }
        else return InteractionResult.PASS;
    }
    public void setRecord(LevelAccessor pLevel, BlockPos pPos, BlockState pState, ItemStack pRecordStack) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            ((TreeHollowBlockEntity)blockentity).setRecord(pRecordStack.copy());
            pLevel.setBlock(pPos, pState.setValue(HAS_RECORD, true), 2);
    }

    private void dispenseMob(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer, BlockHitResult pHit) {
    if (!pLevel.isClientSide) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof TreeHollowBlockEntity) {
            TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
            ItemStack itemstack = TreeHollowBlockEntity.getRecord();
            if (!itemstack.isEmpty()) {
                TreeHollowBlockEntity.clearContent();
                Item item = itemstack.getItem();
                EntityType<?> entityType = (((SpawnEggItem)item).getType(itemstack.getTag()));
                BlockPos blockpos = pHit.getBlockPos();
                entityType.spawn((ServerLevel)pLevel, itemstack, pPlayer, blockpos.relative(pState.getValue(FACING)), MobSpawnType.DISPENSER, false, false);
            }
        }
    }
}

    private void dispenseRecording(Level pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.isClientSide) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof TreeHollowBlockEntity) {
                TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
                ItemStack itemstack = TreeHollowBlockEntity.getRecord();
                if (!itemstack.isEmpty()) {
                    pLevel.levelEvent(1010, pPos, 0);
                    TreeHollowBlockEntity.clearContent();
                    double d0 = (double) EntityType.ITEM.getWidth();
                    double d1 = 0.45D - d0;
                    double d2 = 1;
                    double d3 = Math.floor(pPos.getX()) + d1 + (pState.getValue(FACING) == Direction.EAST ? d2 : 0) - (pState.getValue(FACING) == Direction.WEST ? 0.5 : 0);
                    double d4 = Math.floor(pPos.getY()) + d1;
                    double d5 = Math.floor(pPos.getZ()) + d1 + (pState.getValue(FACING) == Direction.SOUTH ? d2 : 0) - (pState.getValue(FACING) == Direction.NORTH ? 0.5 : 0);
                    ItemStack itemstack1 = itemstack.copy();
                    ItemEntity itementity = new ItemEntity(pLevel, d3, d4, d5, itemstack1);
                    itementity.setDefaultPickUpDelay();
                    pLevel.addFreshEntity(itementity);
                }
            }
        }
    }

    private void dropMob(Level pLevel, BlockPos pPos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof TreeHollowBlockEntity) {
                TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
                ItemStack itemstack = TreeHollowBlockEntity.getRecord();
                if (!itemstack.isEmpty()) {
                    TreeHollowBlockEntity.clearContent();
                    Item item = itemstack.getItem();
                    EntityType<?> entityType = (((SpawnEggItem)item).getType(itemstack.getTag()));
                    entityType.spawn((ServerLevel)pLevel, itemstack, pPlayer, pPos, MobSpawnType.DISPENSER, false, false);
                }
            }
        }
    }



    private void dropRecording(Level pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof TreeHollowBlockEntity) {
                TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
                ItemStack itemstack = TreeHollowBlockEntity.getRecord();
                if (!itemstack.isEmpty()) {
                    pLevel.levelEvent(1010, pPos, 0);
                    TreeHollowBlockEntity.clearContent();
                    double d0 = (double) EntityType.ITEM.getWidth();
                    double d1 = 1.0D - d0;
                    double d2 = d0 / 2.0D;
                    double d3 = Math.floor(pPos.getX()) + RANDOM.nextDouble() * d1 + d2;
                    double d4 = Math.floor(pPos.getY()) + RANDOM.nextDouble() * d1;
                    double d5 = Math.floor(pPos.getZ()) + RANDOM.nextDouble() * d1 + d2;
                    ItemStack itemstack1 = itemstack.copy();
                    ItemEntity itementity = new ItemEntity(pLevel, d3, d4, d5, itemstack1);
                    itementity.setDefaultPickUpDelay();
                    pLevel.addFreshEntity(itementity);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            TreeHollowBlockEntity TreeHollowBlockEntity = (TreeHollowBlockEntity)blockentity;
            if(TreeHollowBlockEntity.getRecord().getItem() instanceof SpawnEggItem) {
                dropMob(pLevel, pPos,  null);
            }
            else {
                this.dropRecording(pLevel, pPos);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TreeHollowBlockEntity(pPos, pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof TreeHollowBlockEntity && pBlockState.getValue(HAS_RECORD)) {
            double to_return = ((double)((TreeHollowBlockEntity)blockentity).getRecord().getCount()/(double)((TreeHollowBlockEntity)blockentity).getRecord().getItem().getMaxStackSize()*15);
            LOGGER.info(((TreeHollowBlockEntity)blockentity).getRecord().getCount() + "/" + ((TreeHollowBlockEntity)blockentity).getRecord().getItem().getMaxStackSize() + "*15 = " + (((TreeHollowBlockEntity)blockentity).getRecord().getCount()/((TreeHollowBlockEntity)blockentity).getRecord().getItem().getMaxStackSize()*15) + " to_return: " + to_return);
                return (int) Math.round(to_return);
        }

        return 0;
    }


    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HAS_RECORD, FACING);
    }
}
