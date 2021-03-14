package azzy.fabric.pulseflux.block;

import azzy.fabric.incubus_core.datagen.BSJsonGen;
import azzy.fabric.incubus_core.datagen.LootGen;
import azzy.fabric.incubus_core.datagen.ModelJsonGen;
import azzy.fabric.pulseflux.PulseFluxCommon;
import azzy.fabric.pulseflux.block.logistics.CreativePulseSourceBlock;
import azzy.fabric.pulseflux.block.logistics.DiodeBlock;
import azzy.fabric.pulseflux.blockentity.logistics.CreativePulseSourceBlockEntity;
import azzy.fabric.pulseflux.blockentity.logistics.SteelDiodeBlockEntity;
import azzy.fabric.pulseflux.energy.PulseFluxEnergyAPIs;
import azzy.fabric.pulseflux.energy.PulseIo;
import azzy.fabric.pulseflux.util.IoProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

import static azzy.fabric.pulseflux.PulseFluxCommon.*;

@SuppressWarnings("unused")
public class PulseFluxBlocks {

    public static FabricItemSettings genericSettings(ItemGroup group, Rarity rarity) {
        return new FabricItemSettings().group(group).rarity(rarity);
    }

    //  BLOCKS

    //Materials
    public static final Block HSLA_STEEL_BLOCK = registerGeneratedBlock("hsla_steel_block", new Block(FabricBlockSettings.of(Material.METAL).strength(6.0F, 8.0F)), genericSettings(MACHINE_MATERIALS, Rarity.COMMON), SingletType.BLOCK);

    public static final Block OBSIDIAN_AMALGAM = registerGeneratedBlock("obsidian_amalgam", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), genericSettings(MACHINE_MATERIALS, Rarity.COMMON), SingletType.BLOCK);

    //Logistics
    public static final Block HSLA_STEEL_DIODE = registerBlock("hsla_steel_diode", new DiodeBlock(FabricBlockSettings.copyOf(HSLA_STEEL_BLOCK).nonOpaque(), SteelDiodeBlockEntity::new), genericSettings(LOGISTICS, Rarity.COMMON), true);

    public static final Block CREATIVE_PULSE_SOURCE = registerBlock("creative_pulse_source", new CreativePulseSourceBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).materialColor(MaterialColor.WHITE)), genericSettings(LOGISTICS, Rarity.EPIC), true);

    //  BLOCK ENTITIES

    //Logistisc
    public static final BlockEntityType<SteelDiodeBlockEntity> HSLA_STEEL_DIODE_BLOCK_ENTITY = registerBlockEntity("hsla_steel_diode_block_entity", SteelDiodeBlockEntity::new, HSLA_STEEL_DIODE);

    public static final BlockEntityType<CreativePulseSourceBlockEntity> CREATIVE_PULSE_SOURCE_BLOCK_ENTITY = registerBlockEntity("creative_pulse_source_block_entity", CreativePulseSourceBlockEntity::new, CREATIVE_PULSE_SOURCE);

    public static void init() {
        PulseFluxEnergyAPIs.IO_LOOKUP.registerForBlockEntities((provider, direction) -> (IoProvider) provider, HSLA_STEEL_DIODE_BLOCK_ENTITY, CREATIVE_PULSE_SOURCE_BLOCK_ENTITY);

        PulseFluxEnergyAPIs.PULSE.registerForBlockEntities((pulse, dir) -> (PulseIo) pulse, HSLA_STEEL_DIODE_BLOCK_ENTITY, CREATIVE_PULSE_SOURCE_BLOCK_ENTITY);
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), HSLA_STEEL_DIODE, CREATIVE_PULSE_SOURCE);
    }

    public static Block registerBlock(String name, Block item, Item.Settings settings, boolean genLoot) {
        Identifier id = PulseFluxCommon.id(name);
        Block block = Registry.register(Registry.BLOCK, id, item);
        Registry.register(Registry.ITEM, id, new BlockItem(block, settings));

        if(genLoot && DEV_ENV && REGEN_LOOT)
            LootGen.genSimpleBlockDropTable(METADATA, block);

        return block;
    }

    public static Block registerGeneratedBlock(String name, Block item, Item.Settings settings, SingletType type) {
        return registerGeneratedBlock(name, item, null, null, settings, type);
    }

    public static Block registerGeneratedBlock(String name, Block item, @Nullable Identifier parent, @Nullable Identifier texture, Item.Settings settings, SingletType type) {
        Identifier id =  new Identifier(MODID, name);
        Block block = Registry.register(Registry.BLOCK, id, item);
        Registry.register(Registry.ITEM, id, new BlockItem(block, settings));

        if(DEV_ENV) {
            if(REGEN_BLOCKS) {
                Identifier texId = texture == null ? new Identifier(MODID, "block/" + name) : texture;

                switch (type) {
                    case BLOCK:
                        BSJsonGen.genBlockBS(METADATA, id, "block/");
                        ModelJsonGen.genBlockJson(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case SLAB:
                        BSJsonGen.genSlabBS(METADATA, id, Objects.requireNonNull(parent),"block/");
                        ModelJsonGen.genSlabJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case STAIRS:
                        BSJsonGen.genStairsBS(METADATA, id, "block/");
                        ModelJsonGen.genStairJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case PILLAR:
                        //BSJsonGen.genStairsBS(METADATA, id, "block/");
                        //ModelJsonGen.genStairJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case WALL:
                        BSJsonGen.genWallBS(METADATA, id, "block/");
                        ModelJsonGen.genWallJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case FENCE:
                        break;
                    default:
                }
            }
            if(REGEN_LOOT) {
                LootGen.genSimpleBlockDropTable(METADATA, block);
            }
        }

        return block;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, Supplier<BlockEntity> entitySupplier, Block ... blocks) {
        return (BlockEntityType<T>) Registry.register(Registry.BLOCK_ENTITY_TYPE, PulseFluxCommon.id(name), BlockEntityType.Builder.create(entitySupplier, blocks).build(null));
    }

    enum SingletType {
        BLOCK,
        SLAB,
        STAIRS,
        PILLAR,
        WALL,
        FENCE,
        NONE
    }

    private enum WallType {
        WALL,
        FENCE,
        NONE
    }
}
