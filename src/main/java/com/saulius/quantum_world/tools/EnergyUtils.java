package com.saulius.quantum_world.tools;

import com.saulius.quantum_world.blocks.advancedBlocks.CableBaseBlock;
import com.saulius.quantum_world.blocks.blocksTile.BasicElectricityHolderEntity;
import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import com.saulius.quantum_world.blocks.blocksTile.CableBaseEntity;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.ArrayList;

import static com.saulius.quantum_world.blocks.advancedBlocks.CableBaseBlock.*;


public class EnergyUtils {

    public static boolean canRecieveEnergy (BlockEntity blockEntity, Direction directionOfSender) {
        if (isCableEntity(blockEntity)) {
            if (isCableConnected((CableBaseEntity) blockEntity, directionOfSender)) {
                return true;
            }
        }
        else if (isRecieverEntity(blockEntity)) {
            return true;
        }
        return false;
    }

    public static boolean canSendToNeighbor (Level level, BlockPos blockPos, Direction directionToSend) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos.relative(directionToSend));
        if (isCableEntity(blockEntity) && isCableConnected((CableBaseEntity) blockEntity, directionToSend.getOpposite())) {
            return true;
        }
        else if (isRecieverEntity(blockEntity)) {
            return true;
        }
        return false;
    }

    public static boolean isCableEntity(BlockEntity blockEntity) {
        if (blockEntity instanceof CableBaseEntity) {
            return  true;
        }
        return false;
    }

    public static boolean isEnergyEntity(BlockEntity blockEntity) {
        if (blockEntity instanceof AbstractModEnergy) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isRecieverEntity (BlockEntity blockEntity) {
        if (blockEntity instanceof BasicElectricityHolderEntity) {
            return true;
        }
        return false;
    }

    private static boolean isCableConnected (CableBaseEntity cableEntity, Direction directionOfSend) {
        return getEnumFromDirection(cableEntity.getBlockState(), directionOfSend).isConnected();
    }

    public ArrayList<BlockEntityWithOriginDirection> getNearbyEnergyBlockEntities(Level level, BlockPos blockPos) {
        ArrayList<BlockEntityWithOriginDirection> energyBlockEntities = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos.relative(direction));
            if (isCableEntity(blockEntity)) {
                energyBlockEntities.add(new BlockEntityWithOriginDirection((CableBaseEntity) blockEntity, direction));
            }
        }
        return energyBlockEntities;
    }

    public ArrayList<AbstractModEnergy> getNearbyEnergyReceivers(Level level, BlockPos blockPos) {
        ArrayList<AbstractModEnergy> energyBlockEntities = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity neighboringBlockEntity = level.getBlockEntity(blockPos.relative(direction));
            CableBaseEntity mainBlockEntity = (CableBaseEntity) level.getBlockEntity(blockPos);
            if (isCableConnected(mainBlockEntity, direction)) {
                if (canRecieveEnergy(neighboringBlockEntity, direction.getOpposite())) {
                    energyBlockEntities.add((AbstractModEnergy) neighboringBlockEntity);
                }
            }
        }
        return energyBlockEntities;
    }

    // WORKS ONLY WITH CABLE ENTITIES!!
    public int sendEnergyToNearbyEntities (Level level, BlockPos blockPos, int energyToSend, int maxSendAmount) {
        int energySentSum = 0;
        int energySentPerConnection;
        ArrayList<AbstractModEnergy> energyBlockEntities = getNearbyEnergyReceivers(level, blockPos);
        if (!energyBlockEntities.isEmpty()) {
            energySentPerConnection = Math.min(energyToSend / energyBlockEntities.size(), maxSendAmount);
            for (AbstractModEnergy blockEntity : energyBlockEntities) {
                energySentSum += blockEntity.getEnergyStorage().receiveEnergy(energySentPerConnection, false);
            }
        }
        return energySentSum;
    }

    public class BlockEntityWithOriginDirection {
        private BlockEntity blockEntity;
        private Direction originDirection;

        public BlockEntityWithOriginDirection (BlockEntity blockEntity, Direction originDirection) {
            this.blockEntity = blockEntity;
            this.originDirection = originDirection;
        }

        public BlockEntity getBaseEntity() { return blockEntity; }

        public Direction getOriginDirection() { return originDirection; }
    }

    public static CableBaseBlock.Connection getEnumFromDirection (BlockState blockState, Direction direction) {
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

    public static EnumProperty<CableBaseBlock.Connection> getEnumPropertyFromDirection (Direction direction) {
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

    // Maybe will need?
    private Direction neighborDirection (BlockPos mainBlockPos, BlockPos neighborBlockPos) {
        if (mainBlockPos.getY() < neighborBlockPos.getY()) {
            return Direction.UP;
        }
        else if (mainBlockPos.getY() > neighborBlockPos.getY()) {
            return Direction.DOWN;
        }
        else if (mainBlockPos.getZ() < neighborBlockPos.getZ()) {
            return Direction.SOUTH;
        }
        else if (mainBlockPos.getZ() > neighborBlockPos.getZ()) {
            return Direction.NORTH;
        }
        else if (mainBlockPos.getX() < neighborBlockPos.getX()) {
            return Direction.EAST;
        }
        else {
            return Direction.WEST;
        }
    }
}
