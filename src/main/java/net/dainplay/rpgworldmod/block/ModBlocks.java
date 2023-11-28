package net.dainplay.rpgworldmod.block;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.block.custom.*;
import net.dainplay.rpgworldmod.block.entity.ModWoodTypes;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.dainplay.rpgworldmod.world.feature.tree.RieTreeGrower;
import net.dainplay.rpgworldmod.item.ModCreativeModeTab;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RPGworldMod.MOD_ID);

    public static final RegistryObject<Block> MASKONITE_BLOCK = registerBlock("maskonite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_STAIRS = registerBlock("maskonite_stairs",
            () -> new StairBlock(() -> ModBlocks.MASKONITE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_SLAB = registerBlock("maskonite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_WALL = registerBlock("maskonite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_BUTTON = registerBlock("maskonite_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_PRESSURE_PLATE = registerBlock("maskonite_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_BRICKS = registerBlock("maskonite_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_BRICK_STAIRS = registerBlock("maskonite_brick_stairs",
            () -> new StairBlock(() -> ModBlocks.MASKONITE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_BRICK_SLAB = registerBlock("maskonite_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_BRICK_WALL = registerBlock("maskonite_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> CRACKED_MASKONITE_BRICKS = registerBlock("cracked_maskonite_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> CHISELED_MASKONITE_BLOCK = registerBlock("chiseled_maskonite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.6f).requiresCorrectToolForDrops()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_GLASS = registerBlock("maskonite_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MASKONITE_GLASS_PANE = registerBlock("maskonite_glass_pane",
            () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).noOcclusion()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> SHIVERALIS = registerBlockWithoutBlockItem("shiveralis",
            () -> new ShiveralisPlantBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion()));
    public static final RegistryObject<Block> POTTED_SHIVERALIS = registerBlockWithoutBlockItem("potted_shiveralis",
            () -> new FlowerPotBlock(ModBlocks.SHIVERALIS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> RPGIROLLE = registerBlockWithoutBlockItem("rpgirolle",
            () -> new FlowerBlock(MobEffects.SATURATION, 0, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.FUNGUS)));
    public static final RegistryObject<Block> WILD_FAIRAPIER = registerBlock("wild_fairapier",
            () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 8, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()) {

                protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
                @Override
                public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                    return SHAPE;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> MIMOSSA = registerBlock("mimossa",
            () -> new FlowerBlock(ModEffects.MOSSIOSIS, 16, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()) {
                 protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
                 @Override
                 public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                 return SHAPE;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);

    public static final RegistryObject<Block> PARALILY = registerBlockWithoutBlockItem("paralily",
            () -> new ParalilyBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.LILY_PAD).noOcclusion()));
    public static final RegistryObject<Block> POTTED_MIMOSSA = registerBlockWithoutBlockItem("potted_mimossa",
            () -> new FlowerPotBlock(ModBlocks.MIMOSSA.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> MOSSHROOM = registerBlock("mosshroom",
            () -> new FlowerBlock(ModEffects.MOSSIOSIS, 16, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS)) {
                //protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
                //@Override
                        //public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                    //    return SHAPE;
                    //}
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> POTTED_MOSSHROOM = registerBlockWithoutBlockItem("potted_mosshroom",
            () -> new FlowerPotBlock(ModBlocks.MOSSHROOM.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> CHEESE_CAP = registerBlockWithoutBlockItem("cheese_cap",
            () -> new FlowerBlock(MobEffects.SATURATION, 0, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.FUNGUS)));
    public static final RegistryObject<Block> POTTED_CHEESE_CAP = registerBlockWithoutBlockItem("potted_cheese_cap",
            () -> new FlowerPotBlock(ModBlocks.CHEESE_CAP.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> FAIRAPIER_PLANT = registerBlockWithoutBlockItem("fairapier_plant",
            () -> new FairapierPlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    public static final RegistryObject<Block> FAIRAPIER_WILTED_PLANT = registerBlockWithoutBlockItem("fairapier_wilted_plant",
            () -> new FairapierWiltedPlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    public static final RegistryObject<Block> POTTED_RPGIROLLE = registerBlockWithoutBlockItem("potted_rpgirolle",
            () -> new FlowerPotBlock(ModBlocks.RPGIROLLE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_WILD_FAIRAPIER = registerBlockWithoutBlockItem("potted_wild_fairapier",
            () -> new FlowerPotBlock(ModBlocks.WILD_FAIRAPIER.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> PROJECTRUFFLE = registerBlock("projectruffle",
            () -> new FlowerBlock(MobEffects.HARM, 0, BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()) {

                protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
                @Override
                public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                    return SHAPE;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> HOLTS_REFLECTION = registerBlock("holts_reflection",
            () -> new HoltsReflectionBlock(BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> WIDOWEED = registerBlock("widoweed",
            () -> new WidoweedBlock(BlockBehaviour.Properties.of(Material.GRASS).noCollission().strength(0.2F).sound(SoundType.GRASS)),  ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> SPIKY_IVY = registerBlock("spiky_ivy",
            () -> new SpikyIvyBlock(BlockBehaviour.Properties.of(Material.GRASS).randomTicks().strength(0.4F).sound(SoundType.WART_BLOCK)), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> POTTED_SPIKY_IVY = registerBlockWithoutBlockItem("potted_spiky_ivy",
            () -> new FlowerPotBlock(ModBlocks.SPIKY_IVY.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));
    public static final RegistryObject<Block> POTTED_PROJECTRUFFLE = registerBlockWithoutBlockItem("potted_projectruffle",
            () -> new FlowerPotBlock(ModBlocks.PROJECTRUFFLE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));


    public static final RegistryObject<Block> RIE_LOG = registerBlock("rie_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_WOOD = registerBlock("rie_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> STRIPPED_RIE_LOG = registerBlock("stripped_rie_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> STRIPPED_RIE_WOOD = registerBlock("stripped_rie_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
            ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_HOLLOW = registerBlock("rie_hollow",
            () -> new TreeHollowBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F)),
            ModCreativeModeTab.RPGWORLD_TAB);


    public static final RegistryObject<Block> RIE_PLANKS = registerBlock("rie_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_STAIRS = registerBlock("rie_stairs",
            () -> new StairBlock(() -> ModBlocks.RIE_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_SLAB = registerBlock("rie_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_FENCE = registerFuelBlock("rie_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB, 300);
    public static final RegistryObject<Block> RIE_FENCE_GATE = registerFuelBlock("rie_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB, 300);

    public static final RegistryObject<Block> RIE_BUTTON = registerBlock("rie_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_PRESSURE_PLATE = registerBlock("rie_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_DOOR = registerBlock("rie_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_TRAPDOOR = registerBlock("rie_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), ModCreativeModeTab.RPGWORLD_TAB);


    public static final RegistryObject<Block> RIE_LEAVES = registerBlock("rie_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }
            }, ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> RIE_SAPLING = registerBlock("rie_sapling",
            () -> new SaplingBlock(new RieTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModCreativeModeTab.RPGWORLD_TAB);
    public static final RegistryObject<Block> POTTED_RIE_SAPLING = registerBlockWithoutBlockItem("potted_rie_sapling",
            () -> new FlowerPotBlock(ModBlocks.RIE_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_OAK_SAPLING).noOcclusion()));
    public static final RegistryObject<Block> RIE_WALL_SIGN = registerBlockWithoutBlockItem("rie_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.RIE));
    public static final RegistryObject<Block> RIE_SIGN = registerBlockWithoutBlockItem("rie_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.RIE));
    public static final RegistryObject<Block> MINTAL_BLOCK = registerBlock("mintal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ModCreativeModeTab.RPGWORLD_TAB);

    public static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    public static <T extends Block> RegistryObject<T> registerFuelBlock(String name, Supplier<T> block, CreativeModeTab tab, int burntime) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().tab(tab)) {
            @Override
            public int getBurnTime(ItemStack itemstack, @Nullable RecipeType<?> recipeType)
            {
                return burntime;
            }
        });
        return toReturn;
    }
    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventbus) {
        BLOCKS.register(eventbus);
    }
}
