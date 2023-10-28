package net.dainplay.rpgworldmod.block.custom;
import com.google.common.collect.ImmutableMap;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class SpikyIvyBlock extends Block implements BonemealableBlock {
    private final Map<BlockState, VoxelShape> shapeByIndex;
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final EnumProperty<WallSide> EAST_IVY = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> NORTH_IVY = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> SOUTH_IVY = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST_IVY = BlockStateProperties.WEST_WALL;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = 3;
    private static final VoxelShape POST_TEST = Block.box(7.0D, 0.0D, 7.0D, 7.0D, 12.0D, 7.0D);
    private static final VoxelShape NORTH_TEST = Block.box(7.0D, 0.0D, 0.0D, 7.0D, 12.0D, 7.0D);
    private static final VoxelShape SOUTH_TEST = Block.box(7.0D, 0.0D, 7.0D, 7.0D, 12.0D, 12.0D);
    private static final VoxelShape WEST_TEST = Block.box(0.0D, 0.0D, 7.0D, 7.0D, 12.0D, 7.0D);
    private static final VoxelShape EAST_TEST = Block.box(7.0D, 0.0D, 7.0D, 12.0D, 12.0D, 7.0D);

    public SpikyIvyBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BOTTOM, Boolean.valueOf(true)).setValue(NORTH_IVY, WallSide.NONE).setValue(EAST_IVY, WallSide.NONE).setValue(SOUTH_IVY, WallSide.NONE).setValue(WEST_IVY, WallSide.NONE));
        this.shapeByIndex = this.makeShapes(3.0F, 3.0F, 12.0F, 0.0F, 12.0F, 12.0F);

    }
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) < 3;
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        Direction growthDirection = Direction.EAST;
        if (pState.getValue(AGE) < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos.relative(growthDirection), pLevel.getBlockState(pPos.relative(growthDirection)),pRandom.nextDouble() < 0.05D)) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }        
        growthDirection = Direction.WEST;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos.relative(growthDirection), pLevel.getBlockState(pPos.relative(growthDirection)),pRandom.nextDouble() <  0.05D)) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
        growthDirection = Direction.SOUTH;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos.relative(growthDirection), pLevel.getBlockState(pPos.relative(growthDirection)),pRandom.nextDouble() < 0.05D)) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
        growthDirection = Direction.NORTH;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos.relative(growthDirection), pLevel.getBlockState(pPos.relative(growthDirection)),pRandom.nextDouble() < 0.05D)) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }

    }

    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pLevel.getBlockState(pPos.east()).getBlock() == Blocks.AIR
                || pLevel.getBlockState(pPos.west()).getBlock() == Blocks.AIR
                || pLevel.getBlockState(pPos.north()).getBlock() == Blocks.AIR
                || pLevel.getBlockState(pPos.south()).getBlock() == Blocks.AIR;
    }

    public boolean isBonemealSuccess(Level pLevel, Random pRand, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        pLevel.setBlockAndUpdate(pPos, ModBlocks.SPIKY_IVY.get().defaultBlockState()
                .setValue(AGE, pState.getValue(AGE) == 0 ? 0 : pState.getValue(AGE)-1)
                .setValue(EAST_IVY, pState.getValue(EAST_IVY))
                .setValue(WEST_IVY, pState.getValue(WEST_IVY))
                .setValue(NORTH_IVY, pState.getValue(NORTH_IVY))
                .setValue(SOUTH_IVY, pState.getValue(SOUTH_IVY))
                .setValue(BOTTOM, pState.getValue(BOTTOM))
        );
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pLevel.getBlockState(pPos));
        Direction growthDirection = Direction.EAST;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
        growthDirection = Direction.WEST;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
        growthDirection = Direction.SOUTH;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }
        growthDirection = Direction.NORTH;
        if (pRandom.nextInt(2) == 1 && pState.getValue(AGE) < 3) {
            BlockPos blockpos = pPos.relative(growthDirection);
            if (pLevel.getBlockState(blockpos).getBlock() == Blocks.AIR) {
                pLevel.setBlockAndUpdate(blockpos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, pState.getValue(AGE)+1));
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, blockpos, pLevel.getBlockState(blockpos));
            }
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int i = pState.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (!flag && pPlayer.getItemInHand(pHand).getItem() instanceof ShearsItem) {
            pLevel.playSound((Player)null, pPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.setBlockAndUpdate(pPos, ModBlocks.SPIKY_IVY.get().defaultBlockState().setValue(AGE, 3)
                    .setValue(EAST_IVY, pState.getValue(EAST_IVY))
                    .setValue(WEST_IVY, pState.getValue(WEST_IVY))
                    .setValue(NORTH_IVY, pState.getValue(NORTH_IVY))
                    .setValue(SOUTH_IVY, pState.getValue(SOUTH_IVY))
                    .setValue(BOTTOM, pState.getValue(BOTTOM)));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pLevel.getBlockState(pPos));
            if (pPlayer != null) {
                pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer, (p_186374_) -> {
                    p_186374_.broadcastBreakEvent(pHand);
                });
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }
    private boolean connectsTo(BlockState pState, boolean pSideSolid, Direction pDirection) {
        return (pState.getBlock() == ModBlocks.SPIKY_IVY.get()
                || pState.is(BlockTags.DIRT)
                || pState.is(Blocks.FARMLAND));
    }
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlockTags.DIRT) || pState.is(Blocks.FARMLAND);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.below();
        BlockState blockstate = levelreader.getBlockState(blockpos1);
        BlockState blockstate1 = levelreader.getBlockState(blockpos2);
        BlockState blockstate2 = levelreader.getBlockState(blockpos3);
        BlockState blockstate3 = levelreader.getBlockState(blockpos4);
        BlockState blockstate4 = levelreader.getBlockState(blockpos5);
        boolean flag = this.connectsTo(blockstate, blockstate.isFaceSturdy(levelreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
        boolean flag1 = this.connectsTo(blockstate1, blockstate1.isFaceSturdy(levelreader, blockpos2, Direction.WEST), Direction.WEST);
        boolean flag2 = this.connectsTo(blockstate2, blockstate2.isFaceSturdy(levelreader, blockpos3, Direction.NORTH), Direction.NORTH);
        boolean flag3 = this.connectsTo(blockstate3, blockstate3.isFaceSturdy(levelreader, blockpos4, Direction.EAST), Direction.EAST);
        BlockState blockstate5 = this.defaultBlockState();
        return this.updateShape(levelreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {

        return pFacing == Direction.DOWN ? this.topUpdate(pLevel, pState, pFacingPos, pFacingState) : this.sideUpdate(pLevel, pCurrentPos, pState, pFacingPos, pFacingState, pFacing);

    }
    private static boolean isConnected(BlockState pState, Property<WallSide> pHeightProperty) {
        if(pState.getBlock()==ModBlocks.SPIKY_IVY.get())
            return pState.getValue(pHeightProperty) != WallSide.NONE;
        else return false;
    }

    private BlockState topUpdate(LevelReader pLevel, BlockState p_57976_, BlockPos p_57977_, BlockState p_57978_) {
        boolean flag = isConnected(p_57976_, NORTH_IVY);
        boolean flag1 = isConnected(p_57976_, EAST_IVY);
        boolean flag2 = isConnected(p_57976_, SOUTH_IVY);
        boolean flag3 = isConnected(p_57976_, WEST_IVY);
        return this.updateShape(pLevel, p_57976_, p_57977_, p_57978_, flag, flag1, flag2, flag3);
    }
    private Map<BlockState, VoxelShape> makeShapes(float p_57966_, float p_57967_, float p_57968_, float p_57969_, float p_57970_, float p_57971_) {
        float f = 8.0F - p_57966_;
        float f1 = 8.0F + p_57966_;
        float f2 = 8.0F - p_57967_;
        float f3 = 8.0F + p_57967_;
        VoxelShape voxelshape = Block.box((double) f, 0.0D, (double) f, (double) f1, (double) p_57968_, (double) f1);
        VoxelShape voxelshape0 = Block.box((double) f, 0.0D, (double) f, (double) f1, 16F, (double) f1);
        VoxelShape voxelshape1 = Block.box((double) f2, (double) p_57969_, 0.0D, (double) f3, (double) p_57970_, (double) f3);
        VoxelShape voxelshape2 = Block.box((double) f2, (double) p_57969_, (double) f2, (double) f3, (double) p_57970_, 16.0D);
        VoxelShape voxelshape3 = Block.box(0.0D, (double) p_57969_, (double) f2, (double) f3, (double) p_57970_, (double) f3);
        VoxelShape voxelshape4 = Block.box((double) f2, (double) p_57969_, (double) f2, 16.0D, (double) p_57970_, (double) f3);
        VoxelShape voxelshape5 = Block.box((double) f2, (double) p_57969_, 0.0D, (double) f3, (double) p_57971_, (double) f3);
        VoxelShape voxelshape6 = Block.box((double) f2, (double) p_57969_, (double) f2, (double) f3, (double) p_57971_, 16.0D);
        VoxelShape voxelshape7 = Block.box(0.0D, (double) p_57969_, (double) f2, (double) f3, (double) p_57971_, (double) f3);
        VoxelShape voxelshape8 = Block.box((double) f2, (double) p_57969_, (double) f2, 16.0D, (double) p_57971_, (double) f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Integer agege : AGE.getPossibleValues()) {
        for (Boolean obool : BOTTOM.getPossibleValues()) {
            for (WallSide wallside : EAST_IVY.getPossibleValues()) {
                for (WallSide wallside1 : NORTH_IVY.getPossibleValues()) {
                    for (WallSide wallside2 : WEST_IVY.getPossibleValues()) {
                        for (WallSide wallside3 : SOUTH_IVY.getPossibleValues()) {
                            VoxelShape voxelshape9 = Shapes.empty();
                            voxelshape9 = applyWallShape(voxelshape9, wallside, voxelshape4, voxelshape8);
                            voxelshape9 = applyWallShape(voxelshape9, wallside2, voxelshape3, voxelshape7);
                            voxelshape9 = applyWallShape(voxelshape9, wallside1, voxelshape1, voxelshape5);
                            voxelshape9 = applyWallShape(voxelshape9, wallside3, voxelshape2, voxelshape6);
                            if (obool) {
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape);
                            } else if (wallside == WallSide.NONE && wallside1 == WallSide.NONE && wallside2 == WallSide.NONE && wallside3 == WallSide.NONE) {
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape0);
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape1);
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape2);
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape3);
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape4);

                            }

                            BlockState blockstate = this.defaultBlockState().setValue(AGE, agege).setValue(BOTTOM, obool).setValue(EAST_IVY, wallside).setValue(WEST_IVY, wallside2).setValue(NORTH_IVY, wallside1).setValue(SOUTH_IVY, wallside3);
                            builder.put(blockstate, voxelshape9);
                        }
                    }
                }
            }
        }
    }

        return builder.build();
    }

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapeByIndex.get(pState);
    }

    @Override
    @Deprecated
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shapeByIndex.get(pState);
    }

    private static VoxelShape applyWallShape(VoxelShape pBaseShape, WallSide pHeight, VoxelShape pLowShape, VoxelShape pTallShape) {
        return pHeight == WallSide.LOW ? Shapes.or(pBaseShape, pLowShape) : pBaseShape;
    }

    private BlockState sideUpdate(LevelReader pLevel, BlockPos p_57990_, BlockState p_57991_, BlockPos p_57992_, BlockState p_57993_, Direction p_57994_) {
        Direction direction = p_57994_.getOpposite();
        boolean flag = p_57994_ == Direction.NORTH ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, NORTH_IVY);
        boolean flag1 = p_57994_ == Direction.EAST ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, EAST_IVY);
        boolean flag2 = p_57994_ == Direction.SOUTH ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, SOUTH_IVY);
        boolean flag3 = p_57994_ == Direction.WEST ? this.connectsTo(p_57993_, p_57993_.isFaceSturdy(pLevel, p_57992_, direction), direction) : isConnected(p_57991_, WEST_IVY);
        BlockPos blockpos = p_57990_.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return this.updateShape(pLevel, p_57991_, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
        // ItemShears#getDestroySpeed is really dumb and doesn't check IShearable, so we have to do it this way to try to match the wool break speed with shears
        return (player.getMainHandItem().getItem() instanceof ShearsItem || player.getMainHandItem().getItem() instanceof SwordItem) ? 0.9F : super.getDestroyProgress(state, player, getter, pos);
    }
    private BlockState updateShape(LevelReader pLevel, BlockState p_57981_, BlockPos p_57982_, BlockState p_57983_, boolean p_57984_, boolean p_57985_, boolean p_57986_, boolean p_57987_) {
        VoxelShape voxelshape = p_57983_.getCollisionShape(pLevel, p_57982_).getFaceShape(Direction.UP);
        BlockState blockstate = this.updateSides(p_57981_, p_57984_, p_57985_, p_57986_, p_57987_, voxelshape);
        return blockstate.setValue(BOTTOM, Boolean.valueOf(this.mayPlaceOn(pLevel.getBlockState(p_57982_), pLevel, p_57982_)));
    }

    private BlockState updateSides(BlockState p_58025_, boolean p_58026_, boolean p_58027_, boolean p_58028_, boolean p_58029_, VoxelShape p_58030_) {
        return p_58025_.setValue(NORTH_IVY, this.makeWallState(p_58026_, p_58030_, NORTH_TEST)).setValue(EAST_IVY, this.makeWallState(p_58027_, p_58030_, EAST_TEST)).setValue(SOUTH_IVY, this.makeWallState(p_58028_, p_58030_, SOUTH_TEST)).setValue(WEST_IVY, this.makeWallState(p_58029_, p_58030_, WEST_TEST));
    }

    private WallSide makeWallState(boolean p_58042_, VoxelShape p_58043_, VoxelShape p_58044_) {
        if (p_58042_) {
            return WallSide.LOW;
        } else {
            return WallSide.NONE;
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, BOTTOM, NORTH_IVY, EAST_IVY, WEST_IVY, SOUTH_IVY);
    }
