package net.azzy.pulseflux.util.interaction;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class HeatTransferHelper {

    private static final HashMap<Block, HeatSource> heatMap = new HashMap<>();

    public static void init() {
        for (int i = 0; i < HeatSource.values().length; i++)
            heatMap.put(HeatSource.values()[i].block, HeatSource.values()[i]);
    }

    private static double calculateHeat(HeatMaterial medium, double bodyATemp, HeatSource bodyB) {
        double transfer;
        transfer = (medium.transfer / 419) * Math.pow(bodyB.size, 2) * (bodyATemp - bodyB.temp);
        return transfer;
    }

    private static double calculateHeat(HeatMaterial medium, double bodyATemp, double bodyBTemp, double area) {
        double transfer;
        transfer = (medium.transfer / 419) * area * (bodyATemp - bodyBTemp);
        return transfer;
    }

    public static <T extends HeatHolder> void simulateHeat(HeatMaterial medium, T bodyA, T bodyB) {
        double area = Math.min(bodyA.getArea(), bodyB.getArea());
        if(bodyA.forceArea() || bodyB.forceArea()){
            area = bodyA.forceArea() ? bodyA.getArea() : bodyB.getArea();
        }
        double flux = calculateHeat(medium, bodyA.getHeat(), bodyB.getHeat(), area);
        bodyA.moveHeat(-flux);
        bodyB.moveHeat(flux);
    }

    public static <T extends HeatHolder> void simulateHeat(HeatMaterial medium, T bodyA, Block bodyB) {
        HeatSource source = heatMap.get(bodyB);
        double flux = calculateHeat(medium, bodyA.getHeat(), source);
        flux = bodyA.getHeat() > source.temp ? -flux : flux;
        bodyA.moveHeat(-flux);
    }

    public static boolean isHeatSource(Block body) {
        return heatMap.containsKey(body);
    }

    public static double translateBiomeHeat(Biome biome) {
        if (biome.getCategory() == Biome.Category.NETHER) {
            return (biome.getTemperature() + 2) * 31.25;
        } else if (biome.getCategory() == Biome.Category.THEEND) {
            return (biome.getTemperature() - 1.5) * 31.25;
        } else {
            return biome.getTemperature() * 31.25;
        }
    }

    public static <T extends HeatHolder> void simulateAmbientHeat(T bodyA, Biome biome) {
        double flux = calculateHeat(HeatMaterial.AIR, bodyA.getHeat(), translateBiomeHeat(biome), bodyA.getArea());
        bodyA.moveHeat(-flux);
    }

    public enum HeatMaterial {
        NULL(0.0),
        AIR(0.026),
        BRICK(0.6),
        STEEL(50.2),
        WATER(0.6),
        GENERIC_FLUID(0.35),
        DIAMOND(335.2),
        ALUMINIUM(205),
        GRANITE(1.73),
        CERAMIC(41),
        TITANIUM(6.7),
        TUNGSTEN(164),
        BEDROCK(400);

        private final double transfer;

        HeatMaterial(double transfer) {
            this.transfer = transfer;
        }

        public static HeatMaterial nullableValueOf(String name){
            try {
                return HeatMaterial.valueOf(name);
            } catch (Exception ignored){
                return NULL;
            }
        }
    }

    public enum HeatSource {
        TEST(9999, Blocks.BARRIER, 1),
        FIRE(900, Blocks.FIRE, 0.65),
        MAGMA(600, Blocks.MAGMA_BLOCK, 1),
        LAVA(1300, Blocks.LAVA, 1),
        GLOWSTONE(200, Blocks.GLOWSTONE, 1),
        TORCH(700, Blocks.TORCH, 0.125),
        WALLTORCH(700, Blocks.WALL_TORCH, 0.125),
        SOULFIRE(1000, Blocks.SOUL_FIRE, 0.65),
        CAMPFIRE(850, Blocks.CAMPFIRE, 0.8),
        SOULCAMPFIRE(1000, Blocks.SOUL_CAMPFIRE, 0.8),
        SOULTORCH(750, Blocks.SOUL_TORCH, 0.125),
        WALLSOULTORCH(750, Blocks.SOUL_WALL_TORCH, 0.125);

        private final int temp;
        private final Block block;
        private final double size;

        HeatSource(int temp, Block block, double size) {
            this.temp = temp;
            this.block = block;
            this.size = size;
        }

        public static HeatSource getSource(Block block) {
            if (heatMap.containsKey(block))
                return heatMap.get(block);
            return null;
        }

        public int getTemp() {
            return temp;
        }
    }
}
