package net.dainplay.rpgworldmod.entity.client.render;

        import com.google.common.collect.ImmutableMap;
        import com.mojang.datafixers.util.Pair;

        import java.util.Map;
        import java.util.stream.Stream;

        import net.dainplay.rpgworldmod.RPGworldMod;
        import net.dainplay.rpgworldmod.entity.custom.ModBoat;
        import net.minecraft.client.model.BoatModel;
        import net.minecraft.client.model.geom.ModelLayerLocation;
        import net.minecraft.client.model.geom.ModelPart;
        import net.minecraft.client.renderer.entity.BoatRenderer;
        import net.minecraft.client.renderer.entity.EntityRendererProvider;
        import net.minecraft.resources.ResourceLocation;
        import net.minecraft.world.entity.vehicle.Boat;
        import net.minecraftforge.api.distmarker.Dist;
        import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModBoatRenderer extends BoatRenderer {
    private final Map<ModBoat.Type, Pair<ResourceLocation, BoatModel>> boatResources;

    public ModBoatRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(ModBoat.Type.values()).collect(ImmutableMap.toImmutableMap((key) -> key, (model) -> Pair.of(new ResourceLocation(RPGworldMod.MOD_ID, getTextureLocation(model)), createBoatModel(context, model))));
    }

    private static String getTextureLocation(ModBoat.Type model)
    {
        return "textures/entity/boat/" + model.getName() + ".png";
    }

    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) { return this.boatResources.get(((ModBoat)boat).getModBoatType());}

        private static ModelLayerLocation createLocation(String name, String layer)
    {
        return new ModelLayerLocation(new ResourceLocation(RPGworldMod.MOD_ID, name), layer);
    }

    public static ModelLayerLocation createBoatModelName(ModBoat.Type model)
    {
        return createLocation("boat/" + model.getName(), "main");
    }

    private BoatModel createBoatModel(EntityRendererProvider.Context context, ModBoat.Type model)
    {
        ModelLayerLocation modellayerlocation = createBoatModelName(model);
        ModelPart baked = context.bakeLayer(modellayerlocation);
        return new BoatModel(baked);
    }
}
