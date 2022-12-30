package com.saulius.quantum_world.blocks.advancedBlocks;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import com.saulius.quantum_world.blocks.blocksTile.CopperCableEntity;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.tools.CableShape;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public class CopperCableBlock extends BaseEntityBlock {
    private final CableShape cableShape = new CableShape();

    public CopperCableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONN_UP, CONN_DOWN, CONN_NORTH, CONN_SOUTH, CONN_EAST, CONN_WEST);
    }

    private VoxelShape currentCableShape = Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity instanceof CopperCableEntity) {
            return ((CopperCableEntity) blockEntity).getShape();
        }
        return currentCableShape;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CopperCableEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape (BlockState blockState) {
        return RenderShape.MODEL;
    }

    static int a = 0;

    @Override
    public InteractionResult use (BlockState blockState, Level level, BlockPos blockPos, Player player,
                                  InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            if (player.getItemInHand(interactionHand).is(ItemsRegistry.IRON_WRENCH.get())) {
                currentCableShape = cableShape.updateBlockShape(currentCableShape, level, blockPos, blockState, blockHitResult);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> tBlockEntityType) {
        return createTickerHelper(tBlockEntityType, BlockEntities.COPPER_CABLE_ENTITY.get(),
                CopperCableEntity::tick);
    }

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        super.initializeClient(consumer);
    }

    //If enum variable isn't static, game crashes
    public static final EnumProperty<Connection> CONN_UP = EnumProperty.create("conn_up", Connection.class);
    public static final EnumProperty<Connection> CONN_DOWN = EnumProperty.create("conn_down", Connection.class);
    public static final EnumProperty<Connection> CONN_NORTH = EnumProperty.create("conn_north", Connection.class);
    public static final EnumProperty<Connection> CONN_SOUTH = EnumProperty.create("conn_south", Connection.class);
    public static final EnumProperty<Connection> CONN_EAST = EnumProperty.create("conn_east", Connection.class);
    public static final EnumProperty<Connection> CONN_WEST = EnumProperty.create("conn_west", Connection.class);

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
