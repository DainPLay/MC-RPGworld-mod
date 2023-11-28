package net.dainplay.rpgworldmod.item;

import net.dainplay.rpgworldmod.potion.ModPotions;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ModCreativeModeTab {
    public static final CreativeModeTab RPGWORLD_TAB = new CreativeModeTab("rpgworldtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RPGIROLLE_ITEM.get());
        }

    };
}
