package net.azzy.pulseflux.registry

import net.azzy.pulseflux.PulseFlux
import net.azzy.pulseflux.block.entity.logistic.*
import net.azzy.pulseflux.block.entity.power.SolarPanelBlock
import net.azzy.pulseflux.block.entity.power.ThermalDynamoBlock
import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine
import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity
import net.azzy.pulseflux.blockentity.logistic.diodes.SteelDiodeEntity
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidMergingEntity
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidSplittingEntity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator2Entity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator4Entity
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator8Entity
import net.azzy.pulseflux.blockentity.logistic.transport.BasicLiquidPipeEntity
import net.azzy.pulseflux.blockentity.power.SolarPanelEntity
import net.azzy.pulseflux.blockentity.power.ThermalDynamoEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.fluid.FlowableFluid
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import java.util.*
import java.util.function.Supplier

object BlockRegistry {

    @JvmStatic
    fun init() {}
    val DEFAULT_SHAPE: VoxelShape = VoxelShapes.fullCube()
    private val MATERIAL = Item.Settings().group(PulseFlux.MACHINE_MATERIALS)
    private val MACHINES = Item.Settings().group(PulseFlux.MACHINES)
    private val LOGISTICS = Item.Settings().group(PulseFlux.LOGISTICS)
    private val OBSIDIAN_MELD = FabricBlockSettings.of(Material.GLASS, MaterialColor.BLACK).strength(6.5f, 400f).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool()

    //Other
    val BLACKSTONE_PANEL = register("blackstone_panel", Block(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.5f, 6.0f).sounds(BlockSoundGroup.GILDED_BLACKSTONE).breakByTool(FabricToolTags.PICKAXES, 1)), MATERIAL)
    val OBSIDIAN_PANEL = register("obsidian_panel", Block(OBSIDIAN_MELD), MATERIAL)

    //misc blocks
    val STEEL_BLOCK = register("hsla_steel_block", Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(6f, 8f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.IRON).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL)
    val TITANIUM_BLOCK = register("titanium_block", Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(7f, 6f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.WHITE).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL)
    val TUNGSTEN_BLOCK = register("tungsten_block", Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(20f, 50f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.PICKAXES, 3)), MATERIAL.fireproof())

    //Logistics
    @JvmField
    val STEEL_DIODE = register("steel_diode", DiodeBlock(FabricBlockSettings.copyOf(Blocks.GRANITE), ::SteelDiodeEntity), LOGISTICS)
    @JvmField
    val CREATIVE_PULSE_SOURCE = register("creative_pulse_source", DiodeBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN), ::CreativePulseSourceEntity), LOGISTICS)
    @JvmField
    val MODULATOR_2 = register("modulator_2x", ModulatorBlock(FabricBlockSettings.copyOf(Blocks.GRANITE)) { Modulator2Entity(1260, 2500.0) }, LOGISTICS)
    @JvmField
    val MODULATOR_4 = register("modulator_4x", ModulatorBlock(FabricBlockSettings.copyOf(MODULATOR_2)) { Modulator4Entity(1260, 2500.0) }, LOGISTICS)
    @JvmField
    val MODULATOR_8 = register("modulator_8x", ModulatorBlock(FabricBlockSettings.copyOf(MODULATOR_2)) { Modulator8Entity(1260, 2500.0) }, LOGISTICS)
    @JvmField
    val SOLENOID_SPLIT = register("solenoid_splitting", SolenoidBlock(OBSIDIAN_MELD, ::SolenoidSplittingEntity), LOGISTICS)
    @JvmField
    val SOLENOID_MERGE = register("solenoid_merging", SolenoidBlock(OBSIDIAN_MELD, ::SolenoidMergingEntity), LOGISTICS)

    //Machines
    @JvmField
    val BLAST_FURNACE_MACHINE = register("blast_furnace", BlastFurnaceMachine(FabricBlockSettings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(3f, 4f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 2).lightLevel { e: BlockState -> if (e.get(BlastFurnaceMachine.LIT)) 15 else 0 }, DEFAULT_SHAPE), MACHINES)

    //Power
    @JvmField
    val SOLAR_PANEL = register("solar_panel", SolarPanelBlock(FabricBlockSettings.copyOf(Blocks.GRANITE), ::SolarPanelEntity), MACHINES)
    @JvmField
    val THERMAL_DYNAMO = register("thermal_dynamo", ThermalDynamoBlock(FabricBlockSettings.copyOf(Blocks.GRANITE), ::ThermalDynamoEntity), MACHINES)

    //Fluid
    @JvmField
    val BASIC_LIQUID_PIPE = register("liquid_pipe", FluidPipeBlock(FabricBlockSettings.copyOf(STEEL_BLOCK).sounds(BlockSoundGroup.GLASS), ::BasicLiquidPipeEntity), LOGISTICS)
    @JvmField
    val EVERFULL_URN = register("everfull_urn", EverfullUrnBlock(OBSIDIAN_MELD.lightLevel(7)), LOGISTICS)

    @JvmField
    @Environment(EnvType.CLIENT)
    val REGISTRY_TRANS: MutableList<Block> = ArrayList()

    @JvmField
    @Environment(EnvType.CLIENT)
    val REGISTRY_PARTIAL: MutableList<Block> = ArrayList()

    private fun register(name: String, block: Block, settings: Item.Settings): Block {
        Registry.register(Registry.ITEM, Identifier(PulseFlux.MOD_ID, name), BlockItem(block, settings))
        return Registry.register(Registry.BLOCK, Identifier(PulseFlux.MOD_ID, name), block)
    }

    private fun registerMachine(name: String, block: Block): Block {
        REGISTRY_PARTIAL.add(block)
        return Registry.register(Registry.BLOCK, Identifier(PulseFlux.MOD_ID, name), block)
    }

    private fun registerNoItem(name: String, block: Block): Block {
        return Registry.register(Registry.BLOCK, Identifier(PulseFlux.MOD_ID, name), block)
    }

    private fun registerFluidBlock(name: String, item: FlowableFluid, base: AbstractBlock.Settings): Block {
        return Registry.register(Registry.BLOCK, Identifier(PulseFlux.MOD_ID, name), object : FluidBlock(item, base) {})
    }

    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun initTransparency() {
        REGISTRY_TRANS.add(BASIC_LIQUID_PIPE)
    }

    @JvmStatic
    @Environment(EnvType.CLIENT)
    fun initPartialblocks() {
        REGISTRY_PARTIAL.add(STEEL_DIODE)
        REGISTRY_PARTIAL.add(CREATIVE_PULSE_SOURCE)
        REGISTRY_PARTIAL.add(MODULATOR_2)
        REGISTRY_PARTIAL.add(MODULATOR_4)
        REGISTRY_PARTIAL.add(MODULATOR_8)
        REGISTRY_PARTIAL.add(SOLENOID_SPLIT)
        REGISTRY_PARTIAL.add(SOLENOID_MERGE)
        REGISTRY_PARTIAL.add(SOLAR_PANEL)
        REGISTRY_PARTIAL.add(BASIC_LIQUID_PIPE)
        REGISTRY_PARTIAL.add(EVERFULL_URN)
        REGISTRY_PARTIAL.add(THERMAL_DYNAMO)
    }
}