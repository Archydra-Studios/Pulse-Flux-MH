package azzy.fabric.pulseflux.block;

import azzy.fabric.incubus_core.datagen.BSJsonGen;
import azzy.fabric.incubus_core.datagen.LootGen;
import azzy.fabric.incubus_core.datagen.ModelJsonGen;
import azzy.fabric.pulseflux.PulseFluxCommon;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static azzy.fabric.pulseflux.PulseFluxCommon.*;

@SuppressWarnings("unused")
public class PulseFluxBlocks {

    public static FabricItemSettings genericSettings(ItemGroup group, Rarity rarity) {
        return new FabricItemSettings().group(group).rarity(rarity);
    }

    public static final Block OBSIDIAN_AMALGAM = registerGeneratedBlock("obsidian_amalgam", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)), genericSettings(MACHINE_MATERIALS, Rarity.COMMON), SingletType.BLOCK);

    public static void init() {}

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
