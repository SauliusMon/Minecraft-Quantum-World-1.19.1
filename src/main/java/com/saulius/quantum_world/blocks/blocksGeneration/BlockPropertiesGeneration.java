package com.saulius.quantum_world.blocks.blocksGeneration;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockPropertiesGeneration {


    public static final BlockBehaviour.Properties cosmicProperties = returnProperties(Material.STONE,50.0F);
    public static final BlockBehaviour.Properties energiumProperties = returnProperties(Material.METAL, 40.0F, 1200.0F);

    private static BlockBehaviour.Properties returnProperties (Material material, Float... strengthArr) {
        Float strength;
        if (strengthArr.length == 1) strength = strengthArr[0];
        else strength = strengthArr[1];

        return BlockBehaviour.Properties.of(material).requiresCorrectToolForDrops()
                .strength(strengthArr[0], strength);
    }
}
