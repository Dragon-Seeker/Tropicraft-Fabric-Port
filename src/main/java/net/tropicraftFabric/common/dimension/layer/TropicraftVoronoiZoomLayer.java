package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum TropicraftVoronoiZoomLayer implements ParentedLayer {
    INSTANCE;

    private static final int ZOOM_BITS = 2;

    @Override
    public int sample(LayerSampleContext<?> random, LayerSampler area, int x, int y) {
        int absx = x - 2;
        int absy = y - 2;
        int shiftedAbsX = absx >> 2;
        int shiftedAbsY = absy >> 2;
        int backX = shiftedAbsX << 2;
        int backY = shiftedAbsY << 2;
        random.initSeed((long)backX, (long)backY);
        double offsetX = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
        double offsetY = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
        random.initSeed((long)(backX + 4), (long)backY);
        double offsetYY = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double offsetXY = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
        random.initSeed((long)backX, (long)(backY + 4));
        double offsetYX = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
        double offsetXX = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        random.initSeed((long)(backX + 4), (long)(backY + 4));
        double offsetYXY = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double offsetXXY = ((double)random.nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        int xx = absx & 3;
        int yy = absy & 3;
        // TODO performance improve -> MathHelper
        double a0 = Math.abs(yy - offsetY) + Math.abs(xx - offsetX);
        //double a0 = ((double)yy - offsetY) * ((double)yy - offsetY) + ((double)xx - offsetX) * ((double)xx - offsetX);
        double a1 = Math.abs(yy - offsetXY) + Math.abs(xx - offsetYY);
        //double a1 = ((double)yy - offsetXY) * ((double)yy - offsetXY) + ((double)xx - offsetYY) * ((double)xx - offsetYY);
        double a2 = Math.abs(yy - offsetXX) + Math.abs(xx - offsetYX);
        //double a2 = ((double)yy - offsetXX) * ((double)yy - offsetXX) + ((double)xx - offsetYX) * ((double)xx - offsetYX);
        double a3 = Math.abs(yy - offsetXXY) + Math.abs(xx - offsetYXY);
        //double a3 = ((double)yy - offsetXXY) * ((double)yy - offsetXXY) + ((double)xx - offsetYXY) * ((double)xx - offsetYXY);
        if (a0 < a1 && a0 < a2 && a0 < a3) {
            return area.sample(this.transformX(backX), this.transformZ(backY));
        } else if (a1 < a0 && a1 < a2 && a1 < a3) {
            return area.sample(this.transformX(backX + 4), this.transformZ(backY)) & 255;
        } else {
            return a2 < a0 && a2 < a1 && a2 < a3 ? area.sample(this.transformX(backX), this.transformZ(backY + 4)) : area.sample(this.transformX(backX + 4), this.transformZ(backY + 4)) & 255;
        }
    }

    @Override
    public int transformX(int x) {
        return x >> ZOOM_BITS;
    }

    @Override
    public int transformZ(int z) {
        return z >> ZOOM_BITS;
    }
}
