package net.azzy.pulseflux.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;


public class FluidRegistry {

    public static final List<FluidPair> FLUID_PAIRS = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static final List<Fluid> FLUID_TRANS = new ArrayList<>();


    @Environment(EnvType.CLIENT)
    public static void initTransparency() {
    }

    public static FlowableFluid registerStill(String name, FlowableFluid item) {
        Registry.register(Registry.FLUID, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static FlowableFluid registerFlowing(String name, FlowableFluid item) {
        Registry.register(Registry.FLUID, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static void init() {
    }

    public static class FluidPair {

        private final FlowableFluid stillState;
        private final FlowableFluid flowState;
        private final int color;

        private FluidPair(final FlowableFluid stillState, final FlowableFluid flowState, final int color) {
            this.stillState = stillState;
            this.flowState = flowState;
            this.color = color;
        }

        public FlowableFluid getStillState() {
            return stillState;
        }

        public FlowableFluid getFlowState() {
            return flowState;
        }

        public int getColor() {
            return color;
        }
    }
}
