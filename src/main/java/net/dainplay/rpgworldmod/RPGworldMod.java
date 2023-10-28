package net.dainplay.rpgworldmod;

import net.dainplay.rpgworldmod.biome.RPGworldRegionProvider;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.block.entity.ModBlockEntities;
import net.dainplay.rpgworldmod.block.entity.ModWoodTypes;
import net.dainplay.rpgworldmod.init.ModFeatures;
import net.dainplay.rpgworldmod.item.ModItems;
import net.dainplay.rpgworldmod.world.entity.ModEntities;
import net.dainplay.rpgworldmod.world.entity.ProjectruffleArrowEntity;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static net.minecraft.core.Registry.FEATURE;
import static net.minecraft.core.Registry.FEATURE_REGISTRY;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RPGworldMod.MOD_ID)
public class RPGworldMod
{
    public static final String MOD_ID = "rpgworldmod";
    public static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(FEATURE_REGISTRY, MOD_ID);
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public RPGworldMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModEntities.ENTITY_TYPES.register(eventBus);
        FEATURE_REGISTER.register(eventBus);
        ModFeatures.setup();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MASKONITE_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MASKONITE_GLASS_PANE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHIVERALIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_SHIVERALIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RIE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RPGIROLLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_RPGIROLLE.get(), RenderType.cutout());
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

        WoodType.register(ModWoodTypes.RIE);
        BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);

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
        event.enqueueWork(() -> {
            // Given we only add two biomes, we should keep our weight relatively low.

            ComposterBlock.COMPOSTABLES.put(ModBlocks.RIE_LEAVES.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.RIE_FRUIT.get().asItem(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModItems.SHIVERALIS_BERRIES.get().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.RPGIROLLE_ITEM.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.HOLTS_REFLECTION.get().asItem(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPIKY_IVY.get().asItem(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.RIE_SAPLING.get().asItem(), 0.3F);
            Regions.register(new RPGworldRegionProvider(new ResourceLocation(MOD_ID, "overworld"),1));
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SHIVERALIS.getId(), ModBlocks.POTTED_SHIVERALIS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RPGIROLLE.getId(), ModBlocks.POTTED_RPGIROLLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PROJECTRUFFLE.getId(), ModBlocks.POTTED_PROJECTRUFFLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RIE_SAPLING.getId(), ModBlocks.POTTED_RIE_SAPLING);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SPIKY_IVY.getId(), ModBlocks.POTTED_SPIKY_IVY);
            DispenserBlock.registerBehavior(ModItems.PROJECTRUFFLE_ITEM.get(), new AbstractProjectileDispenseBehavior() {
                /**
                 * Return the projectile entity spawned by this dispense behavior.
                 */
                protected Projectile getProjectile(Level p_123407_, Position p_123408_, ItemStack p_123409_) {
                    ProjectruffleArrowEntity arrow = new ProjectruffleArrowEntity(ModEntities.PROJECTRUFFLE_ARROW.get(), p_123408_.x(), p_123408_.y(), p_123408_.z(), p_123407_);
                    arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                    return arrow;
                }
            });
            Sheets.addWoodType(ModWoodTypes.RIE);
        });


    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
