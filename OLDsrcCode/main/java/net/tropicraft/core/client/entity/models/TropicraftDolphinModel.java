package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.underdasea.TropicraftDolphinEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class TropicraftDolphinModel extends CompositeEntityModel<TropicraftDolphinEntity> {
	ModelPart body1;
	ModelPart body2;
	ModelPart head1;
	ModelPart lowerJaw1;
	ModelPart lowerJaw2;
	ModelPart lowerJaw3;
	ModelPart lowerJaw4;
	ModelPart lowerJaw5;
	ModelPart upperJaw1;
	ModelPart upperJaw2;
	ModelPart upperJaw3;
	ModelPart upperJaw4;
	ModelPart upperJaw5;
	ModelPart head2;
	ModelPart head3;
	ModelPart head4;
	ModelPart head5;
	ModelPart body3;
	ModelPart rightPectoralFin1;
	ModelPart rightPectoralFin2;
	ModelPart rightPectoralFin3;
	ModelPart leftPectoralFin1;
	ModelPart leftPectoralFin2;
	ModelPart leftPectoralFin3;
	ModelPart tail1;
	ModelPart tail2;
	ModelPart tail3;
	ModelPart tail4;
	ModelPart fluke1;
	ModelPart fluke2;
	ModelPart fluke3;
	ModelPart fluke4;
	ModelPart fluke5;
	ModelPart fluke6;
	ModelPart fluke7;
	ModelPart fluke8;
	ModelPart dorsalFin1;
	ModelPart dorsalFin2;
	ModelPart dorsalFin3;
	ModelPart dorsalFin4;
	ModelPart dorsalFin5;

	public float tailVertSpeed = 1f;
	public float tailHorzSpeed = 1f;

	public TropicraftDolphinModel(ModelPart root) {

		body1 = root.getChild("body1");
		body2 = root.getChild("body2");
		head1 = root.getChild("head1");
		lowerJaw1 = root.getChild("lowerJaw1");
		lowerJaw2 = root.getChild("lowerJaw2");
		lowerJaw3 = root.getChild("lowerJaw3");
		lowerJaw4 = root.getChild("lowerJaw4");
		lowerJaw5 = root.getChild("lowerJaw5");
		upperJaw1 = root.getChild("upperJaw1");
		upperJaw2 = root.getChild("upperJaw2");
		upperJaw3 = root.getChild("upperJaw3");
		upperJaw4 = root.getChild("upperJaw4");
		upperJaw5 = root.getChild("upperJaw5");
		head2 = root.getChild("head2");
		head3 = root.getChild("head3");
		head4 = root.getChild("head4");
		head5 = root.getChild("head5");
		body3 = root.getChild("body3");
		rightPectoralFin1 = root.getChild("rightPectoralFin1");
		rightPectoralFin2 = root.getChild("rightPectoralFin2");
		rightPectoralFin3 = root.getChild("rightPectoralFin3");
		leftPectoralFin1 = root.getChild("leftPectoralFin1");
		leftPectoralFin2 = root.getChild("leftPectoralFin2");
		leftPectoralFin3 = root.getChild("leftPectoralFin3");
		tail1 = root.getChild("tail1");
		tail2 = root.getChild("tail2");
		tail3 = root.getChild("tail3");
		tail4 = root.getChild("tail4");
		fluke1 = root.getChild("fluke1");
		fluke2 = root.getChild("fluke2");
		fluke3 = root.getChild("fluke3");
		fluke4 = root.getChild("fluke4");
		fluke5 = root.getChild("fluke5");
		fluke6 = root.getChild("fluke6");
		fluke7 = root.getChild("fluke7");
		fluke8 = root.getChild("fluke8");
		dorsalFin1 = root.getChild("dorsalFin1");
		dorsalFin2 = root.getChild("dorsalFin2");
		dorsalFin3 = root.getChild("dorsalFin3");
		dorsalFin4 = root.getChild("dorsalFin4");
		dorsalFin5 = root.getChild("dorsalFin5");


		/*
		body1 = new ModelPart(this, 0, 0);
		body1.setTextureSize(64, 64);
		body1.addCuboid(-3F, -3F, -3F, 6, 6, 8);
		body1.setPivot(0F, 20F, 0F);

		body2 = new ModelPart(this, 0, 14);
		body2.setTextureSize(64, 64);
		body2.addCuboid(-3F, -2F, -5F, 6, 5, 4);
		body2.setPivot(0F, 19.8F, -2F);

		head1 = new ModelPart(this, 0, 57);
		head1.setTextureSize(64, 64);
		head1.addCuboid(-2.5F, -3F, -3F, 5, 4, 3);
		head1.setPivot(0F, 21.4F, -6.3F);

		lowerJaw1 = new ModelPart(this, 16, 61);
		lowerJaw1.setTextureSize(64, 64);
		lowerJaw1.addCuboid(-2.5F, 0F, -1F, 5, 2, 1);
		lowerJaw1.setPivot(0F, 20.4F, -9.3F);

		lowerJaw2 = new ModelPart(this, 29, 60);
		lowerJaw2.setTextureSize(64, 64);
		lowerJaw2.addCuboid(-2F, 0F, -3F, 4, 1, 3);
		lowerJaw2.setPivot(0F, 21.4F, -10.3F);

		lowerJaw3 = new ModelPart(this, 29, 54);
		lowerJaw3.setTextureSize(64, 64);
		lowerJaw3.addCuboid(-2F, 0F, -3F, 4, 1, 3);
		lowerJaw3.setPivot(0F, 20.4F, -10.3F);

		lowerJaw4 = new ModelPart(this, 44, 61);
		lowerJaw4.setTextureSize(64, 64);
		lowerJaw4.addCuboid(-1.5F, 0F, -2F, 3, 1, 2);
		lowerJaw4.setPivot(0F, 21.4F, -13.3F);

		lowerJaw5 = new ModelPart(this, 45, 56);
		lowerJaw5.setTextureSize(64, 64);
		lowerJaw5.addCuboid(-1.5F, -1F, -1F, 3, 1, 1);
		lowerJaw5.setPivot(0F, 22.4F, -15.3F);

		upperJaw1 = new ModelPart(this, 52, 0);
		upperJaw1.setTextureSize(64, 64);
		upperJaw1.addCuboid(-2.5F, 0F, -1F, 5, 1, 1);
		upperJaw1.setPivot(0F, 19.4F, -9.3F);

		upperJaw2 = new ModelPart(this, 50, 3);
		upperJaw2.setTextureSize(64, 64);
		upperJaw2.addCuboid(-2F, 0F, -3F, 4, 1, 3);
		upperJaw2.setPivot(0F, 19.4F, -10.3F);

		upperJaw3 = new ModelPart(this, 54, 8);
		upperJaw3.setTextureSize(64, 64);
		upperJaw3.addCuboid(-1.5F, -1F, -2F, 3, 1, 2);
		upperJaw3.setPivot(0F, 21.36575F, -12.77706F);

		upperJaw4 = new ModelPart(this, 58, 12);
		upperJaw4.setTextureSize(64, 64);
		upperJaw4.addCuboid(-1F, -1F, -1F, 2, 1, 1);
		upperJaw4.setPivot(0F, 21.36575F, -14.77706F);

		upperJaw5 = new ModelPart(this, 52, 15);
		upperJaw5.setTextureSize(64, 64);
		upperJaw5.addCuboid(-1F, 0F, -4F, 2, 1, 4);
		upperJaw5.setPivot(0F, 19.74202F, -11.23969F);

		head2 = new ModelPart(this, 0, 49);
		head2.setTextureSize(64, 64);
		head2.addCuboid(-2F, -1F, -4F, 4, 2, 4);
		head2.setPivot(0F, 18.4F, -6.3F);

		head3 = new ModelPart(this, 14, 49);
		head3.setTextureSize(64, 64);
		head3.addCuboid(-1.5F, 0F, -1F, 3, 2, 1);
		head3.setPivot(0F, 17.99005F, -10.40267F);

		head4 = new ModelPart(this, 24, 49);
		head4.setTextureSize(64, 64);
		head4.addCuboid(-1.5F, 0F, -1F, 3, 2, 1);
		head4.setPivot(0F, 18.43765F, -11.29691F);

		head5 = new ModelPart(this, 34, 49);
		head5.setTextureSize(64, 64);
		head5.addCuboid(-1.5F, 0F, -1F, 3, 1, 1);
		head5.setPivot(0F, 19.10989F, -12.03724F);

		body3 = new ModelPart(this, 20, 14);
		body3.setTextureSize(64, 64);
		body3.addCuboid(-2.5F, 0F, -4.3F, 5, 1, 5);
		body3.setPivot(0F, 17.1F, -2.5F);

		rightPectoralFin1 = new ModelPart(this, 0, 37);
		rightPectoralFin1.setTextureSize(64, 64);
		rightPectoralFin1.addCuboid(-3F, 0F, 0F, 3, 1, 3);
		rightPectoralFin1.setPivot(-3F, 21.3F, -5F);

		rightPectoralFin2 = new ModelPart(this, 0, 41);
		rightPectoralFin2.setTextureSize(64, 64);
		rightPectoralFin2.addCuboid(-1F, 0F, 0F, 1, 1, 2);
		rightPectoralFin2.setPivot(-5.104775F, 22.85859F, -3.227792F);

		rightPectoralFin3 = new ModelPart(this, 8, 42);
		rightPectoralFin3.setTextureSize(64, 64);
		rightPectoralFin3.addCuboid(-1F, 0F, 0F, 1, 1, 1);
		rightPectoralFin3.setPivot(-5.521684F, 23.16731F, -1.912163F);

		leftPectoralFin1 = new ModelPart(this, 0, 37);
		leftPectoralFin1.setTextureSize(64, 64);
		leftPectoralFin1.addCuboid(0F, 0F, 0F, 3, 1, 3);
		leftPectoralFin1.setPivot(3F, 21.3F, -5F);

		leftPectoralFin2 = new ModelPart(this, 0, 41);
		leftPectoralFin2.setTextureSize(64, 64);
		leftPectoralFin2.addCuboid(3, -0.1f, 0.5f, 1, 1, 2);
		leftPectoralFin2.setPivot(3F, 21.3F, -5F);

		leftPectoralFin3 = new ModelPart(this, 8, 42);
		leftPectoralFin3.setTextureSize(64, 64);
		leftPectoralFin3.addCuboid(4, -0.15F, 0.5f, 1, 1, 1);
		leftPectoralFin3.setPivot(3F, 21.3F, -5F);

		tail1 = new ModelPart(this, 0, 24);
		tail1.setTextureSize(64, 64);
		tail1.addCuboid(-2.5F, -2.5F, -1F, 5, 5, 7);
		tail1.setPivot(0F, 19.8F, 5.1F);

		tail2 = new ModelPart(this, 24, 27);
		tail2.setTextureSize(64, 64);
		tail2.addCuboid(-2F, -2F, -1F, 4, 4, 5);
		tail2.setPivot(0F, 20.07322F, 11.09378F);
		// Tail1.addChild(Tail2);

		tail3 = new ModelPart(this, 40, 24);
		tail3.setTextureSize(64, 64);
		tail3.addCuboid(-1.5F, -1.5F, -1F, 3, 3, 4);
		tail3.setPivot(0F, 20.8163F, 15.02924F);

		tail4 = new ModelPart(this, 27, 30);
		tail4.setTextureSize(64, 64);
		tail4.addCuboid(-1F, -1F, 0F, 2, 2, 3);
		tail4.setPivot(0F, 21.49112F, 17.43644F);

		fluke1 = new ModelPart(this, 44, 34);
		fluke1.setTextureSize(64, 64);
		fluke1.addCuboid(-3F, 0F, 0F, 6, 1, 1);
		fluke1.setPivot(0F, 22.1683F, 19.21166F);

		fluke2 = new ModelPart(this, 43, 38);
		fluke2.setTextureSize(64, 64);
		fluke2.addCuboid(-4.5F, 0F, 0F, 9, 1, 1);
		fluke2.setPivot(0F, 22.25945F, 20.2075F);

		fluke3 = new ModelPart(this, 30, 38);
		fluke3.setTextureSize(64, 64);
		fluke3.addCuboid(-5F, 0F, -1F, 5, 1, 1);
		fluke3.setPivot(4.9F, 22.44176F, 22.19917F);

		fluke4 = new ModelPart(this, 14, 38);
		fluke4.setTextureSize(64, 64);
		fluke4.addCuboid(-5F, 0F, 0F, 6, 1, 1);
		fluke4.setPivot(4.9F, 22.44176F, 22.19917F);

		fluke5 = new ModelPart(this, 30, 38);
		fluke5.setTextureSize(64, 64);
		fluke5.addCuboid(0F, 0F, -1F, 5, 1, 1);
		fluke5.setPivot(-4.9F, 22.44176F, 22.19917F);

		fluke6 = new ModelPart(this, 14, 38);
		fluke6.setTextureSize(64, 64);
		fluke6.addCuboid(-1F, 0F, 0F, 6, 1, 1);
		fluke6.setPivot(-4.9F, 22.44176F, 22.19917F);

		fluke7 = new ModelPart(this, 55, 30);
		fluke7.setTextureSize(64, 64);
		fluke7.addCuboid(-3F, 0F, 0F, 3, 1, 1);
		fluke7.setPivot(0F, 22.43265F, 22.09959F);

		fluke8 = new ModelPart(this, 55, 30);
		fluke8.setTextureSize(64, 64);
		fluke8.addCuboid(0F, 0F, 0F, 3, 1, 1);
		fluke8.setPivot(0F, 22.43265F, 22.09959F);

		dorsalFin1 = new ModelPart(this, 21, 0);
		dorsalFin1.setTextureSize(64, 64);
		dorsalFin1.addCuboid(-0.5F, -1F, -0.7F, 1, 1, 5);
		dorsalFin1.setPivot(0F, 17.1F, 0F);

		dorsalFin2 = new ModelPart(this, 35, 0);
		dorsalFin2.setTextureSize(64, 64);
		dorsalFin2.addCuboid(-0.5F, -1F, 0F, 1, 1, 4);
		dorsalFin2.setPivot(0F, 16.10415F, 0.09098025F);

		dorsalFin3 = new ModelPart(this, 30, 7);
		dorsalFin3.setTextureSize(64, 64);
		dorsalFin3.addCuboid(-0.5F, -1F, 0F, 1, 1, 3);
		dorsalFin3.setPivot(0F, 15.30191F, 1.255631F);

		dorsalFin4 = new ModelPart(this, 39, 7);
		dorsalFin4.setTextureSize(64, 64);
		dorsalFin4.addCuboid(-0.5F, -1F, 0F, 1, 1, 2);
		dorsalFin4.setPivot(0F, 14.60895F, 2.48844F);

		dorsalFin5 = new ModelPart(this, 45, 0);
		dorsalFin5.setTextureSize(64, 64);
		dorsalFin5.addCuboid(-0.5F, -1F, 0F, 1, 1, 1);
		dorsalFin5.setPivot(0F, 14.15063F, 3.826327F);
		*/
	}

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("body1",
				ModelPartBuilder.create().uv(0, 0)
						.cuboid(-3F, -3F, -3F, 6, 6, 8),
				ModelTransform.pivot(0F, 20F, 0F));

		modelPartData.addChild("body2",
				ModelPartBuilder.create().uv(0, 14)
						.cuboid(-3F, -2F, -5F, 6, 5, 4),
				ModelTransform.pivot(0F, 19.8F, -2F));

		modelPartData.addChild("head1",
				ModelPartBuilder.create().uv(0, 57)
						.cuboid(-2.5F, -3F, -3F, 5, 4, 3),
				ModelTransform.pivot(0F, 21.4F, -6.3F));

		modelPartData.addChild("lowerJaw1",
				ModelPartBuilder.create().uv(16, 61)
						.cuboid(-2.5F, 0F, -1F, 5, 2, 1),
				ModelTransform.pivot(0F, 20.4F, -9.3F));

		modelPartData.addChild("lowerJaw2",
				ModelPartBuilder.create().uv(29, 60)
						.cuboid(-2F, 0F, -3F, 4, 1, 3),
				ModelTransform.pivot(0F, 21.4F, -10.3F));

		modelPartData.addChild("lowerJaw3",
				ModelPartBuilder.create().uv(29, 54)
						.cuboid(-2F, 0F, -3F, 4, 1, 3),
				ModelTransform.pivot(0F, 20.4F, -10.3F));

		modelPartData.addChild("lowerJaw4",
				ModelPartBuilder.create().uv(44, 61)
						.cuboid(-1.5F, 0F, -2F, 3, 1, 2),
				ModelTransform.pivot(0F, 21.4F, -13.3F));

		modelPartData.addChild("lowerJaw5",
				ModelPartBuilder.create().uv(45, 56)
						.cuboid(-1.5F, -1F, -1F, 3, 1, 1),
				ModelTransform.pivot(0F, 22.4F, -15.3F));

		modelPartData.addChild("upperJaw1",
				ModelPartBuilder.create().uv(52, 0)
						.cuboid(-2.5F, 0F, -1F, 5, 1, 1),
				ModelTransform.pivot(0F, 19.4F, -9.3F));

		modelPartData.addChild("upperJaw2",
				ModelPartBuilder.create().uv(50, 3)
						.cuboid(-2F, 0F, -3F, 4, 1, 3),
				ModelTransform.pivot(0F, 19.4F, -10.3F));

		modelPartData.addChild("upperJaw3",
				ModelPartBuilder.create().uv(54, 8)
						.cuboid(-1.5F, -1F, -2F, 3, 1, 2),
				ModelTransform.pivot(0F, 21.36575F, -12.77706F));

		modelPartData.addChild("upperJaw4",
				ModelPartBuilder.create().uv(58, 12)
						.cuboid(-1F, -1F, -1F, 2, 1, 1),
				ModelTransform.pivot(0F, 21.36575F, -14.77706F));

		modelPartData.addChild("upperJaw5",
				ModelPartBuilder.create().uv(52, 15)
						.cuboid(-1F, 0F, -4F, 2, 1, 4),
				ModelTransform.pivot(0F, 19.74202F, -11.23969F));

		modelPartData.addChild("head2",
				ModelPartBuilder.create().uv(0, 49)
						.cuboid(-2F, -1F, -4F, 4, 2, 4),
				ModelTransform.pivot(0F, 18.4F, -6.3F));

		modelPartData.addChild("head3",
				ModelPartBuilder.create().uv(14, 49)
						.cuboid(-1.5F, 0F, -1F, 3, 2, 1),
				ModelTransform.pivot(0F, 17.99005F, -10.40267F));

		modelPartData.addChild("head4",
				ModelPartBuilder.create().uv(24, 49)
						.cuboid(-1.5F, 0F, -1F, 3, 2, 1),
				ModelTransform.pivot(0F, 18.43765F, -11.29691F));

		modelPartData.addChild("head5",
				ModelPartBuilder.create().uv(34, 49)
						.cuboid(-1.5F, 0F, -1F, 3, 1, 1),
				ModelTransform.pivot(0F, 19.10989F, -12.03724F));

		modelPartData.addChild("body3",
				ModelPartBuilder.create().uv(20, 14)
						.cuboid(-2.5F, 0F, -4.3F, 5, 1, 5),
				ModelTransform.pivot(0F, 17.1F, -2.5F));

		modelPartData.addChild("rightPectoralFin1",
				ModelPartBuilder.create().uv(0, 37)
						.cuboid(-3F, 0F, 0F, 3, 1, 3),
				ModelTransform.pivot(-3F, 21.3F, -5F));

		modelPartData.addChild("rightPectoralFin2",
				ModelPartBuilder.create().uv(0, 41)
						.cuboid(-1F, 0F, 0F, 1, 1, 2),
				ModelTransform.pivot(-5.104775F, 22.85859F, -3.227792F));

		modelPartData.addChild("rightPectoralFin3",
				ModelPartBuilder.create().uv(8, 42)
						.cuboid(-1F, 0F, 0F, 1, 1, 1),
				ModelTransform.pivot(-5.521684F, 23.16731F, -1.912163F));

		modelPartData.addChild("leftPectoralFin1",
				ModelPartBuilder.create().uv(0, 37)
						.cuboid(0F, 0F, 0F, 3, 1, 3),
				ModelTransform.pivot(3F, 21.3F, -5F));

		modelPartData.addChild("leftPectoralFin2",
				ModelPartBuilder.create().uv(0, 41)
						.cuboid(3, -0.1f, 0.5f, 1, 1, 2),
				ModelTransform.pivot(3F, 21.3F, -5F));

		modelPartData.addChild("leftPectoralFin3",
				ModelPartBuilder.create().uv(8, 42)
						.cuboid(4, -0.15F, 0.5f, 1, 1, 1),
				ModelTransform.pivot(3F, 21.3F, -5F));

		modelPartData.addChild("tail1",
				ModelPartBuilder.create().uv(0, 24)
						.cuboid(-2.5F, -2.5F, -1F, 5, 5, 7),
				ModelTransform.pivot(0F, 19.8F, 5.1F));

		modelPartData.addChild("tail2",
				ModelPartBuilder.create().uv(24, 27)
						.cuboid(-2F, -2F, -1F, 4, 4, 5),
				ModelTransform.pivot(0F, 20.07322F, 11.09378F));

		modelPartData.addChild("tail3",
				ModelPartBuilder.create().uv(40, 24)
						.cuboid(-1.5F, -1.5F, -1F, 3, 3, 4),
				ModelTransform.pivot(0F, 20.8163F, 15.02924F));

		modelPartData.addChild("tail4",
				ModelPartBuilder.create().uv(27, 30)
						.cuboid(-1F, -1F, 0F, 2, 2, 3),
				ModelTransform.pivot(0F, 21.49112F, 17.43644F));

		modelPartData.addChild("fluke1",
				ModelPartBuilder.create().uv(44, 34)
						.cuboid(-3F, 0F, 0F, 6, 1, 1),
				ModelTransform.pivot(0F, 22.1683F, 19.21166F));

		modelPartData.addChild("fluke2",
				ModelPartBuilder.create().uv(43, 38)
						.cuboid(-4.5F, 0F, 0F, 9, 1, 1),
				ModelTransform.pivot(0F, 22.25945F, 20.2075F));

		modelPartData.addChild("fluke3",
				ModelPartBuilder.create().uv(30, 38)
						.cuboid(-5F, 0F, -1F, 5, 1, 1),
				ModelTransform.pivot(4.9F, 22.44176F, 22.19917F));

		modelPartData.addChild("fluke4",
				ModelPartBuilder.create().uv(14, 38)
						.cuboid(-5F, 0F, 0F, 6, 1, 1),
				ModelTransform.pivot(4.9F, 22.44176F, 22.19917F));

		modelPartData.addChild("fluke5",
				ModelPartBuilder.create().uv(30, 38)
						.cuboid(0F, 0F, -1F, 5, 1, 1),
				ModelTransform.pivot(-4.9F, 22.44176F, 22.19917F));

		modelPartData.addChild("fluke6",
				ModelPartBuilder.create().uv(14, 38)
						.cuboid(-1F, 0F, 0F, 6, 1, 1),
				ModelTransform.pivot(-4.9F, 22.44176F, 22.19917F));

		modelPartData.addChild("fluke7",
				ModelPartBuilder.create().uv(55, 30)
						.cuboid(-3F, 0F, 0F, 3, 1, 1),
				ModelTransform.pivot(0F, 22.43265F, 22.09959F));

		modelPartData.addChild("fluke8",
				ModelPartBuilder.create().uv(55, 30)
						.cuboid(0F, 0F, 0F, 3, 1, 1),
				ModelTransform.pivot(0F, 22.43265F, 22.09959F));

		modelPartData.addChild("dorsalFin1",
				ModelPartBuilder.create().uv(21, 0)
						.cuboid(-0.5F, -1F, -0.7F, 1, 1, 5),
				ModelTransform.pivot(0F, 17.1F, 0F));

		modelPartData.addChild("dorsalFin2",
				ModelPartBuilder.create().uv(35, 0)
						.cuboid(-0.5F, -1F, 0F, 1, 1, 4),
				ModelTransform.pivot(0F, 16.10415F, 0.09098025F));

		modelPartData.addChild("dorsalFin3",
				ModelPartBuilder.create().uv(30, 7)
						.cuboid(-0.5F, -1F, 0F, 1, 1, 3),
				ModelTransform.pivot(0F, 15.30191F, 1.255631F));

		modelPartData.addChild("dorsalFin4",
				ModelPartBuilder.create().uv(39, 7)
						.cuboid(-0.5F, -1F, 0F, 1, 1, 2),
				ModelTransform.pivot(0F, 14.60895F, 2.48844F));

		modelPartData.addChild("dorsalFin5",
				ModelPartBuilder.create().uv(45, 0)
						.cuboid(-0.5F, -1F, 0F, 1, 1, 1),
				ModelTransform.pivot(0F, 14.15063F, 3.826327F));

        return TexturedModelData.of(modelData, 64, 64);
    }

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(
			body1, body2, head1, lowerJaw1, lowerJaw2, lowerJaw3,
			lowerJaw4, lowerJaw5, upperJaw1, upperJaw2, upperJaw3,
			upperJaw4, upperJaw5, head2, head3, head4, head5,
			body3, rightPectoralFin1, rightPectoralFin2, rightPectoralFin3,
			leftPectoralFin1, leftPectoralFin2, leftPectoralFin3,
			tail1, tail2, tail3, tail4, fluke1, fluke2, fluke3,
			fluke4, fluke5, fluke6, fluke7, fluke8, dorsalFin1,
			dorsalFin2, dorsalFin3, dorsalFin4, dorsalFin5
		);
	}

	@Override
	public void setAngles(TropicraftDolphinEntity dolphin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final boolean mouthOpen = dolphin.getMouthOpen();

		if (dolphin.isTouchingWater()) {
			tailVertSpeed = 0.5f / 2;
			tailHorzSpeed = 0.25f / 2;
			if (dolphin.getAir() <= 0) {
				tailVertSpeed = 0.5f;
				tailHorzSpeed = 0.25f;
			}
		} else {
			if (dolphin.isOnGround()) {
				tailVertSpeed = 0.0f;
				tailHorzSpeed = 0.05f;
			} else {
				tailVertSpeed = 1f;
				tailHorzSpeed = 0.5f;
			}
		}

		lowerJaw3.pitch = 0.3490658F;
		if (mouthOpen) {
			lowerJaw5.setPivot(0F, 23.4F, -15.3F+0.52f);
			lowerJaw4.pitch = 0.5F;
		} else {
			lowerJaw5.setPivot(0F, 22.4F, -15.3F);
			lowerJaw4.pitch = 0F;
		}

		lowerJaw5.pitch = -0.2275909F;
		upperJaw2.pitch = 0.3490658F;
		upperJaw4.pitch = -0.09110618F;
		upperJaw5.pitch = 0.15132F;
		head2.pitch = 0.1453859F;
		head3.pitch = 0.4640831F;
		head4.pitch = 0.737227F;
		head5.pitch = 1.055924F;
		body3.pitch = 0.04555309F;

		rightPectoralFin1.pitch = 0.1612329F;
		rightPectoralFin1.yaw = 0.2214468F;
		rightPectoralFin1.roll = -0.6194302F+ (float) (Math.sin(ageInTicks * .025F)) * .3f;

		rightPectoralFin2.pitch = 0.2393862F;
		rightPectoralFin2.yaw = 0.3358756F;
		rightPectoralFin2.roll = -0.5966207F+ (float) (Math.sin(ageInTicks * .025F)) * .45f;

		rightPectoralFin3.pitch = 0.3620028F;
		rightPectoralFin3.yaw = 0.5368112F;
		rightPectoralFin3.roll = -0.5368112F+ (float) (Math.sin(ageInTicks * .025F)) * .5f;

		leftPectoralFin1.pitch = 0.1612329F;
		leftPectoralFin1.yaw = -0.2214468F;
		leftPectoralFin1.roll = 0.6194302F + (float) (Math.sin(ageInTicks * .025F)) * .3f;

		leftPectoralFin2.pitch = 0.2393862F;
		leftPectoralFin2.yaw = -0.3358756F;
		leftPectoralFin2.roll = 0.5966207F + (float) (Math.sin(ageInTicks * .025F)) * .35f;

		leftPectoralFin3.pitch = 0.3620028F;
		leftPectoralFin3.yaw = -0.5368112F;
		leftPectoralFin3.roll = 0.5368112F + (float) (Math.sin(ageInTicks * .025F)) * .4f;

		tail1.pitch = -0.04555309F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .1f;
		tail1.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .135F;
		tail1.roll = 0F;

		tail2.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 1;

		tail2.pivotY = 20 - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 0.8F;

		tail2.pitch = -0.1366593F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .1f;
		tail2.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .135F;
		tail2.roll = 0F;

		tail3.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 1.85f;

		tail3.pivotY = 20.5f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 1.5F;

		tail3.pitch = -0.2733185F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .2f;
		tail3.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .135F;
		tail3.roll = 0F;

		tail4.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.4f;
		tail4.pivotY = 21.5f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 2.5F;

		tail4.pitch = -0.3644247F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .5f;
		tail4.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		tail4.roll = 0F;

		fluke1.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke1.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke1.pitch = -0.09128072F;
		fluke1.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke1.roll = 0F;

		fluke2.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke2.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;

		fluke2.pitch = -0.09128071F;
		fluke2.yaw = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke2.roll = 0F;

		fluke3.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke3.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke3.pitch = -0.09118575F;
		fluke3.yaw = -0.04574326F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke3.roll = 0.00416824F;

		fluke4.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke4.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;

		fluke4.pitch = -0.08892051F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .8f;

		fluke4.yaw = -0.2285096F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke4.roll = 0.02065023F;

		fluke5.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke5.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke5.pitch = -0.09118575F;
		fluke5.yaw = 0.04574326F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke5.roll = -0.00416824F;

		fluke6.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke6.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke6.pitch = -0.08892051F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .8f;
		fluke6.yaw = 0.2285096F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke6.roll = -0.02065023F;

		fluke7.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke7.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke7.pitch = -0.09042732F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .8f;
		fluke7.yaw = -0.1372235F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke7.roll = 0.01246957F;

		fluke8.pivotX = (float) (Math.sin(ageInTicks * tailHorzSpeed)) * 2.8f;
		fluke8.pivotY = 22f - (float) (Math.sin(ageInTicks * tailVertSpeed)) * 4F;

		fluke8.pitch = -0.09042732F + (float) (Math.sin(ageInTicks * tailVertSpeed)) * .8f;

		fluke8.yaw = 0.1372235F + (float) (Math.sin(ageInTicks * tailHorzSpeed)) * .35F;
		fluke8.roll = -0.01246957F;

		dorsalFin1.pitch = -0.09110619F;
		dorsalFin2.pitch = -0.1822124F;
		dorsalFin3.pitch = -0.2733186F;
		dorsalFin4.pitch = -0.4553564F;
		dorsalFin5.pitch = -0.7285004F;
	}
}


