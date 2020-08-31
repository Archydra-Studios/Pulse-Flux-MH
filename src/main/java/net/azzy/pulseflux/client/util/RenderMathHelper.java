package net.azzy.pulseflux.client.util;

import org.apache.commons.lang3.tuple.Triple;

public class RenderMathHelper {

    public static Triple<Integer, Integer, Integer> hexToRGB(int hex){
        return new Triple<Integer, Integer, Integer>() {
            @Override
            public Integer getLeft() {
                return (hex & 0xFF0000) >> 16;
            }

            @Override
            public Integer getMiddle() {
                return (hex & 0xFF00) >> 8;
            }

            @Override
            public Integer getRight() {
                return (hex & 0xFF);
            }
        };
    }

    public static RGBAWrapper fromHex(int hex){
        Triple<Integer, Integer, Integer> rgb = hexToRGB(hex);
        return new RGBAWrapper(rgb.getLeft(), rgb.getMiddle(), rgb.getRight());
    }

    public static RGBAWrapper fromHex(int hex, int alpha){
        Triple<Integer, Integer, Integer> rgb = hexToRGB(hex);
        return new RGBAWrapper(rgb.getLeft(), rgb.getMiddle(), rgb.getRight(), alpha);
    }

    public static class RGBAWrapper {

        public final int r, g, b, a;

        RGBAWrapper(int r, int g, int b){
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = 255;
        }

        RGBAWrapper(int r, int g, int b, int a){
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }

        public RGBAWrapper appendAlpha(int alpha){
            return new RGBAWrapper(this.r, this.b, this.g, alpha);
        }
    }
}
