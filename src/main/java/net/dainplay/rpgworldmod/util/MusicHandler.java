package net.dainplay.rpgworldmod.util;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.dainplay.rpgworldmod.world.feature.ModConfiguredFeatures.RIE_WEALD_MUSIC;
import static net.dainplay.rpgworldmod.world.feature.ModConfiguredFeatures.RIE_WEALD_MUSIC_FOG;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID)
public class MusicHandler {
    @SubscribeEvent
    public static void onPlayerTick (TickEvent.PlayerTickEvent event) {
                Player player = event.player;
                Level world = player.level;
                BlockPos playerPosition = player.blockPosition();
                Biome currentBiome = world.getBiome(playerPosition).value();
                Minecraft minecraft = Minecraft.getInstance();

            if ((world.isRaining() || world.isThundering()) && currentBiome.getRegistryName() != null && currentBiome.getRegistryName().toString().equals("rpgworldmod:rie_weald")
            && (!minecraft.getMusicManager().isPlayingMusic(RIE_WEALD_MUSIC_FOG)
                    && !minecraft.getMusicManager().isPlayingMusic(RIE_WEALD_MUSIC)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.GAME)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.END)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.CREATIVE)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.CREDITS)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.END_BOSS)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.MENU)
                    && !minecraft.getMusicManager().isPlayingMusic(Musics.UNDER_WATER))) {
                    minecraft.getMusicManager().startPlaying(RIE_WEALD_MUSIC_FOG);
            }
    }
}
