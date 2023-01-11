package com.saulius.quantum_world.recipes;

import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, QuantumWorld.MODID);

    public static final RegistryObject<RecipeSerializer<EnergyFuelRecipe>> ENERGY_FUEL_SERIALIZER =
            SERIALIZERS.register("energy_fuel", () -> EnergyFuelRecipe.Serializer.INSTANCE);

    public static void register (IEventBus iEventBus) {
        SERIALIZERS.register(iEventBus);
    }
}
