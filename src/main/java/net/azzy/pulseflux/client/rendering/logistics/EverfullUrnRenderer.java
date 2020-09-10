package net.azzy.pulseflux.client.rendering.logistics;

import net.azzy.pulseflux.blockentity.logistic.transport.EverfullUrnEntity;
import net.azzy.pulseflux.client.util.RenderHelper;
import net.azzy.pulseflux.client.util.RenderMathHelper;
import net.azzy.pulseflux.util.fluid.FluidHelper;
import net.azzy.pulseflux.util.fluid.FluidPackage;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.fluid.Fluids;

public class EverfullUrnRenderer extends BlockEntityRenderer<EverfullUrnEntity> {

    public EverfullUrnRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(EverfullUrnEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        FluidPackage fluid = entity.getFluid();
        if(!FluidHelper.isEmpty(fluid)){
            matrices.push();
            RenderMathHelper.RGBAWrapper color = fluid.getWrappedFluid() == Fluids.LAVA ? RenderMathHelper.fromHex(0xffa841) :  RenderMathHelper.fromHex(FluidRenderHandlerRegistry.INSTANCE.get(fluid.getWrappedFluid()).getFluidColor(entity.getWorld(), entity.getPos(), null));
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(0.25F, 0.25F, 0.25F);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.translate(-0.5f, -0.5f, -0.5f);
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1F, 1F, 1F);

            matrices.pop();

            matrices.push();
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(0.125F, 0.125F, 0.125F);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.translate(-0.5f, -0.5f, -0.5f);
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1F, 1F, 1F);
            matrices.pop();

            matrices.push();
            matrices.translate(0.5, 0.5, 0.5);
            matrices.scale(0.125F, 0.125F, 0.125F);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta)));
            matrices.translate(-2.5f, -0.0f, -0.0f);
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 3));
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2));
            matrices.translate(-0.5f, -0.5f, -0.5f);
            RenderHelper.renderOverlayCuboid(matrices, vertexConsumers, color.r, color.g, color.b, 200, 1F, 1F, 1F);
            matrices.pop();

        }
    }
}
