package com.saulius.quantum_world.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.x;

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
        if (level.isClientSide)
            return false;
        return RECIPE_ITEMS.get(0).test(simpleContainer.getItem(1));
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
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
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
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int x = 0; x < inputs.size(); x++) {
                inputs.set(x, Ingredient.fromJson(ingredients.get(x)));
            }
            return new BasicElectricityGeneratorRecipe(resourceLocation, output, inputs);
        }

        @Override
        public @Nullable BasicElectricityGeneratorRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int x = 0; x < inputs.size(); x++) {
                inputs.set(x, Ingredient.fromNetwork(friendlyByteBuf));
            }
            ItemStack output = friendlyByteBuf.readItem();
            return new BasicElectricityGeneratorRecipe(resourceLocation, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, BasicElectricityGeneratorRecipe basicElectricityGeneratorRecipe) {
            friendlyByteBuf.writeInt(basicElectricityGeneratorRecipe.getIngredients().size());

            for(Ingredient ingredient : basicElectricityGeneratorRecipe.getIngredients()) {
                ingredient.toNetwork(friendlyByteBuf);
            }
            friendlyByteBuf.writeItemStack(basicElectricityGeneratorRecipe.getResultItem(), false);
        }
    }
}
