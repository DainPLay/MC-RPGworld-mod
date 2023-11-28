package net.dainplay.rpgworldmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dainplay.rpgworldmod.gui.OverlayEventHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {


    @Inject(method = "renderHearts", at = @At(value = "HEAD"))
    private void Send(PoseStack pPoseStack, Player pPlayer, int pX, int pY, int pHeight, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_, CallbackInfo ci) {
        OverlayEventHandler.setRenderHeartY(pY);
        OverlayEventHandler.setRegen(p_168694_);
    }
}
