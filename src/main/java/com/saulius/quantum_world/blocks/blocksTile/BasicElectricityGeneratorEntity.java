package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI.BasicElectricityGeneratorMenu;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergyAndTick;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEntity;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.tools.EnergyUtils;
import com.saulius.quantum_world.tools.FEEnergyImpl;
import com.saulius.quantum_world.tools.ProgressScaleObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.saulius.quantum_world.blocks.advancedBlocks.BasicElectricityGeneratorBlock.FACING;

public class BasicElectricityGeneratorEntity extends BlockEntity implements MenuProvider, AbstractModEnergyAndTick, AbstractModEntity {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IEnergyStorage> lazyOptEnergyHandler = LazyOptional.empty();
    private LazyOptional<ProgressScaleObject> lazyOptBurnScale = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyOptItemHandler = LazyOptional.empty();

    public BasicElectricityGeneratorEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.BASIC_ELECTRICITY_GENERATOR_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("B.E.G.");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptItemHandler = LazyOptional.of(() -> itemStackHandler);
        lazyOptEnergyHandler = LazyOptional.of(() -> blockEnergy);
        lazyOptBurnScale = LazyOptional.of(() -> blockProgress);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyOptItemHandler.invalidate();
        lazyOptEnergyHandler.invalidate();
        lazyOptBurnScale.invalidate();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemStackHandler.deserializeNBT(compoundTag.getCompound("inventory"));
        blockEnergy.setEnergy(compoundTag.getInt("current_energy_amount"));
        blockProgress.setScale(compoundTag.getInt("current_scale_amount"));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("inventory", itemStackHandler.serializeNBT());
        compoundTag.putInt("current_energy_amount", blockEnergy.getEnergyStored());
        compoundTag.putInt("current_scale_amount", blockProgress.getScale());
        super.saveAdditional(compoundTag);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyOptItemHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyOptEnergyHandler.cast();
        }
        return super.getCapability(cap, direction);
    }

    private final FEEnergyImpl blockEnergy = new FEEnergyImpl(10000, MAX_ENERGY_SENT_PER_TICK) {
        @Override
        public void onEnergyChange() {
            setChanged();
        }
    };

    private static final int ENERGY_PER_TICK_FROM_ENERGIUM_INGOT = 10;
    private static final int MAX_ENERGY_SENT_PER_TICK = 20;
    private final ProgressScaleObject blockProgress = new ProgressScaleObject(100);


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BasicElectricityGeneratorEntity entity) {
        if (!level.isClientSide) {
            //Sending energy:
            if (entity.getEnergyStorage().getEnergyStored() > 0) {
                if (EnergyUtils.canSendToNeighbor(level, blockPos, blockState.getValue(FACING).getOpposite())) {
                    AbstractModEnergy neighboringBlockEntity = (AbstractModEnergy) level.getBlockEntity(
                            blockPos.relative(blockState.getValue(FACING).getOpposite()));
                    if (entity.getEnergyStorage().getEnergyStored() >= MAX_ENERGY_SENT_PER_TICK) {
                        entity.getEnergyStorage().extractEnergy(neighboringBlockEntity.getEnergyStorage().receiveEnergy(
                                MAX_ENERGY_SENT_PER_TICK, false), false);
                    }
                    else {
                        entity.getEnergyStorage().extractEnergy(neighboringBlockEntity.getEnergyStorage().receiveEnergy(
                                entity.getEnergyStorage().getEnergyStored(), false), false);
                    }
                }
            }
            //Generating energy:
            if (entity.blockProgress.getScale() != 0) {
                if (entity.blockEnergy.canReceive()) {
                    entity.blockEnergy.receiveEnergy(ENERGY_PER_TICK_FROM_ENERGIUM_INGOT, false);
                    entity.blockProgress.reduceScale();
                }
            } else {
                if (entity.itemStackHandler.getStackInSlot(0).getItem() == ItemsRegistry.ENERGIUM_INGOT.get()) {
                    entity.blockProgress.resetScale();
                    entity.itemStackHandler.extractItem(0, 1, false);
                }
            }
        }
    }

    public FEEnergyImpl getEnergyStorage() {
        return blockEnergy;
    }

    public ProgressScaleObject getProgressScale() {
        return blockProgress;
    }

    public BlockEntity getEntity() { return level.getBlockEntity(getBlockPos()); }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BasicElectricityGeneratorMenu(id, inventory, this);
    }
}
