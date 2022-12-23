package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI.BasicElectricityGeneratorMenu;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergyAndTick;
import com.saulius.quantum_world.items.itemsRegistry.ItemsRegistry;
import com.saulius.quantum_world.tools.FEEnergyImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
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

public class BasicElectricityGeneratorEntity extends BlockEntity implements MenuProvider, AbstractModEnergyAndTick {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

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
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyOptItemHandler.invalidate();
        lazyOptEnergyHandler.invalidate();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemStackHandler.deserializeNBT(compoundTag.getCompound("inventory"));
        blockEnergy.setEnergy(compoundTag.getInt("current_energy_amount"));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("inventory", itemStackHandler.serializeNBT());
        compoundTag.putInt("current_energy_amount", blockEnergy.getEnergyStored());
        super.saveAdditional(compoundTag);
    }

    private LazyOptional<IEnergyStorage> lazyOptEnergyHandler = LazyOptional.empty();

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

    private final FEEnergyImpl blockEnergy = new FEEnergyImpl(10000, 20) {
        @Override
        public void onEnergyChange() {
            setChanged();
        }
    };
    private int scale = 0;

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BasicElectricityGeneratorEntity entity) {
        if (!level.isClientSide) {
            if (entity.getScale() != 0) {
                if (entity.getEnergyStorage().canReceive()) {
                    entity.blockEnergy.receiveEnergy(5, false);
                    entity.reduceScale();
                }
            } else {
                if (entity.itemStackHandler.getStackInSlot(0).getItem() == ItemsRegistry.ENERGIUM_INGOT.get()) {
                    entity.setScale(100);
                    entity.itemStackHandler.extractItem(0, 1, false);
                }
            }
        }
    }

    public IEnergyStorage getEnergyStorage() {
        return blockEnergy;
    }

    @Override
    public int getScale() { return scale;}
    public void setScale(int scale) { this.scale = scale;}

    public void reduceScale() { this.scale--;}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BasicElectricityGeneratorMenu(id, inventory, this);
    }
}
