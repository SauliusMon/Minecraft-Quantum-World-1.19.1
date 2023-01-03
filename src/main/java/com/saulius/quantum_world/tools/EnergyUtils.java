package com.saulius.quantum_world.tools;

import com.saulius.quantum_world.blocks.advancedBlocks.CableBaseBlock;
import com.saulius.quantum_world.blocks.blocksTile.BasicElectricityHolderEntity;
import com.saulius.quantum_world.blocks.blocksTile.CableBaseEntity;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.ArrayList;
import java.util.Collections;

import static com.saulius.quantum_world.blocks.advancedBlocks.CableBaseBlock.*;


public class EnergyUtils {

    public static boolean canReceiveEnergy(BlockEntity blockEntity, Direction directionOfSender) {
        if (isCableEntity(blockEntity)) {
            if (isCableConnected((CableBaseEntity) blockEntity, directionOfSender)) {
                return true;
            }
        }
        else if (isReceiverEntity(blockEntity)) {
            return true;
        }
        return false;
    }

    public static boolean canSendToNeighbor (Level level, BlockPos blockPos, Direction directionToSend) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos.relative(directionToSend));

        if (isCableEntity(blockEntity) && isCableConnected((CableBaseEntity) blockEntity, directionToSend.getOpposite())) {
            return true;
        }
        else if (isReceiverEntity(blockEntity)) {
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

    public static boolean isReceiverEntity(BlockEntity blockEntity) {
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
                energyBlockEntities.add(new BlockEntityWithOriginDirection(blockEntity, direction));
            }
        }
        return energyBlockEntities;
    }

    public static ArrayList<AbstractModEntity> getNearbyEnergyReceivers(Level level, BlockPos blockPos) {
        ArrayList<AbstractModEntity> energyBlockEntities = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity neighboringBlockEntity = level.getBlockEntity(blockPos.relative(direction));            // Neighboring block
            CableBaseEntity mainBlockEntity = (CableBaseEntity) level.getBlockEntity(blockPos);                 // Main block
            if (isCableConnected(mainBlockEntity, direction)) {
                if (canReceiveEnergy(neighboringBlockEntity, direction.getOpposite())) {
                    energyBlockEntities.add((AbstractModEntity) neighboringBlockEntity);
                }
            }
        }
        /* This is used to set which blockEntities have priority for energy - for example, block which holds electricity has a functionality of holding it,
           while cables functionality is to transport it between blocks.
           It also sorts cables by energy amount - if cable has more energy, it has higher possibility to be near a block which generates electricity.
           Further implementations might have path finding algorithm through BlockStates conditions.
           Not an ideal way of sending energy - becomes too slow at a bigger distances. Needs further attention.
           In loops, speed is not a problem, but if sent energy is 1 it becomes stuck.
        */
        Collections.sort(energyBlockEntities, (o1, o2) -> {
            if (isReceiverEntity(o1.getEntity())) {
                return -1;
            }
            if (isReceiverEntity(o2.getEntity())) {
                return 1;
            }
            if (o1.getEnergyStorage().getEnergyStored() == o2.getEnergyStorage().getEnergyStored()) {
                return 0;
            } else if (o1.getEnergyStorage().getEnergyStored() < o2.getEnergyStorage().getEnergyStored()) {
                return -1;
            } else {
                return 1;
            }
        });
        return energyBlockEntities;
    }

    //Doesn't work when sentEnergy is 1
    public static void exchangeEnergy (AbstractModEnergy senderEntity, AbstractModEnergy receivingEntity, int sentEnergyPerTick) {
        if (senderEntity.getEnergyStorage().getMaxSend() >= sentEnergyPerTick && receivingEntity.getEnergyStorage().getMaxReceiving() >= sentEnergyPerTick) {
            senderEntity.getEnergyStorage().extractEnergy(receivingEntity.getEnergyStorage().receiveEnergy(sentEnergyPerTick, false),false);
        }
        else {
            senderEntity.getEnergyStorage().extractEnergy(receivingEntity.getEnergyStorage().receiveEnergy(
                    Math.min(senderEntity.getEnergyStorage().getMaxSend(), receivingEntity.getEnergyStorage().getMaxReceiving()), false), false);
        }
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

    // Might need later?
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
