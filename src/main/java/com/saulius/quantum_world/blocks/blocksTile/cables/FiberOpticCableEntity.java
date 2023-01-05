package com.saulius.quantum_world.blocks.blocksTile.cables;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FiberOpticCableEntity extends CableBaseEntity {

    public FiberOpticCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.FIBER_OPTIC_CABLE_ENTITY.get(), blockPos, blockState, 240, 80, 40);
    }
}
