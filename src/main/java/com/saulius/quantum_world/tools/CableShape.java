package com.saulius.quantum_world.tools;

import com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock.*;

public class CableShape {

    private VoxelShape cableShape = Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);


    public VoxelShape getCableShape() {return cableShape;}

    public void setCableShape(VoxelShape cableShape) {this.cableShape = cableShape;}

    private static final VoxelShape CABLE_UP = Block.box(6.0D, 10.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape CABLE_DOWN = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    private static final VoxelShape CABLE_NORTH = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 6.0D);
    private static final VoxelShape CABLE_SOUTH = Block.box(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 16.0D);
    private static final VoxelShape CABLE_EAST = Block.box(10.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape CABLE_WEST = Block.box(0.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D);


    public VoxelShape updateBlockShape (Direction direction, Level level, BlockPos blockPos, BlockState blockState) {
        switch (direction) {
            case UP -> {
                if (blockState.getValue(CONN_UP).isConnected()) {
                    level.setBlock(blockPos, blockState.setValue(CONN_UP, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_UP, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_UP, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_UP);
            }
            case DOWN -> {
                if (blockState.getValue(CONN_DOWN).isConnected()) {
                    level.setBlock(blockPos, blockState.setValue(CONN_DOWN, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_DOWN, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_DOWN, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_DOWN);
            }
            case EAST -> {
                if (blockState.getValue(CONN_EAST).isConnected()) {
                    level.setBlock(blockPos, blockState.setValue(CONN_EAST, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_EAST, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_EAST, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_EAST);
            }
            case WEST -> {
                if (blockState.getValue(CONN_WEST).isConnected()) {
                    level.setBlock(blockPos, blockState.setValue(CONN_WEST, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_WEST, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_WEST, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_WEST);
            }
            case NORTH -> {
                if (blockState.getValue(CONN_NORTH).isConnected()) {
                    level.setBlock(blockPos, blockState.setValue(CONN_NORTH, Connection.NOT_CONNECTED), 3);
                    return Shapes.join(cableShape, CABLE_NORTH, BooleanOp.ONLY_FIRST);
                }
                level.setBlock(blockPos, blockState.setValue(CONN_NORTH, CopperCableBlock.Connection.CONNECTED), 3);
                return Shapes.or(cableShape, CABLE_NORTH);
            }
            case SOUTH -> {
                if (blockState.getValue(CONN_SOUTH).isConnected()) {
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

    public static Direction calcSideConnOnWrench(BlockPos pos, BlockHitResult hit) {
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
        } else if (hitVec.z < pos.getZ() + whichSide) {
            direction = Direction.NORTH;
            boolCenter = false;
        } else if (hitVec.z > pos.getZ() + whichSide + center) {
            direction = Direction.SOUTH;
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
        return Direction.UP;
    }
}