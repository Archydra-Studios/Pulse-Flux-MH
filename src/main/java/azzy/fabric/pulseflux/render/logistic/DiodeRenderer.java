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
            float trans = Math.min(200, (300 + (entity.getLastUpdateTime() - (entity.getWorld().getTime() + tickDelta)) * 3.5F)) / 255;
            RenderHelper.drawPulseIO(vertexConsumers, matrices, entity, trans);
            RenderHelper.drawPulseLaser(entity.getStoredPulse(), entity.getActiveOutputs().get(0), vertexConsumers, matrices, entity.getWorld().getTime(), tickDelta);
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(T blockEntity) {
        return true;
    }
}
