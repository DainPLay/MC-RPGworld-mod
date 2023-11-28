package net.dainplay.rpgworldmod.util;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.entity.ModEntities;
import net.dainplay.rpgworldmod.entity.custom.Bramblefox;
import net.dainplay.rpgworldmod.entity.custom.Mintobat;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityHandler {
    @SubscribeEvent
    public static void registerAttributes (EntityAttributeCreationEvent event) {
        event.put(ModEntities.BRAMBLEFOX.get(), Bramblefox.createAttributes().build());
        event.put(ModEntities.MINTOBAT.get(), Mintobat.createAttributes().build());
    }
}
