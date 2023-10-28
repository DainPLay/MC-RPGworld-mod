package net.dainplay.rpgworldmod.client;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.client.render.ProjectruffleArrowRenderer;
import net.dainplay.rpgworldmod.world.entity.ModBoat;
import net.dainplay.rpgworldmod.world.entity.ModEntities;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.dainplay.rpgworldmod.client.render.ModBoatRenderer;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PROJECTRUFFLE_ARROW.get(), ProjectruffleArrowRenderer::new);
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

        event.registerEntityRenderer((EntityType<ModBoat>) ModEntities.MODBOAT.get(), ModBoatRenderer::new);
    }
}