/*
    @Override
    @Deprecated
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch(pRotation) {
            case CLOCKWISE_180:
                return pState.setValue(NORTH_IVY, pState.getValue(SOUTH_IVY)).setValue(EAST_IVY, pState.getValue(WEST_IVY)).setValue(SOUTH_IVY, pState.getValue(NORTH_IVY)).setValue(WEST_IVY, pState.getValue(EAST_IVY));
            case COUNTERCLOCKWISE_90:
                return pState.setValue(NORTH_IVY, pState.getValue(EAST_IVY)).setValue(EAST_IVY, pState.getValue(SOUTH_IVY)).setValue(SOUTH_IVY, pState.getValue(WEST_IVY)).setValue(WEST_IVY, pState.getValue(NORTH_IVY));
            case CLOCKWISE_90:
                return pState.setValue(NORTH_IVY, pState.getValue(WEST_IVY)).setValue(EAST_IVY, pState.getValue(NORTH_IVY)).setValue(SOUTH_IVY, pState.getValue(EAST_IVY)).setValue(WEST_IVY, pState.getValue(SOUTH_IVY));
            default:
                return pState;
        }
    }

    @Override
    @Deprecated
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        switch(pMirror) {
            case LEFT_RIGHT:
                return pState.setValue(NORTH_IVY, pState.getValue(SOUTH_IVY)).setValue(SOUTH_IVY, pState.getValue(NORTH_IVY));
            case FRONT_BACK:
                return pState.setValue(EAST_IVY, pState.getValue(WEST_IVY)).setValue(WEST_IVY, pState.getValue(EAST_IVY));
            default:
                return super.mirror(pState, pMirror);
        }
    }

 */

    @Override
    @Deprecated
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!(pEntity instanceof ItemEntity) && !pEntity.isCrouching()) {
            pEntity.hurt((new DamageSource("spiky_ivy")), 2.5F);
            pEntity.makeStuckInBlock(pState, new Vec3(0.25D, 1.0F, 0.25D));
        }
    }

    @Nullable
    @Override
    public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.DANGER_CACTUS;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

}
