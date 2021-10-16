package net.tropicraft.core.client.entity.renderers;

/*
import net.tropicraftFabric.common.entity.SeaTurtleEntity;
import net.tropicraftFabric.common.entity.placeable.FurnitureEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.tropicraft.Constants;
import net.tropicraft.core.common.entity.SeaTurtleEntity;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;
import net.tropicraft.core.common.entity.placeable.FurnitureEntity;
 */

//TODO: Get this working as it requires a custom event
//@EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MODID)
public class PlayerRotationHandler {
    /*
    private static float rotationYawHead, prevRotationYawHead, rotationPitch, prevRotationPitch;
    
    private static float interpolateAndWrap(float cur, float prev, float partial) {
        return MathHelper.wrapDegrees(prev + ((cur - prev) * partial));
    }


    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        MatrixStack stack = event.getMatrixStack();
        VertexConsumerProvider buffers = event.getBuffers();
        PlayerEntity p = event.getPlayer();
        Entity riding = p.getVehicle();
        if (riding instanceof BeachFloatEntity) {
            FurnitureEntity floaty = (FurnitureEntity) riding;

            stack.push();
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-(floaty.prevYaw + (event.getPartialRenderTick() * (floaty.yaw - floaty.prevYaw)))));
            stack.translate(0, 1.55, 1.55);
            stack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

            // Cancel out player camera rotation
            float f = interpolateAndWrap(p.bodyYaw, p.prevBodyYaw, event.getPartialRenderTick());
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(f));

            // Lock in head
            rotationYawHead = p.headYaw;
            prevRotationYawHead = p.prevHeadYaw;
            p.headYaw = p.bodyYaw;
            p.prevHeadYaw = p.prevBodyYaw;
            rotationPitch = p.pitch;
            prevRotationPitch = p.prevPitch;
            p.pitch = 10f;
            p.prevPitch = 10f;
            
            // Cancel limb swing
            p.limbAngle = 0;
            p.limbDistance = 0;
            p.lastLimbDistance = 0;
        }
        if (riding instanceof SeaTurtleEntity) {
            SeaTurtleEntity turtle = (SeaTurtleEntity) riding;
            stack.push();

            // Cancel out player camera rotation
            float pitch = interpolateAndWrap(turtle.pitch, turtle.prevPitch, event.getPartialRenderTick());
            float yaw = interpolateAndWrap(turtle.headYaw, turtle.prevHeadYaw, event.getPartialRenderTick());

            stack.translate(0, turtle.getMountedHeightOffset() - p.getHeightOffset(), 0);
            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(yaw));
            stack.translate(0, -0.1, 0); // TODO figure out why this budging is needed
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(pitch));
            stack.translate(0, 0.1, 0);
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(yaw));
            stack.translate(0, -turtle.getMountedHeightOffset() + p.getHeightOffset(), 0);

            Vec3d passengerOffset = (new Vec3d(-0.25f, 0.0D, 0.0D)).rotateY((float) (-Math.toRadians(yaw) - (Math.PI / 2)));
            stack.translate(passengerOffset.getX(), 0, passengerOffset.getZ());

            // Lock in head
            rotationPitch = p.pitch;
            prevRotationPitch = p.prevPitch;
            p.pitch = 10f;
            p.prevPitch = 10f;
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        PlayerEntity p = event.getPlayer();
        if (p.getVehicle() instanceof BeachFloatEntity || p.getVehicle() instanceof SeaTurtleEntity) {
            event.getMatrixStack().pop();
            p.pitch = rotationPitch;
            p.prevPitch = prevRotationPitch;
        }
        if (p.getVehicle() instanceof BeachFloatEntity) {
            p.headYaw = rotationYawHead;
            p.prevHeadYaw = prevRotationYawHead;
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerSpecials(RenderNameplateEvent event) {
        if (event.getEntity().getVehicle() instanceof BeachFloatEntity) {
            event.setResult(Result.DENY);
        }
    }
     */
}
