package net.azzy.pulseflux.client.util;

import net.azzy.pulseflux.util.energy.PulseNode;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;

public class RenderHelper {

    public static final int MAX_LIGHT = 0x00F000F0;

    public static void renderOverlayCuboid(MatrixStack matrices, VertexConsumerProvider consumers, int r, int g, int b, int a, float sizeX, float sizeY, float sizeZ, boolean offset){

        VertexConsumer consumer = consumers.getBuffer(FFRenderLayers.OVERLAY);
        Matrix4f model = matrices.peek().getModel();

        if(offset) {
            matrices.translate(-0.001, -0.001, -0.001);
            matrices.scale(1.002f, 1.002f, 1.002f);
        }

        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();

        matrices.translate(sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(0, 0, -sizeZ);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();

        matrices.translate(1, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.translate(-sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();

        matrices.translate(0, sizeY, 0);
        consumer.vertex(model, 0, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(r, g, b, a).next();
        consumer.vertex(model, sizeX, 0, 0).color(r, g, b, a).next();
        consumer.vertex(model, 0, 0, 0).color(r, g, b, a).next();
    }

    public static void renderCuboid(MatrixStack matrices, VertexConsumerProvider consumers, int a, int light, float sizeX, float sizeY, float sizeZ, Identifier texture){

        Matrix4f model = matrices.peek().getModel();
        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getSolid());

        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(1, 0).light(light).next();

        matrices.translate(1, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(1, 0).light(light).next();

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(0, 0, -sizeZ);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(1, 0).light(light).next();

        matrices.translate(sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(1, 0).light(light).next();

        matrices.translate(-sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(1, 0).light(light).next();

        matrices.translate(0, sizeY, 0);
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(0, 0).light(light).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(200, 200, 200, a).texture(0, 1).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(1, 1).light(light).next();
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(1, 0).light(light).next();
    }

    public static void renderScaledCuboid(MatrixStack matrices, VertexConsumerProvider consumers, int a, int light, float sizeX, float sizeY, float sizeZ, Identifier texture, Scaling scalingMode, boolean centered){

        //0x00F000F0
        Matrix4f model = matrices.peek().getModel();
        VertexConsumer consumer = consumers.getBuffer(RenderLayer.getSolid());
        float u = 0;
        float v = 0;
        float minU = 0;
        float minV = 0;
        float offsetX = Math.max(0, (1 - sizeX )/2);
        float offsetY = Math.max(0, (1 - sizeY)/2);
        float offsetZ = Math.max(0, (1 - sizeZ)/2);

        if(centered)
            matrices.translate(-sizeX/2, -sizeY/2, -sizeZ/2);

        switch (scalingMode){
            case MAX:{
                u = Math.min(1, sizeX);
                v = Math.min(1, sizeY);
                break;
            }
            case MIN:{
                minU = Math.max(0, 1 - sizeX);
                minV = Math.max(0, 1 - sizeY);
                u = 1;
                v = 1;
                break;
            }
            case CENTER:{
                minU = 0 + offsetX;
                minV = 0 + offsetY;
                u = 1 - offsetX;
                v = 1 - offsetY;
            }
        }

        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(u, minV).light(light).next();

        switch (scalingMode){
            case MAX:{
                u = Math.min(1, sizeZ);
                v = Math.min(1, sizeY);
                break;
            }
            case MIN:{
                minU = Math.max(0, 1 - sizeZ);
                minV = Math.max(0, 1 - sizeY);
                u = 1;
                v = 1;
                break;
            }
            case CENTER:{
                minU = 0 + offsetZ;
                minV = 0 + offsetY;
                u = 1 - offsetZ;
                v = 1 - offsetY;
            }
        }

        matrices.translate(sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).next();

        switch (scalingMode){
            case MAX:{
                u = Math.min(1, sizeX);
                v = Math.min(1, sizeY);
                break;
            }
            case MIN:{
                minU = Math.max(0, 1 - sizeX);
                minV = Math.max(0, 1 - sizeY);
                u = 1;
                v = 1;
                break;
            }
            case CENTER:{
                minU = 0 + offsetX;
                minV = 0 + offsetY;
                u = 1 - offsetX;
                v = 1 - offsetY;
            }
        }

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(0, 0, -sizeZ);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, sizeX, sizeY, 0).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(u, minV).light(light).next();

        switch (scalingMode){
            case MAX:{
                u = Math.min(1, sizeZ);
                v = Math.min(1, sizeY);
                break;
            }
            case MIN:{
                minU = Math.max(0, 1 - sizeZ);
                minV = Math.max(0, 1 - sizeY);
                u = 1;
                v = 1;
                break;
            }
            case CENTER:{
                minU = 0 + offsetZ;
                minV = 0 + offsetY;
                u = 1 - offsetZ;
                v = 1 - offsetY;
            }
        }

        matrices.translate(sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, 0, sizeY, 0).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, 0, sizeY, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).next();

        switch (scalingMode){
            case MAX:{
                u = Math.min(1, sizeZ);
                v = Math.min(1, sizeX);
                break;
            }
            case MIN:{
                minU = Math.max(0, 1 - sizeZ);
                minV = Math.max(0, 1 - sizeX);
                u = 1;
                v = 1;
                break;
            }
            case CENTER:{
                minU = 0 + offsetZ;
                minV = 0 + offsetX;
                u = 1 - offsetZ;
                v = 1 - offsetX;
            }
        }

        matrices.translate(-sizeX, 0, 0);
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(u, minV).light(light).next();

        matrices.translate(0, sizeY, 0);
        consumer.vertex(model, 0, 0, sizeZ).color(200, 200, 200, a).texture(minU, minV).light(light).next();
        consumer.vertex(model, sizeX, 0, sizeZ).color(200, 200, 200, a).texture(minU, v).light(light).next();
        consumer.vertex(model, sizeX, 0, 0).color(200, 200, 200, a).texture(u, v).light(light).next();
        consumer.vertex(model, 0, 0, 0).color(200, 200, 200, a).texture(u, minV).light(light).next();

        matrices.translate(0, -sizeY, 0);
        matrices.translate(sizeX, 0, 0);
        matrices.translate(-sizeX, 0, 0);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-180));
        matrices.translate(0, 0, sizeZ);
        matrices.translate(-sizeX, 0, 0);
        matrices.translate(0, 0, -sizeZ * 2);

        if(centered)
            matrices.translate(sizeX/2, sizeY/2, sizeZ/2);
    }

    public enum Scaling{
        MIN,
        MAX,
        CENTER
    }
}