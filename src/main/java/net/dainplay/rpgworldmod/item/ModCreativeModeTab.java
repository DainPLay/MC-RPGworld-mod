package net.dainplay.rpgworldmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab RPGWORLD_TAB = new CreativeModeTab("rpgworldtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RPGIROLLE_ITEM.get());
        }
    };
}
