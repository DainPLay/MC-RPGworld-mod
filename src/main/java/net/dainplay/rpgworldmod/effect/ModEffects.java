package net.dainplay.rpgworldmod.effect;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RPGworldMod.MOD_ID);

    public static final RegistryObject<MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis",
            () -> new ParalysisEffect(MobEffectCategory.HARMFUL, 16666551));

    public static final RegistryObject<MobEffect> MOSSIOSIS = MOB_EFFECTS.register("mossiosis",
            () -> new MossiosisEffect(MobEffectCategory.HARMFUL, 7377453));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}