package net.tropicraft.core.client.entity.renderers;
/*
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.hostile.TropiSpiderEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TropiSpiderRenderer extends SpiderEntityRenderer<TropiSpiderEntity> {
	public TropiSpiderRenderer(final EntityRenderDispatcher manager) {
		super(manager);
		shadowOpacity = 0.5f;
	}

	@Override
	public void render(TropiSpiderEntity spider, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
		stack.push();
		final float scale = getScale(spider);
		shadowRadius = scale;
		stack.scale(scale, scale, scale);
		super.render(spider, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		stack.pop();
	}

	@Override
	public Identifier getTexture(TropiSpiderEntity entity) {
		if (entity.getSpiderType() == TropiSpiderEntity.Type.CHILD) {
			return TropicraftRenderUtils.bindTextureEntity("spiderchild");
		}
		if (entity.getSpiderType() == TropiSpiderEntity.Type.MOTHER) {
			return TropicraftRenderUtils.bindTextureEntity("spidermother");
		}
		return TropicraftRenderUtils.bindTextureEntity("spideradult");
	}

	private float getScale(final TropiSpiderEntity spider) {
		float scale = 1.0f;
		if (spider.getSpiderType() == TropiSpiderEntity.Type.CHILD) {
			scale = 0.5f;
		}
		if (spider.getSpiderType() == TropiSpiderEntity.Type.MOTHER) {
			scale = 1.2f;
		}
		return scale;
	}
}

 */