package com.saulius.quantum_world.items.itemsRegistry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class ModCreativeModeTabs {

    public static final CreativeModeTab TAB_QUANTUM_WORLD = new CreativeModeTab("tab_quantum_world") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemsRegistry.COSMIC_INGOT.get());
        }
    };

}