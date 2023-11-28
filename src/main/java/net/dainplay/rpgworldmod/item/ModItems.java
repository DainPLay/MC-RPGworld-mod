package net.dainplay.rpgworldmod.item;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.entity.ModEntities;
import net.dainplay.rpgworldmod.item.custom.*;
import net.dainplay.rpgworldmod.entity.custom.ModBoat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
    public static final RegistryObject<Item> FAIRAPIER_SWORD = ITEMS.register("fairapier_sword",
            () -> new FairapierSwordItem(ModTiers.FAIRAPIER, 3, -1.9f, new Item.Properties().rarity(Rarity.UNCOMMON).tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> FAIRAPIER_SWORD_WILTED = ITEMS.register("fairapier_sword_wilted",
            () -> new FairapierSwordWiltedItem(ModBlocks.FAIRAPIER_WILTED_PLANT.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

    public static final RegistryObject<Item> MINTAL_INGOT = ITEMS.register("mintal_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MINTAL_NUGGET = ITEMS.register("mintal_nugget",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MINTAL_TRIANGLE = ITEMS.register("mintal_triangle",
            () -> new MintalTriangleItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> RIE_FRUIT = ITEMS.register("rie_fruit",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.RIE_FRUIT)));
    public static final RegistryObject<Item> BRAMBLEFOX_BERRIES = ITEMS.register("bramblefox_berries",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.BRAMBLEFOX_BERRIES)));
    public static final RegistryObject<Item> FAIRAPIER_SEED = ITEMS.register("fairapier_seed",
            () -> new FairapierSeedItem(ModBlocks.FAIRAPIER_PLANT.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).stacksTo(16)));
    public static final RegistryObject<Item> SHIVERALIS_BERRIES = ITEMS.register("shiveralis_berries",
            () -> new ItemNameBlockItem(ModBlocks.SHIVERALIS.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.SHIVERALIS_BERRIES)));
    public static final RegistryObject<Item> PARALILY = ITEMS.register("paralily",
            () -> new WaterLilyBlockItem(ModBlocks.PARALILY.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> PARALILY_BERRY = ITEMS.register("paralily_berry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.PARALILY_BERRY)));
    public static final RegistryObject<Item> RPGIROLLE_ITEM = ITEMS.register("rpgirolle_item",
            () -> new ItemNameBlockItem(ModBlocks.RPGIROLLE.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.RPGIROLLE)));
    public static final RegistryObject<Item> CHEESE_CAP = ITEMS.register("cheese_cap",
            () -> new CheeseItem(ModBlocks.CHEESE_CAP.get(), new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).food(ModFoods.CHEESE_CAP)));
    public static final RegistryObject<Item> PROJECTRUFFLE_ITEM = ITEMS.register("projectruffle_item",
            () -> new ProjectruffleArrowItem(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> RIE_SIGN = ITEMS.register("rie_sign",
            () -> new SignItem(new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB).stacksTo(16),
                    ModBlocks.RIE_SIGN.get(), ModBlocks.RIE_WALL_SIGN.get()));
    public static final RegistryObject<Item> RIE_BOAT = ITEMS.register("rie_boat",
            () -> new ModBoatItem(ModBoat.Type.RIE, (new Item.Properties()).stacksTo(1).tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> BRAMBLEFOX_SPAWN_EGG = ITEMS.register("bramblefox_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BRAMBLEFOX, 0x18693F, 0xE27C21, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<Item> MINTOBAT_SPAWN_EGG = ITEMS.register("mintobat_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MINTOBAT, 0x14B485, 0x263C47, new Item.Properties().tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static final RegistryObject<CurioItem> BRAMBLEFOX_SCARF = ITEMS.register("bramblefox_scarf", () -> new CurioItem(new Item.Properties().rarity(Rarity.UNCOMMON).tab(ModCreativeModeTab.RPGWORLD_TAB)));
    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}
