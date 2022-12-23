package com.saulius.quantum_world.items.itemsGeneration;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public enum ItemTierGeneration implements Tier {
    COSMIC(3000, 8.5F, 4.0F, 4, 10, Ingredient.of(ItemsHolder.COSMIC_INGOTS)),
    ENERGIUM (5,15.0F,7.0F,3,22, Ingredient.of(ItemsHolder.ENERGIUM_INGOTS));

    private final int durability;
    private final float speed;
    private final float attackDamageBonus;
    private final int level;
    private final int enchantmentLevel;
    private final Ingredient repairIngredient;

    ItemTierGeneration(int durability, float speed, float attackDamageBonus, int level, int enchantmentLevel, Ingredient repairIngredient) {
        this.durability = durability;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.level = level;
        this.enchantmentLevel = enchantmentLevel;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() { return this.durability; }
    @Override
    public float getSpeed() { return this.speed; }
    @Override
    public float getAttackDamageBonus() { return this.attackDamageBonus; }
    @Override
    public int getLevel() { return this.level; }
    @Override
    public int getEnchantmentValue() { return this.enchantmentLevel; }
    @Override
    public @NotNull Ingredient getRepairIngredient() { return this.repairIngredient; }

}