package net.dainplay.rpgworldmod.entity;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.entity.custom.Bramblefox;
import net.dainplay.rpgworldmod.entity.custom.Mintobat;
import net.dainplay.rpgworldmod.entity.custom.ModBoat;
import net.dainplay.rpgworldmod.entity.projectile.FairapierSeedEntity;
import net.dainplay.rpgworldmod.entity.projectile.ProjectruffleArrowEntity;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RPGworldMod.MOD_ID);

    public static final RegistryObject<EntityType<ProjectruffleArrowEntity>> PROJECTRUFFLE_ARROW = ENTITY_TYPES.register("projectruffle_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ProjectruffleArrowEntity>) ProjectruffleArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("projectruffle_arrow"));
    public static final RegistryObject<EntityType<FairapierSeedEntity>> FAIRAPIER_SEED_PROJECTILE = ENTITY_TYPES.register("fairapier_seed_projectile",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FairapierSeedEntity>) FairapierSeedEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("fairapier_seed_projectile"));

    public static final RegistryObject<EntityType<ModBoat>> MODBOAT = ENTITY_TYPES.register("modboat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ModBoat>) ModBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("modboat"));
    public static final RegistryObject<EntityType<Bramblefox>> BRAMBLEFOX = ENTITY_TYPES.register("bramblefox",
            () -> EntityType.Builder.of(Bramblefox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build("bramblefox"));
    public static final RegistryObject<EntityType<Mintobat>> MINTOBAT = ENTITY_TYPES.register("mintobat",
            () -> EntityType.Builder.of(Mintobat::new, MobCategory.MONSTER).sized(0.8F, 1.8F).clientTrackingRange(8).build("mintobat"));
    private static <T extends Entity> Supplier<EntityType<T>> create(String key, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(key, () -> builder.build(key));
    }
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        SpawnPlacements.register(BRAMBLEFOX.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bramblefox::checkBramblefoxSpawnRules);
}

}
