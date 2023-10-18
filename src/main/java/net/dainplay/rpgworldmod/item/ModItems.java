package net.dainplay.rpgworldmod.item;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.item.custom.ProjectruffleArrowItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RPGworldMod.MOD_ID);
    public static final RegistryObject<Item> MASKONITE_SWORD = ITEMS.register("maskonite_sword",
            () -> new SwordItem(ModTiers.MASKONITE, 3, -2.4f, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MASKONITE_PICKAXE = ITEMS.register("maskonite_pickaxe",
            () -> new PickaxeItem(ModTiers.MASKONITE, 1, -2.8f, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MASKONITE_AXE = ITEMS.register("maskonite_axe",
            () -> new AxeItem(ModTiers.MASKONITE, 7, -3.2f, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MASKONITE_SHOVEL = ITEMS.register("maskonite_shovel",
            () -> new ShovelItem(ModTiers.MASKONITE, 1.5f, -3f, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MASKONITE_HOE = ITEMS.register("maskonite_hoe",
            () -> new HoeItem(ModTiers.MASKONITE, -1, -2f, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));

    public static final RegistryObject<Item> RIE_FRUIT = ITEMS.register("rie_fruit",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.RIE_FRUIT)));
    public static final RegistryObject<Item> SHIVERALIS_BERRIES = ITEMS.register("shiveralis_berries",
            () -> new ItemNameBlockItem(ModBlocks.SHIVERALIS.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.SHIVERALIS_BERRIES)));
    public static final RegistryObject<Item> RPGIROLLE_ITEM = ITEMS.register("rpgirolle_item",
            () -> new ItemNameBlockItem(ModBlocks.RPGIROLLE.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.RPGIROLLE)));
    public static final RegistryObject<Item> PROJECTRUFFLE_ITEM = ITEMS.register("projectruffle_item",
            () -> new ProjectruffleArrowItem(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
