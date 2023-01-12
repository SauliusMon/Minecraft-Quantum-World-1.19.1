package com.saulius.quantum_world.blocks.blocksGeneration;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockPropertiesGeneration {


    public static final BlockBehaviour.Properties cosmicProperties = returnProperties(Material.METAL,9.0F);
    public static final BlockBehaviour.Properties energiumProperties = returnProperties(Material.STONE, 2.5F, 120.0F);

    private static BlockBehaviour.Properties returnProperties (Material material, Float... strengthArr) {
        Float strength;
        if (strengthArr.length == 1) strength = strengthArr[0];
        else strength = strengthArr[1];

        return BlockBehaviour.Properties.of(material).requiresCorrectToolForDrops()
                .strength(strengthArr[0], strength);
    }
}
