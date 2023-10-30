package net.dainplay.rpgworldmod.client.render;

import net.dainplay.rpgworldmod.RPGworldMod;
import net.dainplay.rpgworldmod.world.entity.FairapierSeedEntity;
import net.dainplay.rpgworldmod.world.entity.ProjectruffleArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FairapierSeedRenderer extends ArrowRenderer<FairapierSeedEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RPGworldMod.MOD_ID, "textures/entity/fairapier_seed.png");

    public FairapierSeedRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public ResourceLocation getTextureLocation(FairapierSeedEntity arrow) {
        return TEXTURE;
    }
}