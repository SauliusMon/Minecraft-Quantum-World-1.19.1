package com.saulius.quantum_world.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.recipes.EnergyFuelRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EnergyFuelRecipeCategory implements IRecipeCategory<EnergyFuelRecipe> {
   public static final ResourceLocation UID = new ResourceLocation(QuantumWorld.MODID, "energy_fuel");
   public static final ResourceLocation TEXTURE_BLOCK = new ResourceLocation(QuantumWorld.MODID, "textures/gui/energy_generation_gui.png");
    public static final ResourceLocation TEXTURE_ICON = new ResourceLocation(QuantumWorld.MODID, "textures/gui/energy_flame_gui.png");

    public static final ResourceLocation TEXTURE_ICON_NO_SHADOW = new ResourceLocation(QuantumWorld.MODID, "textures/gui/energy_flame_no_shadow_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated energyBurningAnimation;

    public EnergyFuelRecipeCategory(IGuiHelper guiHelper) {
        //this.background = guiHelper.createDrawable(TEXTURE_BLOCK, 0, 0, 176, 85);
        //this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksRegistry.BASIC_ELECTRICITY_GENERATOR.get()));
        this.background = guiHelper.drawableBuilder(TEXTURE_BLOCK, 0, 0, 176, 50).setTextureSize(176, 50).build();
        this.icon = guiHelper.drawableBuilder(TEXTURE_ICON, 0, 0, 14, 14).setTextureSize(14, 14).build();
        this.energyBurningAnimation = guiHelper.drawableBuilder(TEXTURE_ICON_NO_SHADOW, 0, 0, 13, 13).setTextureSize(13, 13)
                .buildAnimated(100, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<EnergyFuelRecipe> getRecipeType() {
        return JEIQuantumWorldPlugin.ENERGY_FUEL_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Generating EM");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(EnergyFuelRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        Minecraft.getInstance().font.draw(stack, "Drawing stuff", 75, 21, 0);

        energyBurningAnimation.draw(stack, 41, 18);
    }



    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EnergyFuelRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 17).addIngredients(recipe.getIngredients().get(0));
        //builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 50).addItemStack(recipe.getResultItem());
    }
}
