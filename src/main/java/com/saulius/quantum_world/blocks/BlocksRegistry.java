package com.saulius.quantum_world.blocks;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.advancedBlocks.*;
import com.saulius.quantum_world.blocks.advancedBlocks.cables.AntimatterCable;
import com.saulius.quantum_world.blocks.advancedBlocks.cables.CopperCableBlock;
import com.saulius.quantum_world.blocks.advancedBlocks.cables.FiberOpticCable;
import com.saulius.quantum_world.blocks.advancedBlocks.cables.SilverCableBlock;
import com.saulius.quantum_world.blocks.blocksGeneration.BlockPropertiesGeneration;
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

    private static final RegistryObject<Block> COSMIC_ORE = registerBlock("cosmic_ore",
            () -> new Block(BlockPropertiesGeneration.cosmicProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    private static final RegistryObject<Block> COSMIC_BLOCK = registerBlock("cosmic_block",
            () -> new Block(BlockPropertiesGeneration.cosmicProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    private static final RegistryObject<Block> ENERGIUM_ORE = registerBlock("energium_ore",
            () -> new Block(BlockPropertiesGeneration.energiumProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    private static final RegistryObject<Block> ENERGIUM_BLOCK = registerBlock("energium_block",
            () -> new Block(BlockPropertiesGeneration.energiumProperties), ModCreativeModeTabs.TAB_QUANTUM_WORLD);
    public static final RegistryObject<Block> BASIC_ELECTRICITY_HOLDER = registerBlock("basic_electricity_holder",
            () -> new BasicElectricityHolderBlock(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> BASIC_ELECTRICITY_GENERATOR = registerBlock("basic_electricity_generator",
            () -> new BasicElectricityGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> COPPER_CABLE = registerBlock("copper_cable",
            () -> new CopperCableBlock(BlockBehaviour.Properties.of(Material.WOOL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> SILVER_CABLE = registerBlock("silver_cable",
            () -> new SilverCableBlock(BlockBehaviour.Properties.of(Material.WOOL)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> FIBER_OPTIC_CABLE = registerBlock("fiber_optic_cable",
            () -> new FiberOpticCable(BlockBehaviour.Properties.of(Material.GLASS)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);

    public static final RegistryObject<Block> ANTIMATTER_CABLE = registerBlock("antimatter_cable",
            () -> new AntimatterCable(BlockBehaviour.Properties.of(Material.GLASS)), ModCreativeModeTabs.TAB_QUANTUM_WORLD);


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
