package com.saulius.quantum_world.blocks.advancedBlocks;

import com.saulius.quantum_world.blocks.blocksTile.BasicElectricityGeneratorEntity;
import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BasicElectricityGeneratorBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public BasicElectricityGeneratorBlock(Properties properties) { super(properties); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BasicElectricityGeneratorEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape (BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use (BlockState blockState, Level level, BlockPos blockPos, Player player,
                                  InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof BasicElectricityGeneratorEntity) {
                NetworkHooks.openScreen(((ServerPlayer) player), (MenuProvider) blockEntity, blockPos);
            }                                                             //MenuProvider
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> tBlockEntityType) {
        return createTickerHelper(tBlockEntityType, BlockEntities.BASIC_ELECTRICITY_GENERATOR_ENTITY.get(),
                BasicElectricityGeneratorEntity::tick);
    }
}
