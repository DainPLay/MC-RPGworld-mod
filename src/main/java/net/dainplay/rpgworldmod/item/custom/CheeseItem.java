package net.dainplay.rpgworldmod.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CheeseItem extends ItemNameBlockItem {
    public CheeseItem(Block p_41579_, Properties p_41580_) {
        super(p_41579_, p_41580_);
    }




@Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        pEntityLiving.curePotionEffects(Items.MILK_BUCKET.getDefaultInstance());

        if (pEntityLiving instanceof Player && !((Player)pEntityLiving).getAbilities().instabuild) {
            pEntityLiving.eat(pLevel, pStack);
        }

        return pStack;
    }
}
