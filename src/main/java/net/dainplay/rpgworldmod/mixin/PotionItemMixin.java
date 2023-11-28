package net.dainplay.rpgworldmod.mixin;

import net.dainplay.rpgworldmod.effect.ParalysisEffect;
import net.dainplay.rpgworldmod.item.ModCreativeModeTab;
import net.dainplay.rpgworldmod.potion.ModPotions;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.checkerframework.common.reflection.qual.Invoke;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {

    PotionItem potionItem = (PotionItem) (Object) this;
    @Inject(method = "fillItemCategory", at = @At(value = "HEAD"), cancellable = true)
    private void fillItemCategoryParalysisCheck(CreativeModeTab pGroup, NonNullList<ItemStack> pItems, CallbackInfo ci) {
            for(Potion potion : Registry.POTION) {
                    if (potion == ModPotions.PARALYSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
                    if (potion == ModPotions.STRONG_PARALYSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
                    if (potion == ModPotions.LONG_PARALYSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
                    if (potion == ModPotions.MOSSIOSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
                    if (potion == ModPotions.LONG_MOSSIOSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
                    if (potion == ModPotions.STRONG_MOSSIOSIS_POTION.get()) {
                        if(pGroup == ModCreativeModeTab.RPGWORLD_TAB) {
                            pItems.add(PotionUtils.setPotion(new ItemStack(potionItem), potion));
                        }
                    }
            }
    }
}
