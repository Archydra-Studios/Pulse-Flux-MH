package net.azzy.pulseflux.registry;

import net.azzy.pulseflux.PulseFlux;
import net.azzy.pulseflux.block.entity.logistic.LinearDiodeBlock;
import net.azzy.pulseflux.block.entity.logistic.ModulatorBlock;
import net.azzy.pulseflux.block.entity.logistic.SolenoidBlock;
import net.azzy.pulseflux.block.entity.production.BlastFurnaceMachine;
import net.azzy.pulseflux.blockentity.logistic.CreativePulseSourceEntity;
import net.azzy.pulseflux.blockentity.logistic.diodes.SteelDiodeEntity;
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidMergingEntity;
import net.azzy.pulseflux.blockentity.logistic.misc.SolenoidSplittingEntity;
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator2Entity;
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator4Entity;
import net.azzy.pulseflux.blockentity.logistic.modulators.Modulator8Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;
import java.util.List;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class BlockRegistry {

    public static void init(){}

    public static final VoxelShape DEFAULT_SHAPE = VoxelShapes.fullCube();

    private static final Item.Settings MATERIAL = new Item.Settings().group(PulseFlux.MACHINE_MATERIALS);
    private static final Item.Settings MACHINES = new Item.Settings().group(PulseFlux.MACHINES);
    private static final Item.Settings LOGISTICS = new Item.Settings().group(PulseFlux.LOGISTICS);

    private static final FabricBlockSettings OBSIDIAN_MELD = FabricBlockSettings.of(Material.GLASS, MaterialColor.BLACK).strength(6.5f, 400).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool();


    //Other
    public static final Block BLACKSTONE_PANEL = register("blackstone_panel", new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.GILDED_BLACKSTONE).breakByTool(FabricToolTags.PICKAXES, 1)), MATERIAL);
    public static final Block OBSIDIAN_PANEL = register("obsidian_panel", new Block(OBSIDIAN_MELD), MATERIAL);

    //misc blocks
    public static final Block STEEL_BLOCK = register("hsla_steel_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(6f, 8f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.IRON).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL);
    public static final Block TITANIUM_BLOCK = register("titanium_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(7f, 6f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.WHITE).breakByTool(FabricToolTags.PICKAXES, 2)), MATERIAL);
    public static final Block TUNGSTEN_BLOCK = register("tungsten_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(20f, 50f).sounds(BlockSoundGroup.METAL).materialColor(MaterialColor.BLACK).breakByTool(FabricToolTags.PICKAXES, 3)), MATERIAL.fireproof());


    //Logistics
    public static final Block STEEL_DIODE = register("steel_diode", new LinearDiodeBlock<>(FabricBlockSettings.copyOf(Blocks.GRANITE), SteelDiodeEntity::new), LOGISTICS);

    public static final Block CREATIVE_PULSE_SOURCE = register("creative_pulse_source", new LinearDiodeBlock<>(FabricBlockSettings.copyOf(Blocks.OBSIDIAN), CreativePulseSourceEntity::new), LOGISTICS);

    public static final Block MODULATOR_2 = register("modulator_2x", new ModulatorBlock<>(FabricBlockSettings.copyOf(Blocks.GRANITE), () -> new Modulator2Entity(1260, 2500)), LOGISTICS);
    public static final Block MODULATOR_4 = register("modulator_4x", new ModulatorBlock<>(FabricBlockSettings.copyOf(MODULATOR_2), () -> new Modulator4Entity(1260, 2500)), LOGISTICS);
    public static final Block MODULATOR_8 = register("modulator_8x", new ModulatorBlock<>(FabricBlockSettings.copyOf(MODULATOR_2), () -> new Modulator8Entity(1260, 2500)), LOGISTICS);

    public static final Block SOLENOID_SPLIT = register("solenoid_splitting", new SolenoidBlock<>(OBSIDIAN_MELD, SolenoidSplittingEntity::new), LOGISTICS);
    public static final Block SOLENOID_MERGE = register("solenoid_merging", new SolenoidBlock<>(OBSIDIAN_MELD, SolenoidMergingEntity::new), LOGISTICS);

    //Machines
    public static final Block BLAST_FURNACE_MACHINE = register("blast_furnace", new BlastFurnaceMachine(FabricBlockSettings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(3f, 4f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 2).lightLevel(e -> e.get(BlastFurnaceMachine.LIT) ? 15 : 0), DEFAULT_SHAPE), MACHINES);

    //Fluid

    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_TRANS = new ArrayList<>();
    @Environment(EnvType.CLIENT)
    public static final List<Block> REGISTRY_PARTIAL = new ArrayList<>();

    private static Block register(String name, Block block, Item.Settings settings) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, settings));
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerNoItem(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
    }

    private static Block registerFluidBlock(String name, FlowableFluid item, AbstractBlock.Settings base) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), new FluidBlock(item, base) {
        });
    }

    @Environment(EnvType.CLIENT)
    public static void initTransparency() {
    }

    @Environment(EnvType.CLIENT)
    public static void initPartialblocks() {
        REGISTRY_PARTIAL.add(STEEL_DIODE);
        REGISTRY_PARTIAL.add(CREATIVE_PULSE_SOURCE);
        REGISTRY_PARTIAL.add(MODULATOR_2);
        REGISTRY_PARTIAL.add(MODULATOR_4);
        REGISTRY_PARTIAL.add(MODULATOR_8);
        REGISTRY_PARTIAL.add(SOLENOID_SPLIT);
        REGISTRY_PARTIAL.add(SOLENOID_MERGE);
    }
}
