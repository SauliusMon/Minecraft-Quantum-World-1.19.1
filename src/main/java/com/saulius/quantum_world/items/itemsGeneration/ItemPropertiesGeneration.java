package com.saulius.quantum_world.items.itemsGeneration;

import com.saulius.quantum_world.items.itemsRegistry.ModCreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ItemPropertiesGeneration  {

    public static final Item.Properties toolProperties = returnProperties(1, ModCreativeModeTabs.TAB_QUANTUM_WORLD);
    public static final Item.Properties itemProperties = returnProperties(64, ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    private static Item.Properties returnProperties (Integer stacksTo, CreativeModeTab creativeModeTab) {

        Item.Properties properties = new Item.Properties();

        properties.stacksTo(stacksTo);
        properties.tab(creativeModeTab);

        return properties;
    }
}
