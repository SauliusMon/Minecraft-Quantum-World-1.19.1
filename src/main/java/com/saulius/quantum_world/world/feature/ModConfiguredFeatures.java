package com.saulius.quantum_world.world.feature;

import com.google.common.base.Suppliers;
import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.BlocksRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, QuantumWorld.MODID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> ENERGIUM_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlocksRegistry.ENERGIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlocksRegistry.DEEPSLATE_ENERGIUM_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> COSMIC_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlocksRegistry.COSMIC_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlocksRegistry.DEEPSLATE_COSMIC_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ENERGIUM_ORE = CONFIGURED_FEATURES.register("energium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ENERGIUM_ORES.get(), 10)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> COSMIC_ORE = CONFIGURED_FEATURES.register("cosmic_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(COSMIC_ORES.get(), 5)));

    public static void register (IEventBus iEventBus) {
        CONFIGURED_FEATURES.register(iEventBus);
    }
}
