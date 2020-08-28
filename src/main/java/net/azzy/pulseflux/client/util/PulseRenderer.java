package net.azzy.pulseflux.client.util;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

public interface PulseRenderer<T extends PulseRenderingEntity> {

    default void renderPulse(MatrixStack matrices, VertexConsumerProvider consumers, T entity, float tickDelta){

        float pulseLength = (float) (entity.getPulseDistance() * 16) - 16;

        if(pulseLength <= 0 || entity.getPulseAlpha() <= 0.01f)
            return;

        int r;
        int g;
        int b;

        Direction aaa = entity.getPulseDirection();

        switch (entity.getPulsePolarity()){
            case NEGATIVE:
                r = 223;
                g = 246;
                b = 95;
                break;
            case POSITIVE:
                r = 246;
                g = 210;
                b = 131;
                break;
            default:
                r = 244;
                g = 115;
                b = 197;
        }

        int a = (int) (entity.getPulseAlpha() * 120);

        matrices.push();

        if(aaa == Direction.NORTH){
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            matrices.translate(-1, 0, 0);
        }
        else if(aaa == Direction.WEST){
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(-1, 0, -1);
        }
        else if(aaa == Direction.SOUTH){
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270));
            matrices.translate(0, 0, -1);
        }
        else if(aaa == Direction.UP){
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));
            matrices.translate(0, -1, 0);
        }
        else if(aaa == Direction.DOWN){
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(270));
            matrices.translate(-1, 0, 0);
        }

        matrices.translate(1, 6.0/16, 6.0/16);
        RenderHelper.renderScaledOverlayCuboid(matrices, consumers, r, g, b, a, pulseLength, 4, 4, false);
        matrices.translate(0, 1.0/16, 1.0/16);
        RenderHelper.renderScaledOverlayCuboid(matrices, consumers, r, g, b, (int) (a + (entity.getPulseAlpha() * 50)), pulseLength, 2, 2, false);

        matrices.pop();
    }
}
