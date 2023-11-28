package net.dainplay.rpgworldmod.item;

import net.dainplay.rpgworldmod.effect.ModEffects;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties RIE_FRUIT = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).fast().build();
    public static final FoodProperties SHIVERALIS_BERRIES = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 100, 0), 1.0F).build();
    public static final FoodProperties RPGIROLLE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
    public static final FoodProperties BRAMBLEFOX_BERRIES = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).build();
    public static final FoodProperties PARALILY_BERRY = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(ModEffects.PARALYSIS.get(), 300, 0), 1.0F).build();
    public static final FoodProperties CHEESE_CAP = (new FoodProperties.Builder()).nutrition(1).alwaysEat().build();
}
