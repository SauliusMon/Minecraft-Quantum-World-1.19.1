package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.BlocksRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, QuantumWorld.MODID);

    public static final RegistryObject<BlockEntityType<BasicElectricityHolderEntity>> BASIC_ELECTRICITY_HOLDER_ENTITY =
            BLOCK_ENTITIES.register("basic_electricity_holder_entity", () -> BlockEntityType.Builder.of
                    (BasicElectricityHolderEntity::new, BlocksRegistry.BASIC_ELECTRICITY_HOLDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<BasicElectricityGeneratorEntity>> BASIC_ELECTRICITY_GENERATOR_ENTITY =
            BLOCK_ENTITIES.register("basic_electricity_generator_entity", () -> BlockEntityType.Builder.of
                    (BasicElectricityGeneratorEntity::new, BlocksRegistry.BASIC_ELECTRICITY_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<CopperCableEntity>> COPPER_CABLE_ENTITY =
            BLOCK_ENTITIES.register("copper_cable_entity", () -> BlockEntityType.Builder.of
                    (CopperCableEntity::new, BlocksRegistry.COPPER_CABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
