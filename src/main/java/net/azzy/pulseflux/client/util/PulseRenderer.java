package net.azzy.pulseflux.client.util;

import net.azzy.pulseflux.ClientInit;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

import java.util.Set;

public interface PulseRenderer<T extends PulseRenderingEntity> {

    default void renderPulse(MatrixStack matrices, VertexConsumerProvider consumers, T entity, float tickDelta){

        float ae = (float) entity.getPulseDistance();
        float pulseLength = (float) (entity.getPulseDistance() * 16) - 16;

        if(pulseLength <= 0 || entity.getPulseAlpha() <= 0.01f)
            return;

        RenderMathHelper.RGBAWrapper color;

        Set<Direction> directions = entity.getPulseDirections();

        for(Direction direction : directions){
            switch (entity.getPulsePolarity(direction)){
                case NEGATIVE:
                    color = ClientInit.NEGATIVE_COLOR;
                    break;
                case POSITIVE:
                    color = ClientInit.POSITIVE_COLOR;
                    break;
                default:
                    color = ClientInit.NEUTRAL_COLOR;
            }

            int a = (int) (entity.getPulseAlpha() * 120);

            matrices.push();

            if(direction == Direction.NORTH){
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            else if(direction == Direction.WEST){
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }
            else if(direction == Direction.SOUTH){
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }
            else if(direction == Direction.UP){
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));
                matrices.translate(0, -1, 0);
            }
            else if(direction == Direction.DOWN){
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(270));
                matrices.translate(-1, 0, 0);
            }

            matrices.translate(1, 6.0/16, 6.0/16);
            RenderHelper.renderScaledOverlayCuboid(matrices, consumers, color.r, color.g, color.b, a, pulseLength, 4, 4, false);
            matrices.translate(0, 1.0/16, 1.0/16);
            RenderHelper.renderScaledOverlayCuboid(matrices, consumers, color.r, color.g, color.b, (int) (a + (entity.getPulseAlpha() * 50)), pulseLength, 2, 2, false);

            matrices.pop();
        }
    }
}
