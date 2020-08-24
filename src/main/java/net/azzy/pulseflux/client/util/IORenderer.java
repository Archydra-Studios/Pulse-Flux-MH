package net.azzy.pulseflux.client.util;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface IORenderer<T extends IORenderingEntity> {

    default void renderIO(MatrixStack matrices, VertexConsumerProvider consumers, T entity, double tickDelta){

        if(entity.getRenderTickTime() <= 0)
            return;

        List<Direction> in = new LinkedList<>(entity.getInputs());
        List<Direction> out = new LinkedList<>(entity.getOutputs());

        int alpha = (Math.min(entity.getRenderTickTime(), 100));

        for(Direction direction : in){
            matrices.push();
            translateDirection(matrices, direction, false);
            if(out.contains(direction)){
                out.remove(direction);

                RenderHelper.renderOverlayCuboid(matrices, consumers, 252, 222, 116, alpha, 1, 1 ,1, true);
                continue;
            }
            RenderHelper.renderOverlayCuboid(matrices, consumers, 105, 255, 147, alpha, 1, 1 ,1, true);

            matrices.pop();
        }
        for(Direction direction : out){
            matrices.push();
            translateDirection(matrices, direction, false);
            RenderHelper.renderOverlayCuboid(matrices, consumers, 255, 75, 39, alpha, 1, 1 ,1, false);
            matrices.pop();
        }
    }

    default void translateDirection(MatrixStack matrices, Direction direction, boolean inverse){
        int multiplier = inverse ? -1 : 1;

        switch (direction){
            case SOUTH: matrices.translate(0, 0, multiplier); break;
            case EAST: matrices.translate(multiplier, 0, 0); break;
            case WEST: matrices.translate(-multiplier, 0, 0); break;
            case UP: matrices.translate(0, multiplier, 0); break;
            case DOWN: matrices.translate(0, -multiplier, 0); break;
            default: matrices.translate(0, 0, -multiplier); break;
        }
    }
}
