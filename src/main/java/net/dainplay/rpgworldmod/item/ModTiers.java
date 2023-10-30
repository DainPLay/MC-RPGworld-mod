package net.dainplay.rpgworldmod.item;

import net.dainplay.rpgworldmod.block.ModBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier MASKONITE = new ForgeTier(2, 400, 4.0F, 1.0F, 8, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModBlocks.MASKONITE_BLOCK.get()));
    public static final ForgeTier FAIRAPIER = new ForgeTier(2, 250, 6.0F, 2.0F, 22, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.EMPTY);
}
