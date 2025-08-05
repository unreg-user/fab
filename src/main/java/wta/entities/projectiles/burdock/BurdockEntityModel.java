package wta.entities.projectiles.burdock;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BurdockEntityModel extends EntityModel<BurdockEntity> {
	private final ModelPart axis0;
	private final ModelPart down_side0;
	private final ModelPart up_side0;
	private final ModelPart axis1;
	private final ModelPart down_side1;
	private final ModelPart up_side1;
	private final ModelPart axis2;
	private final ModelPart down_side2;
	private final ModelPart up_side2;
	private final ModelPart center;
	public BurdockEntityModel(ModelPart root) {
		this.axis0 = root.getChild("axis0");
		this.down_side0 = this.axis0.getChild("down_side0");
		this.up_side0 = this.axis0.getChild("up_side0");
		this.axis1 = root.getChild("axis1");
		this.down_side1 = this.axis1.getChild("down_side1");
		this.up_side1 = this.axis1.getChild("up_side1");
		this.axis2 = root.getChild("axis2");
		this.down_side2 = this.axis2.getChild("down_side2");
		this.up_side2 = this.axis2.getChild("up_side2");
		this.center = root.getChild("center");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData axis0 = modelPartData.addChild("axis0", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData down_side0 = axis0.addChild("down_side0", ModelPartBuilder.create(), ModelTransform.of(1.5F, 2.0F, -1.5F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r1 = down_side0.addChild("cube_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r2 = down_side0.addChild("cube_r2", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r3 = down_side0.addChild("cube_r3", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r4 = down_side0.addChild("cube_r4", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r5 = down_side0.addChild("cube_r5", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r6 = down_side0.addChild("cube_r6", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r7 = down_side0.addChild("cube_r7", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r8 = down_side0.addChild("cube_r8", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r9 = down_side0.addChild("cube_r9", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r10 = down_side0.addChild("cube_r10", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r11 = down_side0.addChild("cube_r11", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r12 = down_side0.addChild("cube_r12", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData up_side0 = axis0.addChild("up_side0", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -2.0F, 1.5F));

		ModelPartData cube_r13 = up_side0.addChild("cube_r13", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r14 = up_side0.addChild("cube_r14", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r15 = up_side0.addChild("cube_r15", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r16 = up_side0.addChild("cube_r16", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r17 = up_side0.addChild("cube_r17", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r18 = up_side0.addChild("cube_r18", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r19 = up_side0.addChild("cube_r19", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r20 = up_side0.addChild("cube_r20", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r21 = up_side0.addChild("cube_r21", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r22 = up_side0.addChild("cube_r22", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r23 = up_side0.addChild("cube_r23", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r24 = up_side0.addChild("cube_r24", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData axis1 = modelPartData.addChild("axis1", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData down_side1 = axis1.addChild("down_side1", ModelPartBuilder.create(), ModelTransform.of(1.5F, 2.0F, -1.5F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r25 = down_side1.addChild("cube_r25", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r26 = down_side1.addChild("cube_r26", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r27 = down_side1.addChild("cube_r27", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r28 = down_side1.addChild("cube_r28", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r29 = down_side1.addChild("cube_r29", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r30 = down_side1.addChild("cube_r30", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r31 = down_side1.addChild("cube_r31", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r32 = down_side1.addChild("cube_r32", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r33 = down_side1.addChild("cube_r33", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r34 = down_side1.addChild("cube_r34", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r35 = down_side1.addChild("cube_r35", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r36 = down_side1.addChild("cube_r36", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData up_side1 = axis1.addChild("up_side1", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -2.0F, 1.5F));

		ModelPartData cube_r37 = up_side1.addChild("cube_r37", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r38 = up_side1.addChild("cube_r38", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r39 = up_side1.addChild("cube_r39", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r40 = up_side1.addChild("cube_r40", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r41 = up_side1.addChild("cube_r41", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r42 = up_side1.addChild("cube_r42", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r43 = up_side1.addChild("cube_r43", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r44 = up_side1.addChild("cube_r44", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r45 = up_side1.addChild("cube_r45", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r46 = up_side1.addChild("cube_r46", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r47 = up_side1.addChild("cube_r47", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r48 = up_side1.addChild("cube_r48", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData axis2 = modelPartData.addChild("axis2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData down_side2 = axis2.addChild("down_side2", ModelPartBuilder.create(), ModelTransform.of(1.5F, 2.0F, -1.5F, 3.1416F, 0.0F, 0.0F));

		ModelPartData cube_r49 = down_side2.addChild("cube_r49", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r50 = down_side2.addChild("cube_r50", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r51 = down_side2.addChild("cube_r51", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r52 = down_side2.addChild("cube_r52", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r53 = down_side2.addChild("cube_r53", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r54 = down_side2.addChild("cube_r54", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r55 = down_side2.addChild("cube_r55", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r56 = down_side2.addChild("cube_r56", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r57 = down_side2.addChild("cube_r57", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r58 = down_side2.addChild("cube_r58", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r59 = down_side2.addChild("cube_r59", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r60 = down_side2.addChild("cube_r60", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData up_side2 = axis2.addChild("up_side2", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -2.0F, 1.5F));

		ModelPartData cube_r61 = up_side2.addChild("cube_r61", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, -3.3F, 1.5708F, 1.309F, 1.5708F));

		ModelPartData cube_r62 = up_side2.addChild("cube_r62", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.2F, 0.3F, -1.5708F, 1.309F, -1.5708F));

		ModelPartData cube_r63 = up_side2.addChild("cube_r63", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.3F, 0.0F, -1.5F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r64 = up_side2.addChild("cube_r64", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, -1.5F, 0.0F, 0.0F, 0.2618F));

		ModelPartData cube_r65 = up_side2.addChild("cube_r65", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -2.2F, 0.3345F, 0.4237F, 0.2281F));

		ModelPartData cube_r66 = up_side2.addChild("cube_r66", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -0.9F, -0.2932F, 0.4573F, -0.249F));

		ModelPartData cube_r67 = up_side2.addChild("cube_r67", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, 0.0F, -2.2F, 0.0948F, 0.4779F, -0.2529F));

		ModelPartData cube_r68 = up_side2.addChild("cube_r68", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.8F, 0.0F, -0.9F, -0.0561F, 0.4287F, 0.2326F));

		ModelPartData cube_r69 = up_side2.addChild("cube_r69", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, 0.1744F));

		ModelPartData cube_r70 = up_side2.addChild("cube_r70", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.1F, 0.0F, -3.1F, 0.1745F, 0.0F, 0.1745F));

		ModelPartData cube_r71 = up_side2.addChild("cube_r71", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, 0.1F, -0.1693F, -0.0603F, -0.1747F));

		ModelPartData cube_r72 = up_side2.addChild("cube_r72", ModelPartBuilder.create().uv(0, 8).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.1F, 0.0F, -3.1F, 0.1745F, 0.0F, -0.1745F));

		ModelPartData center = modelPartData.addChild("center", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(BurdockEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		center.render(matrices, vertices, light, overlay);
		axis0.render(matrices, vertices, light, overlay);
		axis1.render(matrices, vertices, light, overlay);
		axis2.render(matrices, vertices, light, overlay);
	}
}