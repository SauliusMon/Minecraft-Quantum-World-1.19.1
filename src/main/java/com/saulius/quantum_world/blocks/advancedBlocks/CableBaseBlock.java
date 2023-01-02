package com.saulius.quantum_world.blocks.advancedBlocks;

import com.saulius.quantum_world.blocks.blocksTile.CableBaseEntity;
import com.saulius.quantum_world.blocks.blocksTile.CopperCableEntity;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.tools.CableShape;
import com.saulius.quantum_world.tools.EnergyUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class CableBaseBlock extends BaseEntityBlock {

    public CableBaseBlock(Properties properties) { super(properties); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONN_UP, CONN_DOWN, CONN_NORTH, CONN_SOUTH, CONN_EAST, CONN_WEST);
    }
    private final VoxelShape CABLE_SHAPE_CENTER = Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity instanceof CopperCableEntity) {
            return ((CopperCableEntity) blockEntity).getShape();
        }
        return CABLE_SHAPE_CENTER;
    }

    @Override
    public RenderShape getRenderShape (BlockState blockState) {
        return RenderShape.MODEL;
    }


    // Use, Placed and Destroyed are all in both server / client side
    @Override
    public InteractionResult use (BlockState blockState, Level level, BlockPos blockPos, Player player,
                                  InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).is(ItemsRegistry.IRON_WRENCH.get())) {
            CableBaseEntity cableEntity = (CableBaseEntity) level.getBlockEntity(blockPos);
            //cableEntity.updateBlockShapeOnWrenchHit(level, blockPos, blockState, blockHitResult);
            CableShape.updateBlockShape(cableEntity, level, blockPos, blockState, blockHitResult);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        ArrayList<EnergyUtils.BlockEntityWithOriginDirection> nearbyBlockEntities = new EnergyUtils().getNearbyEnergyBlockEntities(level, blockPos);
        if (!nearbyBlockEntities.isEmpty()) {
            for (EnergyUtils.BlockEntityWithOriginDirection blockEntityWithOriginDirection : nearbyBlockEntities) {
                BlockEntity neighboringEnergyEntity = blockEntityWithOriginDirection.getBaseEntity();
                Direction originDirection = blockEntityWithOriginDirection.getOriginDirection();

                if (EnergyUtils.isCableEntity(neighboringEnergyEntity)) {
                    BlockState neighborBlockState = neighboringEnergyEntity.getBlockState();
                    CableBaseEntity callerCableEntity = (CableBaseEntity) level.getBlockEntity(blockPos);

                    if (!neighborBlockState.getValue(EnergyUtils.getEnumPropertyFromDirection(originDirection.getOpposite())).isConnected()) {
                        CableShape.addShape(callerCableEntity, originDirection, level, blockPos, callerCableEntity.getBlockState());
                        CableShape.addShape((CableBaseEntity) neighboringEnergyEntity, originDirection.getOpposite(), level, blockPos.relative(originDirection), neighborBlockState);
                        /*
                        Note:
                        If passed blockState is used in method for main block, main entity blockState doesn't change in some cases
                        Need to use callerCableEntity.getBlockState() method
                        Maybe blockState passed here has some kind of immutability?
                        */
                    }
                }
            }
        }
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockState, Level level, BlockPos blockPos, Player player, boolean willHarvest, FluidState fluidState) {
        ArrayList<EnergyUtils.BlockEntityWithOriginDirection> nearbyBlockEntities = new EnergyUtils().getNearbyEnergyBlockEntities(level, blockPos);
        if (!nearbyBlockEntities.isEmpty()) {
            for (EnergyUtils.BlockEntityWithOriginDirection blockEntityWithOriginDirection : nearbyBlockEntities) {
                BlockEntity neighboringEnergyEntity = blockEntityWithOriginDirection.getBaseEntity();
                Direction originDirection = blockEntityWithOriginDirection.getOriginDirection();

                if (EnergyUtils.isCableEntity(neighboringEnergyEntity)) {
                    BlockState neighborBlockState = neighboringEnergyEntity.getBlockState();
                    if (neighborBlockState.getValue(EnergyUtils.getEnumPropertyFromDirection(originDirection.getOpposite())).isConnected()) {
                        CableShape.removeShape((CableBaseEntity) neighboringEnergyEntity, originDirection.getOpposite(), level, blockPos.relative(originDirection), neighborBlockState);
                    }
                }
            }
        }
        return super.onDestroyedByPlayer(blockState, level, blockPos, player, willHarvest, fluidState);
    }

    //If enum variable isn't static, game crashes
    public static final EnumProperty<CableBaseBlock.Connection> CONN_UP = EnumProperty.create("conn_up", CableBaseBlock.Connection.class);
    public static final EnumProperty<CableBaseBlock.Connection> CONN_DOWN = EnumProperty.create("conn_down", CableBaseBlock.Connection.class);
    public static final EnumProperty<CableBaseBlock.Connection> CONN_NORTH = EnumProperty.create("conn_north", CableBaseBlock.Connection.class);
    public static final EnumProperty<CableBaseBlock.Connection> CONN_SOUTH = EnumProperty.create("conn_south", CableBaseBlock.Connection.class);
    public static final EnumProperty<CableBaseBlock.Connection> CONN_EAST = EnumProperty.create("conn_east", CableBaseBlock.Connection.class);
    public static final EnumProperty<CableBaseBlock.Connection> CONN_WEST = EnumProperty.create("conn_west", CableBaseBlock.Connection.class);

    public enum Connection implements StringRepresentable {
        NOT_CONNECTED("not_connected"),
        CONNECTED("connected");

        private final String name;

        Connection(String name) {
            this.name = name;
        }

        public Boolean isConnected() { return name.equals("connected"); }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
