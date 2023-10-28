package net.dainplay.rpgworldmod.world.entity;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RPGworldMod.MOD_ID);

    public static final RegistryObject<EntityType<ProjectruffleArrowEntity>> PROJECTRUFFLE_ARROW = ENTITY_TYPES.register("projectruffle_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ProjectruffleArrowEntity>) ProjectruffleArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("projectruffle_arrow"));

    public static final RegistryObject<EntityType<ModBoat>> MODBOAT = ENTITY_TYPES.register("modboat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ModBoat>) ModBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("modboat"));
    private static <T extends Entity> Supplier<EntityType<T>> create(String key, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(key, () -> builder.build(key));
    }
}
