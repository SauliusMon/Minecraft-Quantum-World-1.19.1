package com.saulius.quantum_world.blocks;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.advancedBlocks.BasicElectricityGeneratorBlock;
import com.saulius.quantum_world.blocks.advancedBlocks.BasicElectricityHolderBlock;
import com.saulius.quantum_world.blocks.blocksGeneration.BlockPropertiesGeneration;
import com.saulius.quantum_world.blocks.blocksTile.BasicElectricityGeneratorEntity;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.items.itemsRegistry.ModCreativeModeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumWorld.MODID);

    @SuppressWarnings("unused")
    private static final RegistryObject<Block> COSMIC_ORE = registerBlock("cosmic_ore",
            () -> new Block(BlockPropertiesGeneration.cosmicProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    @SuppressWarnings("unused")
    private static final RegistryObject<Block> COSMIC_BLOCK = registerBlock("cosmic_block",
            () -> new Block(BlockPropertiesGeneration.cosmicProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> BASIC_ELECTRICITY_HOLDER = registerBlock("basic_electricity_holder",
            () -> new BasicElectricityHolderBlock(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> BASIC_ELECTRICITY_GENERATOR = registerBlock("basic_electricity_generator",
            () -> new BasicElectricityGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);



    private static <T extends Block> RegistryObject<T> registerBlock (String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem (String name, RegistryObject<T> block, CreativeModeTab tab) {
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register (IEventBus iEventBus) { BLOCKS.register(iEventBus); }

}
