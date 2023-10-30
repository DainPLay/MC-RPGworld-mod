/*******************************************************************************
 * Copyright 2022, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package net.dainplay.rpgworldmod.sounds;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;

import static net.dainplay.rpgworldmod.sounds.RPGSounds.*;

public class ModSounds
{
        public static final SoundEvent MUSIC_BIOME_RIE_WEALD = registerSound("music.overworld.rie_weald");
    public static final SoundEvent MUSIC_BIOME_RIE_WEALD_FOG = registerSound("music.overworld.rie_weald_fog");

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(MUSIC_BIOME_RIE_WEALD,MUSIC_BIOME_RIE_WEALD_FOG);
    }

    private static SoundEvent registerSound(String sound)
    {
        ResourceLocation name = RPGworldMod.prefix(sound);
        return new SoundEvent(name).setRegistryName(name);
    }
}