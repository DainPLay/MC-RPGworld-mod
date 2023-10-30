package net.dainplay.rpgworldmod.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static net.minecraftforge.client.ClientCommandHandler.sendMessage;


@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID)
public class FogEventHandler {

    @SubscribeEvent
    public static void onRenderFogColors(EntityViewRenderEvent.@NotNull FogColors event) {

        Player player = (Player) event.getCamera().getEntity();
        Level world = player.level;
        BlockPos playerPosition = player.blockPosition();
        Biome currentBiome = world.getBiome(playerPosition).value();
        FogType fogtype = event.getCamera().getFluidInCamera();



        if (!player.hasEffect(MobEffects.BLINDNESS) && fogtype == FogType.NONE && (world.isRaining() || world.isThundering()) && event.getCamera().getEntity() instanceof Player && currentBiome.getRegistryName() != null && currentBiome.getRegistryName().toString().equals("rpgworldmod:rie_weald")) {

            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            double[] c = {Math.abs(10),Math.abs(100),Math.abs(10)} ;

            int r = 10;

            double cal = Math.sqrt( Math.pow(x-c[0], 2) + Math.pow(y-c[1], 2) + Math.pow(z-c[2], 2)  ) ;
            //player.sendMessage(new TextComponent("rayon Color"+cal), player.getUUID() );




            if (r < cal ) {


                Color color = Color.decode("#FFFFFF");
                float red = color.getRed();
                float green = color.getGreen();
                float blue = color.getBlue();

                final float[] fogColors = {red / 255F, green / 255F, blue / 255F};
                event.setRed(fogColors[0]);
                event.setGreen(fogColors[1]);
                event.setBlue(fogColors[2]);


            }
        }
        if (!player.hasEffect(MobEffects.BLINDNESS) && fogtype == FogType.NONE && event.getCamera().getEntity() instanceof Player && currentBiome.getRegistryName() != null && !currentBiome.getRegistryName().toString().equals("rpgworldmod:rie_weald") && rie_far > 0)
        {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            double[] c = {Math.abs(10),Math.abs(100),Math.abs(10)} ;

            int r = 10;

            double cal = Math.sqrt( Math.pow(x-c[0], 2) + Math.pow(y-c[1], 2) + Math.pow(z-c[2], 2)  ) ;
            //player.sendMessage(new TextComponent("rayon Color"+cal), player.getUUID() );




            if (r < cal ) {


                Color color = Color.decode("#FFFFFF");
                float red = color.getRed();
                float green = color.getGreen();
                float blue = color.getBlue();

                final float[] fogColors = {red / 255F, green / 255F, blue / 255F};
                event.setRed(fogColors[0]);
                event.setGreen(fogColors[1]);
                event.setBlue(fogColors[2]);


            }
        }
    }



    public static int rie_far = 0;
    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {

        Player player = (Player) event.getCamera().getEntity();
        Level world = player.level;
        BlockPos playerPosition = player.blockPosition();
        Biome currentBiome = world.getBiome(playerPosition).value();
        FogType fogtype = event.getCamera().getFluidInCamera();

        if(rie_far>0 && !(world.isRaining() || world.isThundering())) rie_far = rie_far-1;

        if (!player.hasEffect(MobEffects.BLINDNESS) && fogtype == FogType.NONE && (world.isRaining() || world.isThundering()) && event.getCamera().getEntity() instanceof Player && currentBiome.getRegistryName() != null && currentBiome.getRegistryName().toString().equals("rpgworldmod:rie_weald")) {
            if (rie_far < event.getFarPlaneDistance() - 30) rie_far = rie_far + 1;
            double x = player.getX();
            double y = player.getY() ;
            double z = player.getZ();

            double[] c = {Math.abs(10),Math.abs(100),Math.abs(10)} ;

            int r = 10;
            double cal = Math.sqrt( Math.pow(x-c[0], 2) + Math.pow(y-c[1], 2) + Math.pow(z-c[2], 2)  ) ;
            //player.sendMessage(new TextComponent("rie_prev: "+rie_prev), player.getUUID() );



            if (r < cal ) {


                RenderSystem.setShaderFogStart(event.getNearPlaneDistance() - event.getNearPlaneDistance()*world.rainLevel);
                RenderSystem.setShaderFogEnd(event.getFarPlaneDistance() + event.getFarPlaneDistance() - 30 - (event.getFarPlaneDistance()-30)*world.rainLevel - rie_far);

            }
        }
        if (!player.hasEffect(MobEffects.BLINDNESS) && fogtype == FogType.NONE && event.getCamera().getEntity() instanceof Player && currentBiome.getRegistryName() != null && !currentBiome.getRegistryName().toString().equals("rpgworldmod:rie_weald"))
        {
            if (rie_far > 0) rie_far = rie_far - 1;
            double x = player.getX();
            double y = player.getY() ;
            double z = player.getZ();

            double[] c = {Math.abs(10),Math.abs(100),Math.abs(10)} ;

            int r = 10;
            double cal = Math.sqrt( Math.pow(x-c[0], 2) + Math.pow(y-c[1], 2) + Math.pow(z-c[2], 2)  ) ;
            //player.sendMessage(new TextComponent("rie_prev: "+rie_prev), player.getUUID() );



            if (r < cal ) {


                RenderSystem.setShaderFogStart(event.getNearPlaneDistance() - rie_far/(event.getFarPlaneDistance() - 30)*event.getNearPlaneDistance());
                RenderSystem.setShaderFogEnd(event.getFarPlaneDistance() - rie_far *world.rainLevel);

            }
        }

    }










}
