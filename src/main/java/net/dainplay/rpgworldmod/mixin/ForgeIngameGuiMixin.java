package net.dainplay.rpgworldmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeIngameGui.class)
public class ForgeIngameGuiMixin {

    @Unique
    private static void renderParalysis(PoseStack pStack)
    {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()) != null && Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()).getAmplifier() >= 1) {
            Minecraft.getInstance().gui.renderTextureOverlay(new ResourceLocation(RPGworldMod.MOD_ID,"textures/misc/paralysis_outline.png"), (float) Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()).getDuration() /200);
        }
    }

    @Unique
    private static final IIngameOverlay PARALYSIS_ELEMENT = OverlayRegistry.registerOverlayTop("Paralysis", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        gui.setupOverlayRenderState(true, false);
        renderParalysis(poseStack);
    });
    @Inject(method = "render", at = @At(value = "TAIL"))
    private void renderParalysisCheck(CallbackInfo ci) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()) != null && Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()).getAmplifier() >= 1) {

                Minecraft.getInstance().gui.renderTextureOverlay(new ResourceLocation(RPGworldMod.MOD_ID,"textures/misc/paralysis_outline.png"), (float) Minecraft.getInstance().player.getEffect(ModEffects.PARALYSIS.get()).getDuration() /200);

        }
    }
}
