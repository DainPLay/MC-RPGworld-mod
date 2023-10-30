package net.dainplay.rpgworldmod.features;

import com.mojang.serialization.Codec;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.block.custom.TreeHollowBlock;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import java.util.Random;

import static net.dainplay.rpgworldmod.block.custom.TreeHollowBlock.FACING;

public class TreeHollowFeatureSouth extends Feature<BlockStateConfiguration> {
    public TreeHollowFeatureSouth(Codec<BlockStateConfiguration> p_65248_) {
        super(p_65248_);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> p_159471_) {
        BlockPos blockpos = p_159471_.origin();
        WorldGenLevel worldgenlevel = p_159471_.level();
        Random random = p_159471_.random();

        if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 3) {
            return false;
        } else {
            int placechance = random.nextInt(21);
            if(placechance > 0 && worldgenlevel.getBlockState(blockpos).getBlock() == ModBlocks.RIE_LOG.get() && worldgenlevel.getBlockState(blockpos.above()).getBlock() == ModBlocks.RIE_LOG.get()) {
                worldgenlevel.setBlock(blockpos.above(), ModBlocks.RIE_HOLLOW.get().defaultBlockState().setValue(FACING, Direction.SOUTH),4);

                Block hollowblock = worldgenlevel.getBlockState(blockpos.above()).getBlock();
                int itemchance = random.nextInt(120);
                ItemStack itemstack = ItemStack.EMPTY;
                switch(itemchance) {
                    case 0:
                    case 1:
                        itemstack = ModItems.MASKONITE_AXE.get().getDefaultInstance();
                        itemstack.setDamageValue(150+random.nextInt(200));
                        break;
                    case 2:
                    case 3:
                        itemstack = ModItems.MASKONITE_PICKAXE.get().getDefaultInstance();
                        itemstack.setDamageValue(150+random.nextInt(200));
                        break;
                    case 4:
                    case 5:
                        itemstack = ModItems.MASKONITE_SWORD.get().getDefaultInstance();
                        itemstack.setDamageValue(150+random.nextInt(200));
                        break;
                    case 6:
                    case 7:
                        itemstack = ModItems.MASKONITE_HOE.get().getDefaultInstance();
                        itemstack.setDamageValue(150+random.nextInt(200));
                        break;
                    case 8:
                    case 9:
                        itemstack = ModItems.MASKONITE_SHOVEL.get().getDefaultInstance();
                        itemstack.setDamageValue(150+random.nextInt(200));
                        break;
                    case 10:
                    case 11:
                        itemstack = Items.CREEPER_SPAWN_EGG.getDefaultInstance();
                        break;
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                        itemstack = Items.BAT_SPAWN_EGG.getDefaultInstance();
                        break;
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                        itemstack = Items.ROTTEN_FLESH.getDefaultInstance();
                        itemstack.setCount(1+random.nextInt(5));
                        break;
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                    case 69:
                    case 70:
                    case 71:
                    case 72:
                    case 73:
                    case 74:
                    case 75:
                        itemstack = Items.WHEAT.getDefaultInstance();
                        itemstack.setCount(1+random.nextInt(6));
                        break;
                    case 76:
                    case 77:
                    case 78:
                    case 79:
                    case 80:
                    case 81:
                    case 82:
                    case 83:
                    case 84:
                    case 85:
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                    case 92:
                        itemstack = Items.QUARTZ.getDefaultInstance();
                        itemstack.setCount(1+random.nextInt(6));
                        break;
                    case 93:
                    case 94:
                    case 95:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                    case 100:
                        itemstack = Items.COBWEB.getDefaultInstance();
                        break;
                    default:
                        itemstack = ItemStack.EMPTY;
                        break;
                }
                if(itemchance <= 100) ((TreeHollowBlock)hollowblock).setItem(worldgenlevel, blockpos.above(), worldgenlevel.getBlockState(blockpos.above()), itemstack);
            }
            return true;
        }
    }

}