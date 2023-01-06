package com.saulius.quantum_world.blocks.blocksTile.cables;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SilverCableEntity extends CableBaseEntity {

    public SilverCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.SILVER_CABLE_ENTITY.get(), blockPos, blockState, 120, 40, 20);
    }
}
