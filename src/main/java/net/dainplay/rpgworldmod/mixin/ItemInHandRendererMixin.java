package net.dainplay.rpgworldmod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    ItemInHandRenderer handRenderer = (ItemInHandRenderer) (Object) this;


    @Inject(method = "renderOneHandedMap", at = @At(value = "HEAD"), cancellable = true)
    private void renderOneHandedMapMossiosisAndInvisCheck(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, HumanoidArm pHand, float pSwingProgress, ItemStack pStack, CallbackInfo ci) {
        float f = pHand == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        pMatrixStack.translate((double)(f * 0.125F), -0.125D, 0.0D);
        if (!handRenderer.minecraft.player.isInvisible()) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 10.0F));
            handRenderer.renderPlayerArm(pMatrixStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pHand);
            pMatrixStack.popPose();
        }
        else if (handRenderer.minecraft.player.isInvisible() && handRenderer.minecraft.player.hasEffect(ModEffects.MOSSIOSIS.get())) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 10.0F));
            handRenderer.renderPlayerArm(pMatrixStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pHand);
            pMatrixStack.popPose();
        }

        pMatrixStack.pushPose();
        pMatrixStack.translate((double)(f * 0.51F), (double)(-0.08F + pEquippedProgress * -1.2F), -0.75D);
        float f1 = Mth.sqrt(pSwingProgress);
        float f2 = Mth.sin(f1 * (float)Math.PI);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * Mth.sin(f1 * ((float)Math.PI * 2F));
        float f5 = -0.3F * Mth.sin(pSwingProgress * (float)Math.PI);
        pMatrixStack.translate((double)(f * f3), (double)(f4 - 0.3F * f2), (double)f5);
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f2 * -45.0F));
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(f * f2 * -30.0F));
        handRenderer.renderMap(pMatrixStack, pBuffer, pCombinedLight, pStack);
        pMatrixStack.popPose();
        ci.cancel();
    }
    @Inject(method = "renderTwoHandedMap", at = @At(value = "HEAD"), cancellable = true)
    private void renderTwoHandedMapMossiosisAndInvisCheck(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, float pPitch, float pEquippedProgress, float pSwingProgress, CallbackInfo ci) {
        float f = Mth.sqrt(pSwingProgress);
        float f1 = -0.2F * Mth.sin(pSwingProgress * (float)Math.PI);
        float f2 = -0.4F * Mth.sin(f * (float)Math.PI);
        pMatrixStack.translate(0.0D, (double)(-f1 / 2.0F), (double)f2);
        float f3 = handRenderer.calculateMapTilt(pPitch);
        pMatrixStack.translate(0.0D, (double)(0.04F + pEquippedProgress * -1.2F + f3 * -0.5F), (double)-0.72F);
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f3 * -85.0F));
        if (!handRenderer.minecraft.player.isInvisible()) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            handRenderer.renderMapHand(pMatrixStack, pBuffer, pCombinedLight, HumanoidArm.RIGHT);
            handRenderer.renderMapHand(pMatrixStack, pBuffer, pCombinedLight, HumanoidArm.LEFT);
            pMatrixStack.popPose();
        }
        else if (handRenderer.minecraft.player.isInvisible() && handRenderer.minecraft.player.hasEffect(ModEffects.MOSSIOSIS.get())) {
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            handRenderer.renderMapHand(pMatrixStack, pBuffer, pCombinedLight, HumanoidArm.RIGHT);
            handRenderer.renderMapHand(pMatrixStack, pBuffer, pCombinedLight, HumanoidArm.LEFT);
            pMatrixStack.popPose();
        }

        float f4 = Mth.sin(f * (float)Math.PI);
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f4 * 20.0F));
        pMatrixStack.scale(2.0F, 2.0F, 2.0F);
        handRenderer.renderMap(pMatrixStack, pBuffer, pCombinedLight, handRenderer.mainHandItem);
        ci.cancel();
    }
    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmWithItemMossiosisAndInvisCheck(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (!pPlayer.isScoping()) {
            boolean flag = pHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidarm = flag ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
            pMatrixStack.pushPose();
            if (pStack.isEmpty()) {
                if (flag && !pPlayer.isInvisible()) {
                    handRenderer.renderPlayerArm(pMatrixStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, humanoidarm);
                }
                else if (flag && pPlayer.isInvisible() && pPlayer.hasEffect(ModEffects.MOSSIOSIS.get())) {
                    handRenderer.renderPlayerArm(pMatrixStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, humanoidarm);
                }
            } else if (pStack.is(Items.FILLED_MAP)) {
                if (flag && handRenderer.offHandItem.isEmpty()) {
                    handRenderer.renderTwoHandedMap(pMatrixStack, pBuffer, pCombinedLight, pPitch, pEquippedProgress, pSwingProgress);
                } else {
                    handRenderer.renderOneHandedMap(pMatrixStack, pBuffer, pCombinedLight, pEquippedProgress, humanoidarm, pSwingProgress, pStack);
                }
            } else if (pStack.getItem() instanceof CrossbowItem) {
                boolean flag1 = CrossbowItem.isCharged(pStack);
                boolean flag2 = humanoidarm == HumanoidArm.RIGHT;
                int i = flag2 ? 1 : -1;
                if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                    handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                    pMatrixStack.translate((double)((float)i * -0.4785682F), (double)-0.094387F, (double)0.05731531F);
                    pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-11.935F));
                    pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)i * 65.3F));
                    pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees((float)i * -9.785F));
                    float f9 = (float)pStack.getUseDuration() - ((float)handRenderer.minecraft.player.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                    float f13 = f9 / (float)CrossbowItem.getChargeDuration(pStack);
                    if (f13 > 1.0F) {
                        f13 = 1.0F;
                    }

                    if (f13 > 0.1F) {
                        float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
                        float f3 = f13 - 0.1F;
                        float f4 = f16 * f3;
                        pMatrixStack.translate((double)(f4 * 0.0F), (double)(f4 * 0.004F), (double)(f4 * 0.0F));
                    }

                    pMatrixStack.translate((double)(f13 * 0.0F), (double)(f13 * 0.0F), (double)(f13 * 0.04F));
                    pMatrixStack.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                    pMatrixStack.mulPose(Vector3f.YN.rotationDegrees((float)i * 45.0F));
                } else {
                    float f = -0.4F * Mth.sin(Mth.sqrt(pSwingProgress) * (float)Math.PI);
                    float f1 = 0.2F * Mth.sin(Mth.sqrt(pSwingProgress) * ((float)Math.PI * 2F));
                    float f2 = -0.2F * Mth.sin(pSwingProgress * (float)Math.PI);
                    pMatrixStack.translate((double)((float)i * f), (double)f1, (double)f2);
                    handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                    handRenderer.applyItemArmAttackTransform(pMatrixStack, humanoidarm, pSwingProgress);
                    if (flag1 && pSwingProgress < 0.001F && flag) {
                        pMatrixStack.translate((double)((float)i * -0.641864F), 0.0D, 0.0D);
                        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)i * 10.0F));
                    }
                }

                handRenderer.renderItem(pPlayer, pStack, flag2 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag2, pMatrixStack, pBuffer, pCombinedLight);
            } else {
                boolean flag3 = humanoidarm == HumanoidArm.RIGHT;
                if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                    int k = flag3 ? 1 : -1;
                    switch(pStack.getUseAnimation()) {
                        case NONE:
                            handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                            break;
                        case EAT:
                        case DRINK:
                            handRenderer.applyEatTransform(pMatrixStack, pPartialTicks, humanoidarm, pStack);
                            handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                            break;
                        case BLOCK:
                            handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                            break;
                        case BOW:
                            handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                            pMatrixStack.translate((double)((float)k * -0.2785682F), (double)0.18344387F, (double)0.15731531F);
                            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
                            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)k * 35.3F));
                            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees((float)k * -9.785F));
                            float f8 = (float)pStack.getUseDuration() - ((float)handRenderer.minecraft.player.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                            float f12 = f8 / 20.0F;
                            f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                            if (f12 > 1.0F) {
                                f12 = 1.0F;
                            }

                            if (f12 > 0.1F) {
                                float f15 = Mth.sin((f8 - 0.1F) * 1.3F);
                                float f18 = f12 - 0.1F;
                                float f20 = f15 * f18;
                                pMatrixStack.translate((double)(f20 * 0.0F), (double)(f20 * 0.004F), (double)(f20 * 0.0F));
                            }

                            pMatrixStack.translate((double)(f12 * 0.0F), (double)(f12 * 0.0F), (double)(f12 * 0.04F));
                            pMatrixStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                            pMatrixStack.mulPose(Vector3f.YN.rotationDegrees((float)k * 45.0F));
                            break;
                        case SPEAR:
                            handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                            pMatrixStack.translate((double)((float)k * -0.5F), (double)0.7F, (double)0.1F);
                            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-55.0F));
                            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)k * 35.3F));
                            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees((float)k * -9.785F));
                            float f7 = (float)pStack.getUseDuration() - ((float)handRenderer.minecraft.player.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                            float f11 = f7 / 10.0F;
                            if (f11 > 1.0F) {
                                f11 = 1.0F;
                            }

                            if (f11 > 0.1F) {
                                float f14 = Mth.sin((f7 - 0.1F) * 1.3F);
                                float f17 = f11 - 0.1F;
                                float f19 = f14 * f17;
                                pMatrixStack.translate((double)(f19 * 0.0F), (double)(f19 * 0.004F), (double)(f19 * 0.0F));
                            }

                            pMatrixStack.translate(0.0D, 0.0D, (double)(f11 * 0.2F));
                            pMatrixStack.scale(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                            pMatrixStack.mulPose(Vector3f.YN.rotationDegrees((float)k * 45.0F));
                    }
                } else if (pPlayer.isAutoSpinAttack()) {
                    handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                    int j = flag3 ? 1 : -1;
                    pMatrixStack.translate((double)((float)j * -0.4F), (double)0.8F, (double)0.3F);
                    pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float)j * 65.0F));
                    pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees((float)j * -85.0F));
                } else {
                    float f5 = -0.4F * Mth.sin(Mth.sqrt(pSwingProgress) * (float)Math.PI);
                    float f6 = 0.2F * Mth.sin(Mth.sqrt(pSwingProgress) * ((float)Math.PI * 2F));
                    float f10 = -0.2F * Mth.sin(pSwingProgress * (float)Math.PI);
                    int l = flag3 ? 1 : -1;
                    pMatrixStack.translate((double)((float)l * f5), (double)f6, (double)f10);
                    handRenderer.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);
                    handRenderer.applyItemArmAttackTransform(pMatrixStack, humanoidarm, pSwingProgress);
                }

                handRenderer.renderItem(pPlayer, pStack, flag3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, pMatrixStack, pBuffer, pCombinedLight);
            }

            pMatrixStack.popPose();
        }
        ci.cancel();
    }
}
