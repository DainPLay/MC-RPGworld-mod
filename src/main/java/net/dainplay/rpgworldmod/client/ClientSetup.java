package net.dainplay.rpgworldmod.client;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.client.render.ProjectruffleArrowRenderer;
import net.dainplay.rpgworldmod.world.entity.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PROJECTRUFFLE_ARROW.get(), ProjectruffleArrowRenderer::new);
    }
}