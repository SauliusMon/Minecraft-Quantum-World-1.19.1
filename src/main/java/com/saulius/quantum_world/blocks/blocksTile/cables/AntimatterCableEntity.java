package com.saulius.quantum_world.blocks.blocksTile.cables;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AntimatterCableEntity extends CableBaseEntity {

    public AntimatterCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.ANTIMATTER_CABLE_ENTITY.get(), blockPos, blockState, 960, 320, 160);
    }
}
