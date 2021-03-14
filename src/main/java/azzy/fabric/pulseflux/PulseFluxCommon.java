package azzy.fabric.pulseflux;

import azzy.fabric.incubus_core.datagen.Metadata;
import azzy.fabric.pulseflux.block.PulseFluxBlocks;
import azzy.fabric.pulseflux.item.PulseFluxItems;
import azzy.fabric.pulseflux.util.PulseFluxMaterials;
import azzy.fabric.pulseflux.util.PulseFluxRegistries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.SplittableRandom;

@SuppressWarnings("unused")
public class PulseFluxCommon implements ModInitializer {
	public static final String MODID = "pulseflux";

	public static final Logger PFLog = LogManager.getLogger(MODID);
	public static final SplittableRandom PFRandom = new SplittableRandom();
	public static final Metadata METADATA = new Metadata(MODID);

	public static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static final boolean REGEN_RECIPES = false, REGEN_ITEMS = true, REGEN_BLOCKS = true, REGEN_LOOT = true;

	public static final ItemGroup MACHINES = FabricItemGroupBuilder.create(new Identifier(MODID, "machine")).icon(() -> new ItemStack(Items.COD)).build();
	public static final ItemGroup LOGISTICS = FabricItemGroupBuilder.create(new Identifier(MODID, "logistic")).icon(() -> new ItemStack(PulseFluxBlocks.HSLA_STEEL_DIODE)).build();
	public static final ItemGroup TOOLS  = FabricItemGroupBuilder.create(new Identifier(MODID, "tool")).icon(() -> new ItemStack(PulseFluxItems.SCREWDRIVER)).build();
	public static final ItemGroup MACHINE_MATERIALS = FabricItemGroupBuilder.create(new Identifier(MODID, "material")).icon(() -> new ItemStack(PulseFluxItems.HSLA_STEEL_INGOT)).build();

	@Override
	public void onInitialize() {
		PulseFluxBlocks.init();
		PulseFluxItems.init();
		PulseFluxRegistries.init();
		PulseFluxMaterials.init();
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}
