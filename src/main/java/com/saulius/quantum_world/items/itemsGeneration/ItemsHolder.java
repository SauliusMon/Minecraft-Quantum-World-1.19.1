package com.saulius.quantum_world.items.itemsGeneration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemsHolder {

    public static final TagKey<Item> COSMIC_INGOTS = TagKey.create(Registry.ITEM_REGISTRY,new ResourceLocation("cosmic_ingot"));
    public static final TagKey<Item> ENERGIUM_INGOTS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("energium_ingot"));


}
