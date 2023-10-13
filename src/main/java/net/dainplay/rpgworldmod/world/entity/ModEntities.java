package net.dainplay.rpgworldmod.world.entity;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RPGworldMod.MOD_ID);

    public static final RegistryObject<EntityType<ProjectruffleArrowEntity>> PROJECTRUFFLE_ARROW = ENTITY_TYPES.register("projectruffle_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ProjectruffleArrowEntity>) ProjectruffleArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("projectruffle_arrow"));
}
