package com.dragonseeker.tropicfabricport.client;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;

public class TropicraftSpecialRenderHelper {

    public void renderMask(MatrixStack stack, VertexConsumer buffer, int maskIndex, int packedLightIn, int overlayLightIn) { //IVertexBuilder
        stack.push();
        float f = ((float) ((maskIndex % 8) * 32) + 0.0F) / 256F;
        float f1 = ((float) ((maskIndex % 8) * 32) + 31.99F) / 256F;
        float f2 = ((float) ((maskIndex / 8) * 32) + 0.0F) / 256F;
        float f3 = ((float) ((maskIndex / 8) * 32) + 31.99F) / 256F;
        float f1shifted = ((float) ((maskIndex / 8) * 32) + 128) / 256F;
        float f3shifted = ((float) ((maskIndex / 8) * 32) + 159.99F) / 256F;
        float f4 = 0.0F;
        float f5 = 0.3F;
        stack.translate(-f4, -f5, 0.0F);
        float f6 = 2F;
        stack.scale(f6, f6, f6);
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180F));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180F));
        stack.translate(-0.5F, -0.5F, 0.0F);
        popper(f1, f2, f, f3, f1shifted, f3shifted, stack, buffer, packedLightIn, overlayLightIn);
        stack.pop();
    }

    public void renderFish(final MatrixStack stack, final VertexConsumer buffer, final int index, final int packedLightIn, final int overlayLightIn) { //IVertexBuilder
        float f = ((float) ((index % 8) * 32) + 0.0F) / 256F;
        float f1 = ((float) ((index % 8) * 32) + 31.99F) / 256F;
        float f2 = ((float) ((index / 8) * 32) + 0.0F) / 256F;
        float f3 = ((float) ((index / 8) * 32) + 31.99F) / 256F;
        float f4 = 0.0F;
        float f5 = 0.3F;
        stack.push();
        stack.translate(-f4, -f5, 0.0F);
        float f6 = 1.7F;
        stack.scale(f6, f6, f6);
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180F));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180F));
        stack.translate(-0.5F, -0.5F, 0.0F);
        popper(f1, f2, f, f3, f2, f3, stack, buffer, packedLightIn, overlayLightIn);
        stack.pop();
    }
    
    public static void vertex(VertexConsumer bufferIn, MatrixStack ms, double x, double y, double z, float red, float green, float blue, float alpha, float texU, float texV, Direction normal, int packedLight, int packedOverlay) { //IVertexBuilder
    	vertex(bufferIn, ms.peek().getModel(), ms.peek().getNormal(), x, y, z, red, green, blue, alpha, texU, texV, normal, packedLight, packedOverlay);
    }

    public static void vertex(VertexConsumer bufferIn, Matrix4f matrixIn, Matrix3f matrixNormalIn, double x, double y, double z, float red, float green, float blue, float alpha, float texU, float texV, Direction normal, int packedLight, int packedOverlay) { //IVertexBuilder
    	Vec3i normalVec = normal.getVector();
        bufferIn.vertex(matrixIn, (float) x, (float) y, (float) z)
                .color(red, green, blue, alpha)
                .texture(texU, texV)
                .overlay(packedOverlay)
                .light(packedLight)
                .normal(matrixNormalIn, normalVec.getX(), normalVec.getY(), normalVec.getZ())
                .next();
                //.endVertex();
    }

    public static void popper(float f, float f1, float f2, float f3, float f1shifted, float f3shifted, float layerHeight, MatrixStack stack, VertexConsumer buffer, int packedLightIn, int overlayLightIn, float red, float green, float blue, float alpha) { //IVertexBuilder
        float f4 = 1.0F;

        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, 0.0D, 0.0D, red, green, blue, alpha, f, f3shifted, Direction.SOUTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, 0.0D, 0.0D, red, green, blue, alpha, f2, f3shifted, Direction.SOUTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, 1.0D, 0.0D, red, green, blue, alpha, f2, f1shifted, Direction.SOUTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, 1.0D, 0.0D, red, green, blue, alpha, f, f1shifted, Direction.SOUTH, packedLightIn, overlayLightIn);

        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, 1.0D, 0.0F - layerHeight, red, green, blue, alpha, f, f1, Direction.NORTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, 1.0D, 0.0F - layerHeight, red, green, blue, alpha, f2, f1, Direction.NORTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, 0.0D, 0.0F - layerHeight, red, green, blue, alpha, f2, f3, Direction.NORTH, packedLightIn, overlayLightIn);
        vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, 0.0D, 0.0F - layerHeight, red, green, blue, alpha, f, f3, Direction.NORTH, packedLightIn, overlayLightIn);

        for (int i = 0; i < 32; i++) {
            float f6 = (float) i / 32F;
            float f10 = (f + (f2 - f) * f6) - 0.001953125F;
            float f14 = f4 * f6;
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f14, 0.0D, 0.0F - layerHeight, red, green, blue, alpha, f10, f3, Direction.EAST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f14, 0.0D, 0.0D, red, green, blue, alpha, f10, f3, Direction.EAST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f14, 1.0D, 0.0D, red, green, blue, alpha, f10, f1, Direction.EAST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f14, 1.0D, 0.0F - layerHeight, red, green, blue, alpha, f10, f1, Direction.EAST, packedLightIn, overlayLightIn);
        }

        for (int j = 0; j < 32; j++) {
            float f7 = (float) j / 32F;
            float f11 = (f + (f2 - f) * f7) - 0.001953125F;
            float f15 = f4 * f7 + 0.03125F;
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f15, 1.0D, 0.0F - layerHeight, red, green, blue, alpha, f11, f1, Direction.WEST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f15, 1.0D, 0.0D, red, green, blue, alpha, f11, f1, Direction.WEST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f15, 0.0D, 0.0D, red, green, blue, alpha, f11, f3, Direction.WEST, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f15, 0.0D, 0.0F - layerHeight, red, green, blue, alpha, f11, f3, Direction.WEST, packedLightIn, overlayLightIn);
        }

        for (int k = 0; k < 32; k++) {
            float f8 = (float) k / 32F;
            float f12 = (f3 + (f1 - f3) * f8) - 0.001953125F;
            float f16 = f4 * f8 + 0.03125F;
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, f16, 0.0D, red, green, blue, alpha, f, f12, Direction.UP, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, f16, 0.0D, red, green, blue, alpha, f2, f12, Direction.UP, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, f16, 0.0F - layerHeight, red, green, blue, alpha, f2, f12, Direction.UP, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, f16, 0.0F - layerHeight, red, green, blue, alpha, f, f12, Direction.UP, packedLightIn, overlayLightIn);
        }

        for (int l = 0; l < 32; l++) {
            float f9 = (float) l / 32F;
            float f13 = (f3 + (f1 - f3) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, f17, 0.0D, red, green, blue, alpha, f2, f13, Direction.DOWN, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, f17, 0.0D, red, green, blue, alpha, f, f13, Direction.DOWN, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0.0D, f17, 0.0F - layerHeight, red, green, blue, alpha, f, f13, Direction.DOWN, packedLightIn, overlayLightIn);
            vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), f4, f17, 0.0F - layerHeight, red, green, blue, alpha, f2, f13, Direction.DOWN, packedLightIn, overlayLightIn);
        }
    }

    public static void popper(float f, float f1, float f2, float f3, float f1shifted, float f3shifted, float layerHeight, MatrixStack stack, VertexConsumer buffer, int packedLightIn, int overlayLightIn) { //IVertexBuilder
        popper(f, f1, f2, f3, f1shifted, f3shifted, layerHeight, stack, buffer, packedLightIn, overlayLightIn, 1, 1, 1, 1);
    }

    public static void popper(float f, float f1, float f2, float f3, float f1shifted, float f3shifted, MatrixStack stack, VertexConsumer buffer, int packedLightIn, int overlayLightIn) { //IVertexBuilder
        popper(f, f1, f2, f3, f1shifted, f3shifted, 0.03125f, stack, buffer, packedLightIn, overlayLightIn);
    }
}
