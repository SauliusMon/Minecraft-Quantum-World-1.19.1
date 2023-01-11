package com.saulius.quantum_world.world.feature;

import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, QuantumWorld.MODID);

    public static final RegistryObject<PlacedFeature> ENERGIUM_ORE_PLACED = PLACED_FEATURES.register("energium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ENERGIUM_ORE.getHolder().get(),
                    commonOrePlacement(10,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(60)))));

    public static final RegistryObject<PlacedFeature> COSMIC_ORE_PLACED = PLACED_FEATURES.register("cosmic_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.COSMIC_ORE.getHolder().get(),
                    commonOrePlacement(3,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(0)))));


    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier1) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int veinsPerChunk, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(veinsPerChunk), placementModifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(i), placementModifier);
    }

    public static void register (IEventBus iEventBus) {
        PLACED_FEATURES.register(iEventBus);
    }
}
