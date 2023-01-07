package com.saulius.quantum_world.integration;

import com.saulius.quantum_world.recipes.BasicElectricityGeneratorRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;

public class BasicElectricityGeneratorRecipeCategory implements IRecipeCategory<BasicElectricityGeneratorRecipe> {
    @Override
    public RecipeType<BasicElectricityGeneratorRecipe> getRecipeType() {
        return null;
    }

    @Override
    public Component getTitle() {
        return null;
    }

    @Override
    public IDrawable getBackground() {
        return null;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BasicElectricityGeneratorRecipe recipe, IFocusGroup focuses) {

    }
}
