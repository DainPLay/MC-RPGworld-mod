package net.dainplay.rpgworldmod.entity.client.render;

import net.dainplay.rpgworldmod.entity.client.model.ScarfModel;
import net.dainplay.rpgworldmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CurioRenderers {

    public static void register() {
        CuriosRendererRegistry.register(ModItems.BRAMBLEFOX_SCARF.get(), () -> new CurioRenderer("scarf/bramblefox_scarf", new ScarfModel(bakeLayer(CurioLayers.SCARF), RenderType::entityCutoutNoCull)));
        }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }
}
