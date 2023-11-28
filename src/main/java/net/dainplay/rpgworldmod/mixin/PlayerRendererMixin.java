package net.dainplay.rpgworldmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.fml.common.Mod;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    PlayerRenderer playerRenderer = (PlayerRenderer) (Object) this;
    @Inject(method = "renderHand", at = @At(value = "TAIL"), cancellable = true)
    private void renderHandMossiosisCheck(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear, CallbackInfo ci) {
        if (pPlayer.hasEffect(ModEffects.MOSSIOSIS.get())) {
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(RPGworldMod.MOD_ID, "textures/entity/mossiosis_overlay.png")));
            pRendererArmwear.render(pMatrixStack, ivertexbuilder, pCombinedLight, OverlayTexture.NO_OVERLAY);
        }
    }
    @Inject(method = "renderHand", at = @At(value = "HEAD"), cancellable = true)
    private void renderHandInvisCheck(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear, CallbackInfo ci) {
        if (pPlayer.isInvisible() && !pPlayer.hasEffect(ModEffects.MOSSIOSIS.get())) {
            ci.cancel();
        }
    }


    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD), cancellable = true)
    private void renderHandMossiosisAndInvisCheck(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear, CallbackInfo ci) {
        if (pPlayer.isInvisible() && pPlayer.hasEffect(ModEffects.MOSSIOSIS.get())) {
            PlayerModel<AbstractClientPlayer> playermodel = playerRenderer.getModel();
            playermodel.attackTime = 0.0F;
            playermodel.crouching = false;
            playermodel.swimAmount = 0.0F;
            pRendererArmwear.xRot = 0.0F;
            VertexConsumer ivertexbuilder = pBuffer.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(RPGworldMod.MOD_ID, "textures/entity/mossiosis_overlay.png")));
            pRendererArmwear.render(pMatrixStack, ivertexbuilder, pCombinedLight, OverlayTexture.NO_OVERLAY);
            ci.cancel();
        }
    }
}
