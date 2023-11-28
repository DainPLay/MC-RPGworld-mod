package net.dainplay.rpgworldmod.effect;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.effect.ModEffects;
import net.dainplay.rpgworldmod.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

import static net.dainplay.rpgworldmod.world.feature.ModConfiguredFeatures.RIE_WEALD_MUSIC;

@Mod.EventBusSubscriber(modid = RPGworldMod.MOD_ID)
public class ParalysisHandler {

    @SubscribeEvent
    public static void onScroll(InputEvent.MouseScrollEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (hasParalysisEffect(player)) {
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                event.setCanceled(true); // Cancel the mouse scroll event
            }
        }
    }
    @SubscribeEvent
    public static void onClickInput(InputEvent.ClickInputEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && hasParalysisEffect(player) && event.isPickBlock()) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
           event.setCanceled(true);
        }
        }
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && hasParalysisEffect(player)) {
            if (Minecraft.getInstance().options.keyHotbarSlots[0].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[0].setDown(false);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
        }
            if (Minecraft.getInstance().options.keyHotbarSlots[1].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[1].setDown(false);
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
    }
            if (Minecraft.getInstance().options.keyHotbarSlots[2].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[2].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[3].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[3].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[4].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[4].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[5].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[5].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[6].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[6].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[7].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[7].setDown(false);
            }
            if (Minecraft.getInstance().options.keyHotbarSlots[8].consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyHotbarSlots[8].setDown(false);
            }
            if (Minecraft.getInstance().options.keySwapOffhand.consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keySwapOffhand.setDown(false);
            }
            if (Minecraft.getInstance().options.keyDrop.consumeClick()){
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                Minecraft.getInstance().options.keyDrop.setDown(false);
            }
        }
    }
    @SubscribeEvent
    public static void onMouseClickInInventory(InputEvent.RawMouseEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (hasParalysisEffect(player) && Minecraft.getInstance().screen instanceof AbstractContainerScreen) {
                if(event.getAction()==GLFW.GLFW_PRESS) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                event.setCanceled(true); // Cancel the mouse scroll event
            }
        }
    }
    @SubscribeEvent
    public static void onInputInInventory(InputEvent.KeyInputEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (hasParalysisEffect(player) && (Minecraft.getInstance().screen instanceof ContainerScreen || Minecraft.getInstance().screen instanceof InventoryScreen)
                    && (Minecraft.getInstance().options.keyDrop.getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyPickItem.getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keySwapOffhand.getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[0].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[1].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[2].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[3].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[4].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[5].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[6].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[7].getKey().getValue() == event.getKey()
                    || Minecraft.getInstance().options.keyHotbarSlots[8].getKey().getValue() == event.getKey())) {
                
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.PARALISED, 1.0F, 0.1F));
                //event.isCanceled();
            }
        }
    }
    private static boolean hasParalysisEffect(Player player) {
        for (MobEffectInstance effect : player.getActiveEffects()) {
            MobEffect mobEffect = effect.getEffect();
            if (mobEffect == ModEffects.PARALYSIS.get()) {
                return true;
            }
        }
        return false;
    }

}