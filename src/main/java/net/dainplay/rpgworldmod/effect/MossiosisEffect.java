package net.dainplay.rpgworldmod.effect;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class MossiosisEffect extends MobEffect {
    public static final UUID MODIFIER_UUID = UUID.fromString("F6A6BC5A-2DC2-48C4-BFFE-0B4B3CB91361");
    public MossiosisEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        addAttributeModifier(Attributes.MAX_HEALTH, MossiosisEffect.MODIFIER_UUID.toString(), 0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(pLivingEntity.getHealth() - (pAmplifier+1)*6 <= 0) {
            if (pLivingEntity.getLastDamageSource() == null) {
                pLivingEntity.hurt(new DamageSource("mossiosis"), pLivingEntity.getHealth());
            } else {
                pLivingEntity.hurt(pLivingEntity.getLastDamageSource().bypassArmor().bypassMagic(), pLivingEntity.getHealth());
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}