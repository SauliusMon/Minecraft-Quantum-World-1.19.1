package com.saulius.quantum_world.tools;

import com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock;
import com.saulius.quantum_world.blocks.blocksTile.CopperCableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock.*;

public class CableShape {

    private static final VoxelShape CABLE_UP = Block.box(6.0D, 10.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape CABLE_DOWN = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    private static final VoxelShape CABLE_NORTH = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 6.0D);
    private static final VoxelShape CABLE_SOUTH = Block.box(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 16.0D);
    private static final VoxelShape CABLE_EAST = Block.box(10.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape CABLE_WEST = Block.box(0.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D);


    public VoxelShape updateBlockShape (VoxelShape cableShape, Level level, BlockPos blockPos, BlockState blockState, BlockHitResult blockHitResult) {

        DirectionAndBool directionAndBool = calcSideOnWrenchUse(blockPos, blockHitResult);
        Direction direction = directionAndBool.getDirection();
        Boolean shouldAdd = !directionAndBool.getBool();

        switch (direction) {
            case UP -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_UP, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_UP, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_UP, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_UP);
            }
            case DOWN -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_DOWN, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_DOWN, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_DOWN, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_DOWN);
            }
            case EAST -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_EAST, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_EAST, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_EAST, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_EAST);
            }
            case WEST -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_WEST, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_WEST, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_WEST, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_WEST);
            }
            case NORTH -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_NORTH, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_NORTH, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_NORTH, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_NORTH);
            }
            case SOUTH -> {
                if (shouldAdd) {
                    level.setBlock(blockPos, blockState.setValue(CONN_SOUTH, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_SOUTH, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_SOUTH, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_SOUTH);
            }
            default -> {
                return cableShape;
            }
        }
    }
    private static final double TEXTURE_SIZE = 16, CABLE_SIDE_LENGTH = 6, CABLE_CENTER_LENGTH = 4;

    public DirectionAndBool calcSideOnWrenchUse(BlockPos pos, BlockHitResult hit) {
        double whichSide = 1 / TEXTURE_SIZE * CABLE_SIDE_LENGTH;
        double center = 1 / TEXTURE_SIZE * CABLE_CENTER_LENGTH;
        Vec3 hitVec = hit.getLocation();

        Direction direction;
        boolean boolCenter;

        if (hitVec.y > pos.getY() + whichSide + center) {
            direction = Direction.UP;
            boolCenter = false;
        } else if (hitVec.y < pos.getY() + whichSide) {
            direction = Direction.DOWN;
            boolCenter = false;
        }  else if (hitVec.z > pos.getZ() + whichSide + center) {
            direction = Direction.SOUTH;
            boolCenter = false;
        } else if (hitVec.z < pos.getZ() + whichSide) {
            direction = Direction.NORTH;
            boolCenter = false;
        } else if (hitVec.x > pos.getX() + whichSide + center) {
            direction = Direction.EAST;
            boolCenter = false;
        } else if (hitVec.x < pos.getX() + whichSide) {
            direction = Direction.WEST;
            boolCenter = false;
        } else {
            direction = hit.getDirection();
            boolCenter = true;
        }
        return new DirectionAndBool(direction, boolCenter);
    }

    private class DirectionAndBool {
        Direction direction;
        Boolean bool;
       private DirectionAndBool (Direction direction, Boolean bool) {
           this.direction = direction;
           this.bool = bool;
       }
        public Direction getDirection() {
            return direction;
        }
        public Boolean getBool() {
            return bool;
        }
    }

    public VoxelShape onLoadCableShape(VoxelShape cableShape, Level level, BlockPos blockPos, BlockState blockState) {
        if(blockState.getValue(CONN_UP).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_UP);
        }
        if(blockState.getValue(CONN_DOWN).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_DOWN);
        }
        if(blockState.getValue(CONN_EAST).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_EAST);
        }
        if(blockState.getValue(CONN_WEST).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_WEST);
        }
        if(blockState.getValue(CONN_NORTH).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_NORTH);
        }
        if(blockState.getValue(CONN_SOUTH).isConnected()) {
            cableShape = Shapes.or(cableShape, CABLE_SOUTH);
        }
        return cableShape;
    }

    public static void addShape (CopperCableEntity cableEntity, Direction direction, Level level, BlockPos blockPos, BlockState blockState) {
        level.setBlock(blockPos, blockState.setValue(EnergyUtils.getEnumPropertyFromDirection(direction), CopperCableBlock.Connection.CONNECTED), 3);
        cableEntity.setChanged();

        System.out.println(blockState.getValue(EnergyUtils.getEnumPropertyFromDirection(direction))  + " " + direction + " " + blockPos + " ");

        cableEntity.setShape(Shapes.or(cableEntity.getShape(), getShapeFromDirection(direction)));
        cableEntity.setChanged();
    }

    public static void removeShape (CopperCableEntity cableEntity, Direction direction, Level level, BlockPos blockPos, BlockState blockState) {
        level.setBlock(blockPos, blockState.setValue(EnergyUtils.getEnumPropertyFromDirection(direction), Connection.NOT_CONNECTED), 3);
        cableEntity.setShape(Shapes.join(cableEntity.getShape(), getShapeFromDirection(direction), BooleanOp.ONLY_FIRST));
    }

    private static VoxelShape getShapeFromDirection (Direction direction) {
        switch (direction) {
            case UP -> { return CABLE_UP; }
            case DOWN -> { return CABLE_DOWN; }
            case EAST -> { return CABLE_EAST; }
            case WEST -> { return CABLE_WEST; }
            case NORTH -> { return CABLE_NORTH; }
            case SOUTH -> { return CABLE_SOUTH; }
        }
        return CABLE_UP;
    }
}