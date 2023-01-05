package com.saulius.quantum_world.blocks.advancedBlocks.cables;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import com.saulius.quantum_world.blocks.blocksTile.cables.SilverCableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SilverCableBlock extends CableBaseBlock {

    public SilverCableBlock(Properties properties) { super(properties); }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SilverCableEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> tBlockEntityType) {
        return createTickerHelper(tBlockEntityType, BlockEntities.SILVER_CABLE_ENTITY.get(),
                SilverCableEntity::tick);
    }
}
