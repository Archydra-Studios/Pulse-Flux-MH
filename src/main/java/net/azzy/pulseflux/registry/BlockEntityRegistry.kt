package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity
import net.azzy.pulseflux.blockentity.logistic.ModulatorEntity
import net.azzy.pulseflux.blockentity.logistic.diodes.SteelDiodeEntity
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidMergingEntity
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidSplittingEntity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator2Entity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator4Entity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator8Entity
import net.azzy.pulseflux.blockentity.logistic.transport.BasicLiquidPipeEntity
import net.azzy.pulseflux.blockentity.logistic.transport.EverfullUrnEntity
import net.azzy.pulseflux.blockentity.power.SolarPanelEntity
import net.azzy.pulseflux.blockentity.power.ThermalDynamoEntity
import net.azzy.pulseflux.blockentity.production.BlastFurnaceMachineEntity
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

object BlockEntityRegistry {

    @JvmField
    val BLAST_FURNACE_ENTITY = register("blast_furnace_machine_entity", ::BlastFurnaceMachineEntity, BlockRegistry.BLAST_FURNACE_MACHINE)
    @JvmField
    val STEEL_DIODE_ENTITY = register("steel_diode_entity", ::SteelDiodeEntity, BlockRegistry.STEEL_DIODE)
    @JvmField
    val MODULATOR_2_ENTITY = register<ModulatorEntity>("modulator_2_entity", { Modulator2Entity(1260, 2500.0) }, BlockRegistry.MODULATOR_2)
    @JvmField
    val MODULATOR_4_ENTITY = register<ModulatorEntity>("modulator_4_entity", { Modulator4Entity(1260, 2500.0) }, BlockRegistry.MODULATOR_4)
    @JvmField
    val MODULATOR_8_ENTITY = register<ModulatorEntity>("modulator_8_entity", { Modulator8Entity(1260, 2500.0) }, BlockRegistry.MODULATOR_8)
    @JvmField
    val SOLENOID_SPLITTING_ENTITY = register("splitter_entity", ::SolenoidSplittingEntity, BlockRegistry.SOLENOID_SPLIT)
    @JvmField
    val SOLENOID_MERGING_ENTITY = register("merger_entity", ::SolenoidMergingEntity, BlockRegistry.SOLENOID_MERGE)
    @JvmField
    val SOLAR_PANEL_ENTITY = register("solar_panel_entity", ::SolarPanelEntity, BlockRegistry.SOLAR_PANEL)
    @JvmField
    val LIQUID_PIPE_ENTITY = register("liquid_pipe_entity", ::BasicLiquidPipeEntity, BlockRegistry.BASIC_LIQUID_PIPE)
    @JvmField
    val EVERFULL_URN_ENTITY = register("everfull_urn_entity", ::EverfullUrnEntity, BlockRegistry.EVERFULL_URN)

    val THERMAL_DYNAMO_ENTITY = register("thermal_dynamo_entity", ::ThermalDynamoEntity, BlockRegistry.THERMAL_DYNAMO)

    @JvmField
    val CREATIVE_PULSE_SOURCE = register("creative_pulse_source", ::CreativePulseSourceEntity, BlockRegistry.CREATIVE_PULSE_SOURCE)

    private fun <T : BlockEntity> register(name: String, item: Supplier<T>, block: Block): BlockEntityType<T> {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Identifier(PulseFlux.MOD_ID, name), BlockEntityType.Builder.create(item, block).build(null))
    }

    @JvmStatic
    fun init() {}
}