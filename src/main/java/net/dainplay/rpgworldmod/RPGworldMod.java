package net.dainplay.rpgworldmod;

import net.dainplay.rpgworldmod.biome.RPGworldRegionProvider;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.block.entity.ModBlockEntities;
import net.dainplay.rpgworldmod.block.entity.ModWoodTypes;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.dainplay.rpgworldmod.entity.client.render.ClientLayerRegistry;
import net.dainplay.rpgworldmod.entity.client.render.CurioLayers;
import net.dainplay.rpgworldmod.entity.client.render.CurioRenderers;
import net.dainplay.rpgworldmod.gui.OverlayEventHandler;
import net.dainplay.rpgworldmod.init.ModFeatures;
import net.dainplay.rpgworldmod.item.ModItems;
import net.dainplay.rpgworldmod.potion.ModPotions;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.dainplay.rpgworldmod.entity.projectile.FairapierSeedEntity;
import net.dainplay.rpgworldmod.entity.ModEntities;
import net.dainplay.rpgworldmod.entity.projectile.ProjectruffleArrowEntity;
import net.dainplay.rpgworldmod.util.ModBrewingRecipe;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import terrablender.api.Regions;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Locale;
import java.util.stream.Collectors;

import static net.minecraft.core.Registry.FEATURE_REGISTRY;
@Mod(RPGworldMod.MOD_ID)
public class RPGworldMod
{
    public static final String MOD_ID = "rpgworldmod";
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(FEATURE_REGISTRY, MOD_ID);
    public static final Logger LOGGER = LogManager.getLogger();
    public RPGworldMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModEntities.ENTITY_TYPES.register(eventBus);
        FEATURE_REGISTER.register(eventBus);
        ModEffects.register(eventBus);
        ModPotions.register(eventBus);
        ModFeatures.setup();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::processIMC);
        eventBus.addListener(this::onRegisterLayerDefinitions);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addGenericListener(SoundEvent.class, ModSounds::registerSounds);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientLayerRegistry::onAddLayers);
        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MASKONITE_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MASKONITE_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHIVERALIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SHIVERALIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RIE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RPGIROLLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FAIRAPIER_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FAIRAPIER_WILTED_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RPGIROLLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WILD_FAIRAPIER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_WILD_FAIRAPIER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MIMOSSA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_MIMOSSA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PARALILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PROJECTRUFFLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_PROJECTRUFFLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HOLTS_REFLECTION.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPIKY_IVY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SPIKY_IVY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WIDOWEED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MOSSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_MOSSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHEESE_CAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_CHEESE_CAP.get(), RenderType.cutout());

        WoodType.register(ModWoodTypes.RIE);
        BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
        CurioRenderers.register();

        event.enqueueWork(() -> {
            ItemProperties.register(Items.CROSSBOW, new ResourceLocation("projectruffle"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
                        return p_174607_ != null && CrossbowItem.isCharged(p_174605_) && CrossbowItem.containsChargedProjectile(p_174605_, ModItems.PROJECTRUFFLE_ITEM.get()) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(Items.BOW,
                    new ResourceLocation("projectruffle"), (stack, level, living, id) -> {
                float result = 0.0F;
                if (living instanceof Player) {
                    if (((Player) living).getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.BOW) {
                        for (int i = 0; i < ((Player) living).getInventory().getContainerSize(); ++i) {
                            if (ModItems.PROJECTRUFFLE_ITEM.get() == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 1.0F;
                                break;
                                //return result;
                            }
                            if (Items.ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                            if (Items.TIPPED_ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                            if (Items.SPECTRAL_ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                        }
                        if (((Player) living).getOffhandItem().getItem() == Items.ARROW) result = 0.0F;
                        if (((Player) living).getOffhandItem().getItem() == Items.TIPPED_ARROW) result = 0.0F;
                        if (((Player) living).getOffhandItem().getItem() == Items.SPECTRAL_ARROW) result = 0.0F;
                        if (((Player) living).getOffhandItem().getItem() == ModItems.PROJECTRUFFLE_ITEM.get())
                            result = 1.0F;
                    }
                    if (((Player) living).getItemInHand(InteractionHand.OFF_HAND).getItem() == Items.BOW) {
                        for (int i = 0; i < ((Player) living).getInventory().getContainerSize(); ++i) {
                            if (ModItems.PROJECTRUFFLE_ITEM.get() == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 1.0F;
                                break;
                                //return result;
                            }
                            if (Items.ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                            if (Items.TIPPED_ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                            if (Items.SPECTRAL_ARROW == (((Player) living).getInventory().getItem(i)).getItem()) {
                                result = 0.0F;
                                break;
                                //return result;
                            }
                        }
                        if (((Player) living).getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.ARROW) result = 0.0F;
                        if (((Player) living).getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.TIPPED_ARROW) result = 0.0F;
                        if (((Player) living).getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.SPECTRAL_ARROW) result = 0.0F;
                        if (((Player) living).getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.PROJECTRUFFLE_ITEM.get())
                            result = 1.0F;
                    }
                }
                        return result;
                    });
        });

    }
    private void setup(final FMLCommonSetupEvent event)
    {
        OverlayRegistry.OverlayEntry entry = OverlayRegistry.getEntry(ForgeIngameGui.PLAYER_HEALTH_ELEMENT);
        //Register Armor Renderer for events
        assert entry != null;
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.PLAYER_HEALTH_ELEMENT, entry.getDisplayName(), OverlayEventHandler.overlay);
        event.enqueueWork(() -> {
            // Given we only add two biomes, we should keep our weight relatively low.

            ComposterBlock.COMPOSTABLES.put(ModBlocks.RIE_LEAVES.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.FAIRAPIER_SEED.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.RIE_FRUIT.get().asItem(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModItems.SHIVERALIS_BERRIES.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.PARALILY_BERRY.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.RPGIROLLE_ITEM.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.HOLTS_REFLECTION.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.WILD_FAIRAPIER.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.MIMOSSA.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModItems.PARALILY.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPIKY_IVY.get().asItem(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.RIE_SAPLING.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.BRAMBLEFOX_BERRIES.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.MOSSHROOM.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CHEESE_CAP.get().asItem(), 0.65F);
            Regions.register(new RPGworldRegionProvider(new ResourceLocation(MOD_ID, "overworld"),1));
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SHIVERALIS.getId(), ModBlocks.POTTED_SHIVERALIS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RPGIROLLE.getId(), ModBlocks.POTTED_RPGIROLLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PROJECTRUFFLE.getId(), ModBlocks.POTTED_PROJECTRUFFLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RIE_SAPLING.getId(), ModBlocks.POTTED_RIE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SPIKY_IVY.getId(), ModBlocks.POTTED_SPIKY_IVY);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_FAIRAPIER.getId(), ModBlocks.POTTED_WILD_FAIRAPIER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MIMOSSA.getId(), ModBlocks.POTTED_MIMOSSA);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.MOSSHROOM.getId(), ModBlocks.POTTED_MOSSHROOM);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHEESE_CAP.getId(), ModBlocks.POTTED_CHEESE_CAP);
            DispenserBlock.registerBehavior(ModItems.PROJECTRUFFLE_ITEM.get(), new AbstractProjectileDispenseBehavior() {
                protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                    ProjectruffleArrowEntity arrow = new ProjectruffleArrowEntity(ModEntities.PROJECTRUFFLE_ARROW.get(), p_123408_.x(), p_123408_.y(), p_123408_.z(), p_123407_);
                    arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                    return arrow;
                }
            });
            DispenserBlock.registerBehavior(ModItems.FAIRAPIER_SEED.get(), new AbstractProjectileDispenseBehavior() {
                protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                    FairapierSeedEntity arrow = new FairapierSeedEntity(ModEntities.FAIRAPIER_SEED_PROJECTILE.get(), p_123408_.x(), p_123408_.y(), p_123408_.z(), p_123407_);
                    arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                    return arrow;
                }
            });
            Sheets.addWoodType(ModWoodTypes.RIE);

            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ModItems.PARALILY_BERRY.get(), ModPotions.PARALYSIS_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(ModPotions.PARALYSIS_POTION.get(), Items.REDSTONE, ModPotions.LONG_PARALYSIS_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(ModPotions.PARALYSIS_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_PARALYSIS_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Potions.AWKWARD, ModBlocks.MOSSHROOM.get().asItem(), ModPotions.MOSSIOSIS_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(ModPotions.MOSSIOSIS_POTION.get(), Items.REDSTONE, ModPotions.LONG_MOSSIOSIS_POTION.get()));
            BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(ModPotions.MOSSIOSIS_POTION.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_MOSSIOSIS_POTION.get()));
        });


    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        CurioLayers.register(event);
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        SlotTypePreset[] types = {SlotTypePreset.NECKLACE};
        for (SlotTypePreset type : types) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
    }

}
