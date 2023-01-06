package com.saulius.quantum_world.recipes;

import com.google.gson.JsonObject;
import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BasicElectricityGeneratorRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation ID;
    private final ItemStack OUTPUT;
    private final NonNullList<Ingredient> RECIPE_ITEMS;

    public BasicElectricityGeneratorRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        ID = id;
        this.OUTPUT = output;
        this.RECIPE_ITEMS = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return OUTPUT;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return OUTPUT.copy();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    public static class Type implements RecipeType<BasicElectricityGeneratorRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "electricity_generator";
    }

    public static class Serializer implements RecipeSerializer<BasicElectricityGeneratorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(QuantumWorld.MODID, "electricity_generator");

        @Override
        public BasicElectricityGeneratorRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return null;
        }

        @Override
        public @Nullable BasicElectricityGeneratorRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, BasicElectricityGeneratorRecipe basicElectricityGeneratorRecipe) {

        }
    }
}
