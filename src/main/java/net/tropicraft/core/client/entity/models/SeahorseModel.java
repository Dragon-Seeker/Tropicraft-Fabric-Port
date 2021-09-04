package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.underdasea.SeahorseEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;
/*
public class SeahorseModel extends CompositeEntityModel<SeahorseEntity> {
    ModelPart head1;
    ModelPart snout1;
    ModelPart snout2;
    ModelPart snout3;
    ModelPart eye1;
    ModelPart eye2;
    ModelPart fin2;
    ModelPart fin4;
    ModelPart fin3;
    ModelPart neck1;
    ModelPart neck2;
    ModelPart belly;
    ModelPart tail1;
    ModelPart tail2;
    ModelPart tail3;
    ModelPart tail4;
    ModelPart tail5;
    ModelPart tail6;
    ModelPart tail7;
    ModelPart tail8;
    ModelPart tail9;
    ModelPart tail10;
    ModelPart tail11;
    ModelPart fin1;

    public SeahorseModel() {
        head1 = new ModelPart( this, 0, 0 );
        head1.setTextureSize( 64, 64 );
        head1.addCuboid( -2.5F, -2.5F, -2.5F, 5, 5, 5);
        head1.setPivot( 1F, -36F, 0.5F );

        snout1 = new ModelPart( this, 20, 0 );
        snout1.setTextureSize( 64, 64 );
        snout1.addCuboid( -1.5F, -1F, -1.5F, 3, 3, 4);
        snout1.setPivot( -2.448189F, -33.97269F, 2.980232E-08F );

        snout2 = new ModelPart( this, 34, 0 );
        snout2.setTextureSize( 64, 64 );
        snout2.addCuboid( -2.5F, -0.5F, -0.5F, 5, 2, 2);
        snout2.setPivot( -5.491952F, -31.3774F, 2.980232E-08F );

        snout3 = new ModelPart( this, 23, 7 );
        snout3.setTextureSize( 64, 64 );
        snout3.addCuboid( -0.5F, -1F, -1F, 1, 3, 3);
        snout3.setPivot( -7.54649F, -29.62558F, 0F );

        eye1 = new ModelPart( this, 40, 4 );
        eye1.setTextureSize( 64, 64 );
        eye1.addCuboid( -1F, -1F, -0.5F, 2, 2, 1);
        eye1.setPivot( -2.955017F, -34.83473F, -2F );

        eye2 = new ModelPart( this, 40, 4 );
        eye2.setTextureSize( 64, 64 );
        eye2.addCuboid( -1F, -1F, -0.5F, 2, 2, 1);

        eye2.setPivot( -2.958766F, -34.83232F, 3F );

        fin2 = new ModelPart( this, 39, 15 );
        fin2.setTextureSize( 64, 64 );
        fin2.addCuboid( -3F, -2.5F, 0F, 6, 5, 0);
        fin2.setPivot( 1.222835F, -38.81833F, 0.5F );

        fin4 = new ModelPart( this, 36, 9 );
        fin4.setTextureSize( 64, 64 );
        fin4.addCuboid( -4F, -2.5F, 0F, 4, 5, 0);
        fin4.setPivot( 1.000001F, -36F, -2F );

        fin3 = new ModelPart( this, 45, 9 );
        fin3.setTextureSize( 64, 64 );
        fin3.addCuboid( -4F, -2.5F, 0F, 4, 5, 0);
        fin3.setPivot( 1.000001F, -36F, 3F );

        neck1 = new ModelPart( this, 0, 10 );
        neck1.setTextureSize( 64, 64 );
        neck1.addCuboid( -2F, -2F, -2F, 4, 4, 4);
        neck1.setPivot( 3.5F, -33.5F, 0.5F );

        neck2 = new ModelPart( this, 0, 18 );
        neck2.setTextureSize( 64, 64 );
        neck2.addCuboid( -2.5F, -2F, -2.5F, 5, 4, 5);
        neck2.setPivot( 4.999997F, -31F, 0.5F );

        belly = new ModelPart( this, 0, 27 );
        belly.setTextureSize( 64, 64 );
        belly.addCuboid( -3.5F, 0F, -3F, 7, 8, 6);
        belly.setPivot( 5F, -30F, 0.5F );

        tail1 = new ModelPart( this, 0, 18 );
        tail1.setTextureSize( 64, 64 );
        tail1.addCuboid( -2.5F, 0F, -2.5F, 5, 4, 5);
        tail1.setPivot( 5.5F, -22.5F, 0.5F );

        tail2 = new ModelPart( this, 0, 41 );
        tail2.setTextureSize( 64, 64 );
        tail2.addCuboid( -2F, 0F, -2F, 4, 4, 4);
        tail2.setPivot( 5F, -19F, 0.5F );
        tail3 = new ModelPart( this, 0, 49 );
        tail3.setTextureSize( 64, 64 );
        tail3.addCuboid( -2F, 0F, -1.5F, 3, 4, 3);
        tail3.setPivot( 4.5F, -15.5F, 0.5F );
        tail4 = new ModelPart( this, 0, 56 );
        tail4.setTextureSize( 64, 64 );
        tail4.addCuboid( -1F, 0F, -1F, 2, 4, 2);
        tail4.setPivot( 2.652397F, -12.89918F, 0.5F );
        tail5 = new ModelPart( this, 8, 56 );
        tail5.setTextureSize( 64, 64 );
        tail5.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail5.setPivot( -0.8942064F, -12.51931F, 0.5F );
        tail6 = new ModelPart( this, 12, 56 );
        tail6.setTextureSize( 64, 64 );
        tail6.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail6.setPivot( -2.551666F, -13.06961F, 0.5F );
        tail7 = new ModelPart( this, 12, 56 );
        tail7.setTextureSize( 64, 64 );
        tail7.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail7.setPivot( -3.685031F, -14.47157F, 0.5F );
        tail8 = new ModelPart( this, 12, 56 );
        tail8.setTextureSize( 64, 64 );
        tail8.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail8.setPivot( -3.770199F, -16.05041F, 0.5F );
        tail9 = new ModelPart( this, 12, 56 );
        tail9.setTextureSize( 64, 64 );
        tail9.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail9.setPivot( -2.846481F, -17.36065F, 0.5F );
        tail10 = new ModelPart( this, 12, 56 );
        tail10.setTextureSize( 64, 64 );
        tail10.addCuboid( -0.5F, 0F, -0.5F, 1, 2, 1);
        tail10.setPivot( -0.2576861F, -15.77428F, 0.5F );
        tail11 = new ModelPart( this, 12, 56 );
        tail11.setTextureSize( 64, 64 );
        tail11.addCuboid( -0.5F, -1F, -0.5F, 1, 2, 1);
        tail11.setPivot( -0.856306F, -15.47153F, 0.5F );
        fin1 = new ModelPart( this, 40, 22 );
        fin1.setTextureSize( 64, 64 );
        fin1.addCuboid( -2.5F, -4F, 0F, 5, 8, 0);
        fin1.setPivot( 8.5F, -20F, 0.5F );
    }

    @Override
    public void animateModel(SeahorseEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        head1.pitch = 0F;
        head1.yaw = 0F;
        head1.roll = -0.7060349F;

        snout1.pitch = 0F;
        snout1.yaw = 0F;
        snout1.roll = -0.7060349F;

        snout2.pitch = 0F;
        snout2.yaw = 0F;
        snout2.roll = -0.7060349F;

        snout3.pitch = 0F;
        snout3.yaw = 0F;
        snout3.roll = -1.055101F;

        eye1.pitch = -0.1802033F;
        eye1.yaw = 0.1073159F;
        eye1.roll = -0.7155942F;

        eye2.pitch = -0.1327665F;
        eye2.yaw = 2.978997F;
        eye2.roll = -2.432569F;

        fin2.pitch = 0F;
        fin2.yaw = 0F;
        fin2.roll = -0.1043443F;

        fin4.pitch = -0.2562083F;
        fin4.yaw = -2.679784F;
        fin4.roll = 0.4709548F;

        fin3.pitch = 0.2562083F;
        fin3.yaw = 2.679784F;
        fin3.roll = 0.4709548F;

        neck1.pitch = 0F;
        neck1.yaw = 0F;
        neck1.roll = -0.7853982F;

        neck2.pitch = 0F;
        neck2.yaw = 0F;
        neck2.roll = -0.349066F;

        belly.pitch = 0F;
        belly.yaw = 0F;
        belly.roll = 0F;

        tail1.pitch = 0F;
        tail1.yaw = 0F;
        tail1.roll = 0.08726645F;

        tail2.pitch = 0F;
        tail2.yaw = 0F;
        tail2.roll = 0.3490658F;

        tail3.pitch = 0F;
        tail3.yaw = 0F;
        tail3.roll = 0.6981316F;

        tail4.pitch = 0F;
        tail4.yaw = 0F;
        tail4.roll = 1.466756F;

        tail5.pitch = 0F;
        tail5.yaw = 0F;
        tail5.roll = 1.947916F;

        tail6.pitch = 0F;
        tail6.yaw = 0F;
        tail6.roll = 2.471515F;

        tail7.pitch = 0F;
        tail7.yaw = 0F;
        tail7.roll = -3.113539F;

        tail8.pitch = 0F;
        tail8.yaw = 0F;
        tail8.roll = -2.415407F;

        tail9.pitch = 0F;
        tail9.yaw = 0F;
        tail9.roll = -1.542743F;

        tail10.pitch = 0F;
        tail10.yaw = 0F;
        tail10.roll = 2.659437F;

        tail11.pitch = 0F;
        tail11.yaw = 0F;
        tail11.roll = -2.415407F;

        fin1.pitch = 0F;
        fin1.yaw = 0F;
        fin1.roll = 0.2188137F;
    }

    @Override
    public void setAngles(SeahorseEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        fin2.roll = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            head1, snout1, snout2, snout3, eye1, eye2, fin1, fin2, fin4, fin3, neck1, neck2, belly,
                tail1, tail2, tail3, tail4, tail5, tail6, tail7, tail8, tail9, tail10, tail11
        );
    }
}


 */