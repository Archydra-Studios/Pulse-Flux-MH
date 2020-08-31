package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.DiodeEntity;
import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity;
import net.azzy.pulseflux.blockentity.logistic.diodes.SteelDiodeEntity;
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator2Entity;
import net.azzy.pulseflux.blockentity.production.BlastFurnaceMachineEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;
import static net.azzy.pulseflux.registry.BlockRegistry.*;


public class BlockEntityRegistry {

    public static final BlockEntityType<BlastFurnaceMachineEntity> BLAST_FURNACE_ENTITY = register("blast_furnace_machine_entity", BlastFurnaceMachineEntity::new, BLAST_FURNACE_MACHINE);
    public static final BlockEntityType<DiodeEntity> STEEL_DIODE_ENTITY = register("steel_diode_entity", SteelDiodeEntity::new, STEEL_DIODE);
    public static final BlockEntityType<ModulatorEntity> MODULATOR_2_ENTITY = register("modulator_2_entity", () -> new Modulator2Entity(1260, 2500), MODULATOR_2);

    public static final BlockEntityType<CreativePulseSourceEntity> CREATIVE_PULSE_SOURCE = register("creative_pulse_source", CreativePulseSourceEntity::new, BlockRegistry.CREATIVE_PULSE_SOURCE);

    private static <T extends BlockEntity> BlockEntityType<T>  register(String name, Supplier<T> item, Block block){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), BlockEntityType.Builder.create(item, block).build(null));
    }

    public static void init(){}
}
