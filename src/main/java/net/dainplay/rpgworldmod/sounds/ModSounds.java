/*******************************************************************************
 * Copyright 2022, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package net.dainplay.rpgworldmod.sounds;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class ModSounds
{
        public static final SoundEvent MUSIC_BIOME_RIE_WEALD = registerSound("music.overworld.rie_weald");
    public static final SoundEvent MUSIC_BIOME_RIE_WEALD_FOG = registerSound("music.overworld.rie_weald_fog");
    public static final SoundEvent ZAP = registerSound("rpgworldmod.zap");
    public static final SoundEvent PARALISED = registerSound("rpgworldmod.paralised");
    public static final SoundEvent MINTOBAT_ATTACK = registerSound("rpgworldmod.mintobat.attack");
    public static final SoundEvent MINTOBAT_HURT = registerSound("rpgworldmod.mintobat.hit");
    public static final SoundEvent MINTOBAT_DEATH = registerSound("rpgworldmod.mintobat.death");
    public static final SoundEvent MINTOBAT_AMBIENT = registerSound("rpgworldmod.mintobat.ambient");

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(MUSIC_BIOME_RIE_WEALD,MUSIC_BIOME_RIE_WEALD_FOG,ZAP,PARALISED,MINTOBAT_ATTACK, MINTOBAT_HURT,MINTOBAT_DEATH,MINTOBAT_AMBIENT);
    }

    private static SoundEvent registerSound(String sound)
    {
        ResourceLocation name = RPGworldMod.prefix(sound);
        return new SoundEvent(name).setRegistryName(name);
    }
}