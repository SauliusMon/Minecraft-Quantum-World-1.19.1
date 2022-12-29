package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.blocks.advancedBlocks.CopperCableBlock;
import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI.BasicElectricityGeneratorMenu;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.tools.FEEnergyImpl;
import com.saulius.quantum_world.tools.ProgressScaleObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CopperCableEntity extends BlockEntity {
    private LazyOptional<IEnergyStorage> lazyOptEnergyHandler = LazyOptional.empty();

    public CopperCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.COPPER_CABLE_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptEnergyHandler = LazyOptional.of(() -> blockEnergy);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyOptEnergyHandler.invalidate();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        blockEnergy.setEnergy(compoundTag.getInt("current_energy_amount"));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.putInt("current_energy_amount", blockEnergy.getEnergyStored());
        super.saveAdditional(compoundTag);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyOptEnergyHandler.cast();
        }
        return super.getCapability(cap, direction);
    }

    private final FEEnergyImpl blockEnergy = new FEEnergyImpl(100, 5) {
        @Override
        public void onEnergyChange() {
            setChanged();
        }
    };

    private static final int ENERGY_PER_TICK_FROM_ENERGIUM_INGOT = 5;
    private final ProgressScaleObject blockProgress = new ProgressScaleObject(100);

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, CopperCableEntity entity) {
        if (!level.isClientSide) {

        }
    }

    public IEnergyStorage getEnergyStorage() {
        return blockEnergy;
    }
}
