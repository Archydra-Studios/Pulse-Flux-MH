package azzy.fabric.pulseflux.render.logistic;

import azzy.fabric.pulseflux.blockentity.logistics.DiodeBlockEntity;
import azzy.fabric.pulseflux.energy.ActiveIO;
import azzy.fabric.pulseflux.energy.PulseCarrier;
import azzy.fabric.pulseflux.render.RenderHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class DiodeRenderer<T extends DiodeBlockEntity> extends BlockEntityRenderer<T> {

    public DiodeRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.getWorld() != null) {
            matrices.push();
            float trans = Math.min(200, (300 + (entity.getLastUpdateTime() - (entity.getWorld().getTime() + tickDelta)) * 3.5F)) / 255;
            RenderHelper.drawPulseIO(vertexConsumers, matrices, entity, trans);
            matrices.pop();

            PulseCarrier pulse = entity.getStoredPulse();

            if(!PulseCarrier.isEmpty(pulse)) {
                matrices.push();
                ActiveIO<?> output = entity.getActiveOutputs().get(0);
                if(output != null) {
                    RenderHelper.drawLaser(vertexConsumers, matrices, pulse.polarity.color, 0.5F, output.distance - 1, 0.325F, 0.5F, output.io);
                }
                matrices.pop();

                matrices.push();
                if(output != null) {
                    RenderHelper.drawLaser(vertexConsumers, matrices, pulse.polarity.color, 0.7F, output.distance - 1, 0.15F, 0.5F, output.io);
                }
                matrices.pop();
            }
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(T blockEntity) {
        return true;
    }
}
