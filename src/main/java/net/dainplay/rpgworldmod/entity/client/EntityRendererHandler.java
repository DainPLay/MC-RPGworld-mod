package net.dainplay.rpgworldmod.entity.client;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.entity.client.model.BramblefoxModel;
import net.dainplay.rpgworldmod.entity.client.model.MintobatModel;
import net.dainplay.rpgworldmod.entity.client.render.*;
import net.dainplay.rpgworldmod.entity.custom.ModBoat;
import net.dainplay.rpgworldmod.entity.ModEntities;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRendererHandler {

    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PROJECTRUFFLE_ARROW.get(), ProjectruffleArrowRenderer::new);
        EntityRenderers.register(ModEntities.FAIRAPIER_SEED_PROJECTILE.get(), FairapierSeedRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BramblefoxModel.LAYER_LOCATION, BramblefoxModel::createBodyLayer);
        event.registerLayerDefinition(MintobatModel.LAYER_LOCATION, MintobatModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        // Register boat layer definitions
        LayerDefinition boatLayerDefinition = BoatModel.createBodyModel();

        for (ModBoat.Type type : ModBoat.Type.values())
        {
            ForgeHooksClient.registerLayerDefinition(ModBoatRenderer.createBoatModelName(type), () -> boatLayerDefinition);
        }
        event.registerEntityRenderer(ModEntities.BRAMBLEFOX.get(), BramblefoxRenderer::new);
        event.registerEntityRenderer(ModEntities.MINTOBAT.get(), MintobatRenderer::new);
        event.registerEntityRenderer(ModEntities.MODBOAT.get(), ModBoatRenderer::new);
    }
}