package azzy.fabric.pulseflux.render;

import azzy.fabric.pulseflux.energy.ActiveIO;
import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import azzy.fabric.pulseflux.energy.PulseIo;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.HashSet;
import java.util.Set;

public class RenderHelper {

    public static final Vector3f INPUT_COLOR = new Vector3f(0.309F, 1F, 0.757F);
    public static final Vector3f OUTPUT_COLOR = new Vector3f(1F, 0.469F, 0.259F);
    public static final Vector3f MIXED_IO = new Vector3f(1F, 0.973F, 0.573F);

    public static void drawPulseIO(VertexConsumerProvider vertexConsumers, MatrixStack matrices, BlockEntity entity, float trans) {
        matrices.push();
        if(trans > 0) {
            BlockState state = entity.getCachedState();
            PulseIo provider = PulseFluxEnergyAPIs.PULSE.find(entity.getWorld(), entity.getPos(), Direction.DOWN);

            if(provider != null) {

                Set<Direction> inputs = provider.getInputs();
                Set<Direction> outputs = provider.getOutputs();
                Set<Direction> mixedIo = new HashSet<>(inputs);
                mixedIo.retainAll(outputs);

                inputs.forEach(input -> {
                    if(!mixedIo.contains(input)) {
                        RenderHelper.directionalMatrixOffset(matrices, input, 1);
                        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, RenderHelper.INPUT_COLOR, trans);
                        RenderHelper.directionalMatrixOffset(matrices, input, -1);
                    }
                });

                outputs.forEach(output -> {
                    if(!mixedIo.contains(output)) {
                        RenderHelper.directionalMatrixOffset(matrices, output, 1);
                        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, RenderHelper.OUTPUT_COLOR, trans);
                        RenderHelper.directionalMatrixOffset(matrices, output, -1);
                    }
                });

                mixedIo.forEach(mixed -> {
                    RenderHelper.directionalMatrixOffset(matrices, mixed, 1);
                    RenderHelper.drawEmissiveCube(vertexConsumers, matrices, RenderHelper.MIXED_IO, trans);
                    RenderHelper.directionalMatrixOffset(matrices, mixed, 1);
                });
            }
        }
        matrices.pop();
    }

    public static void drawPulseLaser(PulseCarrier pulse, ActiveIO<?> output, VertexConsumerProvider vertexConsumers, MatrixStack matrices, long time, float tickDelta) {
        drawPulseLaser(pulse, output, vertexConsumers, matrices, Math.min(0.8F, 1F - time % 16 / 20F - (tickDelta / 20)));
    }

    public static void drawPulseLaser(PulseCarrier pulse, ActiveIO<?> output, VertexConsumerProvider vertexConsumers, MatrixStack matrices, float trans) {
        if(!PulseCarrier.isEmpty(pulse) && output != null) {
            matrices.push();
            RenderHelper.drawLaser(vertexConsumers, matrices, pulse.polarity.color, trans * 0.8F, output.distance - 1, 0.325F, 0.5F, output.io);
            matrices.pop();
            matrices.push();
            RenderHelper.drawLaser(vertexConsumers, matrices, pulse.polarity.color, trans, output.distance - 1, 0.15F, 0.5F, output.io);
            matrices.pop();
        }
    }

    public static void drawLaser(VertexConsumerProvider vertexConsumers, MatrixStack matrices, Vector3f color, float trans, float length, float scale, float offset, Direction direction) {
        matrices.translate(offset, offset, offset);
        directionalMatrixMultiply(matrices, direction);
        matrices.translate(0F, 0F, offset);
        matrices.translate(-scale / 2F, -scale / 2F, 0F);
        matrices.scale(scale, scale, length);
        RenderHelper.drawEmissiveCube(vertexConsumers, matrices, color, trans);
    }

    public static void drawEmissiveCube(VertexConsumerProvider vertexConsumers, MatrixStack matrices, Vector3f color, float trans) {
        MatrixStack.Entry matrix = matrices.peek();
        VertexConsumer consumer = vertexConsumers.getBuffer(PulseFluxRenderLayers.SOFT_BLOOM);

        //north
        consumer.vertex(matrix.getModel(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //east
        consumer.vertex(matrix.getModel(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //south
        consumer.vertex(matrix.getModel(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //west
        consumer.vertex(matrix.getModel(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //down
        consumer.vertex(matrix.getModel(), 0, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 0, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 0, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();

        //up
        consumer.vertex(matrix.getModel(), 0, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 1).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 1, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
        consumer.vertex(matrix.getModel(), 0, 1, 0).color(color.getX(), color.getY(), color.getZ(), trans).next();
    }

    public static void directionalMatrixOffset(MatrixStack matrices, Direction direction, float offset) {
        Vec3i vector = direction.getVector();
        matrices.translate(vector.getX() * offset, vector.getY() * offset, vector.getZ() * offset);
    }

    public static void directionalMatrixMultiply(MatrixStack matrices, Direction direction) {
        switch (direction) {
            case NORTH: {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
                break;
            }
            case SOUTH: {
                break;
            }
            case EAST: {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
                break;
            }
            case WEST: {
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270));
                break;
            }
            case UP: {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(270));
                break;
            }
            case DOWN: {
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
                break;
            }
        }
    }
}
