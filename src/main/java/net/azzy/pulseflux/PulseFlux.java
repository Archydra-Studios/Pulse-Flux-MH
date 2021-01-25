package net.azzy.pulseflux;

import net.azzy.pulseflux.registry.*;
import net.azzy.pulseflux.util.interaction.HeatTransferHelper;
import net.azzy.pulseflux.util.networking.ClientPacketRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Random;
import java.util.SplittableRandom;

import static net.azzy.pulseflux.registry.BlockRegistry.BLAST_FURNACE_MACHINE;
import static net.azzy.pulseflux.registry.BlockRegistry.STEEL_DIODE;
import static net.azzy.pulseflux.registry.ItemRegistry.SCREWDRIVER;
import static net.azzy.pulseflux.registry.ItemRegistry.STEEL_INGOT;

public class PulseFlux implements ModInitializer {
	public static final String MOD_ID = "pulseflux";

	public static final Logger PFLog = LogManager.getLogger(MOD_ID);
	public static final SplittableRandom PFRandom = new SplittableRandom();

	public static final ItemGroup MACHINE_MATERIALS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "material")).icon(() -> new ItemStack(STEEL_INGOT)).build();
	public static final ItemGroup MACHINES = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "machine")).icon(() -> new ItemStack(BLAST_FURNACE_MACHINE.asItem())).build();
	public static final ItemGroup LOGISTICS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "logistic")).icon(() -> new ItemStack(STEEL_DIODE)).build();
	public static final ItemGroup TOOLS  = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "tool")).icon(() -> new ItemStack(SCREWDRIVER)).build();

	public static final boolean CANVAS_LOADED = FabricLoader.getInstance().isModLoaded("canvas");

	@Override
	public void onInitialize() {
		ItemRegistry.init();
		BlockRegistry.init();
		RecipeRegistry.INSTANCE.awaken();
		BlockEntityRegistry.init();
		FTLRegistry.init();
		ContainerRegistry.init();
		FluidRegistry.init();
		HeatTransferHelper.init();
		ClientPacketRegistry.init();
	}
}
