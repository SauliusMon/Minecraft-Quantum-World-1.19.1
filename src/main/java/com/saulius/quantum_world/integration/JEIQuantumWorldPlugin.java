package com.saulius.quantum_world.integration;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.recipes.EnergyFuelRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIQuantumWorldPlugin implements IModPlugin {
    public static RecipeType<EnergyFuelRecipe> ENERGY_FUEL_TYPE =
            new RecipeType<> (EnergyFuelRecipeCategory.UID, EnergyFuelRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(QuantumWorld.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new EnergyFuelRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<EnergyFuelRecipe> recipesGenerator = recipeManager.getAllRecipesFor(EnergyFuelRecipe.Type.INSTANCE);
        registration.addRecipes(ENERGY_FUEL_TYPE, recipesGenerator);
    }
}
