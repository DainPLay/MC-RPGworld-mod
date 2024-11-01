package net.dainplay.rpgworldmod.block.entity;

import com.mojang.datafixers.types.Type;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.block.entity.custom.FairapierWiltedPlantBlockEntity;
import net.dainplay.rpgworldmod.block.entity.custom.HoltsReflectionBlockEntity;
import net.dainplay.rpgworldmod.block.entity.custom.ModSignBlockEntity;
import net.dainplay.rpgworldmod.block.entity.custom.TreeHollowBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.Registry;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, RPGworldMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<HoltsReflectionBlockEntity>> HOLTS_REFLECTION_BLOCK_ENTITY = BLOCK_ENTITIES.register("holts_reflection_block_entity", () ->
            BlockEntityType.Builder.of(HoltsReflectionBlockEntity::new, ModBlocks.HOLTS_REFLECTION.get()).build(null));

    public static final RegistryObject<BlockEntityType<TreeHollowBlockEntity>> TREE_HOLLOW_BLOCK_ENTITY = BLOCK_ENTITIES.register("tree_hollow_block_entity", () ->
            BlockEntityType.Builder.of(TreeHollowBlockEntity::new, ModBlocks.RIE_HOLLOW.get()).build(null));
    public static final RegistryObject<BlockEntityType<FairapierWiltedPlantBlockEntity>> FAIRAPIER_WILTED_PLANT_BLOCK_ENTITY = BLOCK_ENTITIES.register("fairapier_wilted_plant_block_entity", () ->
            BlockEntityType.Builder.of(FairapierWiltedPlantBlockEntity::new, ModBlocks.FAIRAPIER_WILTED_PLANT.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign_block_entity", () ->
            BlockEntityType.Builder.of(ModSignBlockEntity::new, ModBlocks.RIE_WALL_SIGN.get(),ModBlocks.RIE_SIGN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}