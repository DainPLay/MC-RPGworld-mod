package net.dainplay.rpgworldmod;

import net.dainplay.rpgworldmod.biome.RPGworldRegionProvider;
import net.dainplay.rpgworldmod.block.ModBlocks;
import net.dainplay.rpgworldmod.item.ModItems;
import net.dainplay.rpgworldmod.world.entity.ModEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.Regions;

import java.util.function.Predicate;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RPGworldMod.MOD_ID)
public class RPGworldMod
{
    public static final String MOD_ID = "rpgworldmod";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public RPGworldMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEntities.ENTITY_TYPES.register(eventBus);
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
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WIDOWEED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RIE_TRAPDOOR.get(), RenderType.cutout());

        event.enqueueWork(() -> {
            ItemProperties.register(Items.CROSSBOW, new ResourceLocation("projectruffle"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
                        return p_174607_ != null && CrossbowItem.isCharged(p_174605_) && CrossbowItem.containsChargedProjectile(p_174605_, ModItems.PROJECTRUFFLE_ITEM.get()) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(Items.BOW,
                    new ResourceLocation("projectruffle"), (stack, level, living, id) -> {
                float result = 0.0F;
                if (living instanceof Player)
                {
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
                    if(((Player) living).getOffhandItem().getItem() == Items.ARROW) result = 0.0F;
                    if(((Player) living).getOffhandItem().getItem() == Items.TIPPED_ARROW) result = 0.0F;
                    if(((Player) living).getOffhandItem().getItem() == Items.SPECTRAL_ARROW) result = 0.0F;
                    if(((Player) living).getOffhandItem().getItem() == ModItems.PROJECTRUFFLE_ITEM.get()) result = 1.0F;
                }
                        return result;
                    });
        });

    }
    private void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            // Given we only add two biomes, we should keep our weight relatively low.
            Regions.register(new RPGworldRegionProvider(new ResourceLocation(MOD_ID, "overworld"),1));
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SHIVERALIS.getId(), ModBlocks.POTTED_SHIVERALIS);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RPGIROLLE.getId(), ModBlocks.POTTED_RPGIROLLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PROJECTRUFFLE.getId(), ModBlocks.POTTED_PROJECTRUFFLE);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.RIE_SAPLING.getId(), ModBlocks.POTTED_RIE_SAPLING);
        });

    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
