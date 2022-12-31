package com.saulius.quantum_world.tools;

import com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock;
import com.saulius.quantum_world.blocks.blocksTile.CopperCableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock.*;

public class EnergyUtils {

    public static boolean canRecieveEnergy (BlockEntity blockEntity, Direction directionOfSender, BlockState blockState) {
        if (isCableEntity(blockEntity)) {
            if (isCableConnected((CopperCableEntity) blockEntity, directionOfSender)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCableEntity (BlockEntity blockEntity) {
        if (blockEntity instanceof CopperCableEntity) {
            return  true;
        }
        return false;
    }

    public static boolean isCableConnected (CopperCableEntity copperCableEntity, Direction directionOfSender) {
        return getEnumFromDirection(copperCableEntity.getBlockState(), directionOfSender).isConnected();
    }

    public ArrayList<BlockEntityWithOriginDirection> getNearbyEnergyBlockEntities(Level level, BlockPos blockPos) {
        ArrayList<BlockEntityWithOriginDirection> energyBlockEntities = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos.relative(direction));
            if (isCableEntity(blockEntity)) {
                energyBlockEntities.add(new BlockEntityWithOriginDirection((CopperCableEntity) blockEntity, direction));
            }
        }
        return energyBlockEntities;
    }

    public class BlockEntityWithOriginDirection {
        private CopperCableEntity copperCableEntity;
        private Direction originDirection;

        public BlockEntityWithOriginDirection (CopperCableEntity copperCableEntity, Direction originDirection) {
            this.copperCableEntity = copperCableEntity;
            this.originDirection = originDirection;
        }

        public CopperCableEntity getCopperCableEntity() { return copperCableEntity; }

        public Direction getOriginDirection() { return originDirection; }
    }

    public static CopperCableBlock.Connection getEnumFromDirection (BlockState blockState, Direction direction) {
        switch (direction) {
            case UP -> { return blockState.getValue(CONN_UP); }
            case DOWN -> { return blockState.getValue(CONN_DOWN); }
            case EAST -> { return blockState.getValue(CONN_EAST); }
            case WEST -> { return blockState.getValue(CONN_WEST); }
            case NORTH -> { return blockState.getValue(CONN_NORTH); }
            case SOUTH -> { return blockState.getValue(CONN_SOUTH); }
        }
        return Connection.NOT_CONNECTED;
    }

    public static EnumProperty<Connection> getEnumPropertyFromDirection (Direction direction) {
        switch (direction) {
            case UP -> { return CONN_UP; }
            case DOWN -> { return CONN_DOWN; }
            case EAST -> { return CONN_EAST; }
            case WEST -> { return CONN_WEST; }
            case NORTH -> { return CONN_NORTH; }
            case SOUTH -> { return CONN_SOUTH; }
        }
        return CONN_UP;
    }
}
