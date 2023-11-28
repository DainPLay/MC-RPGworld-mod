// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class mintobat<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mintobat"), "main");
	private final ModelPart head;

	public mintobat(ModelPart root) {
		this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 0).mirror().addBox(2.0F, -7.0F, -4.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 38).addBox(-6.0F, -1.0F, -3.0F, 12.0F, 16.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -7.0F, -4.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition headwear = head.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -21.0F, -3.0F, 12.0F, 16.0F, 10.0F, new CubeDeformation(-0.5F))
		.texOffs(0, 26).addBox(-3.0F, -17.0F, 0.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(2.25F)), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition right_wing = head.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(42, 29).addBox(-16.0F, 0.0F, 1.5F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition outer_right_wing = right_wing.addOrReplaceChild("outer_right_wing", CubeListBuilder.create().texOffs(46, 51).addBox(-12.0F, 0.0F, 0.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 1.0F, 1.5F));

		PartDefinition left_wing = head.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(42, 29).mirror().addBox(6.0F, 0.0F, 1.5F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition outer_left_wing = left_wing.addOrReplaceChild("outer_left_wing", CubeListBuilder.create().texOffs(46, 51).mirror().addBox(4.0F, 0.0F, 0.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, 1.0F, 1.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}