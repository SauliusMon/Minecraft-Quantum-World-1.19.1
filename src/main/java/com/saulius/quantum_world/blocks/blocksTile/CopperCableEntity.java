package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.tools.CableShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CopperCableEntity extends CableBaseEntity {

    public CopperCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.COPPER_CABLE_ENTITY.get(), blockPos, blockState, 30, 5);
    }
}
