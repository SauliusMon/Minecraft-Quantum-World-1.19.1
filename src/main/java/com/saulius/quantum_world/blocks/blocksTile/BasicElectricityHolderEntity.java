package com.saulius.quantum_world.blocks.blocksTile;

import com.saulius.quantum_world.blocks.blocksGui.BasicElectricityHolderGUI.BasicElectricityHolderMenu;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEntity;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicElectricityHolderEntity extends BlockEntity implements MenuProvider, AbstractModEntity {

    //protected final ContainerData data;
    public BasicElectricityHolderEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.BASIC_ELECTRICITY_HOLDER_ENTITY.get(), blockPos, blockState);

//        this.data = new ContainerData() {             //Container to hold values
//            @Override
//            public int get(int index) {
//                return 0;
//            }
//            @Override
//            public void set(int index, int value) {
//
//            }
//            @Override
//            public int getCount() {
//                return 1;
//            }
//        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("B.E.H.");
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

    private LazyOptional<IEnergyStorage> lazyOptEnergyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyOptEnergyHandler.cast();
        }
        return super.getCapability(cap, direction);
    }

    private final FEEnergyImpl blockEnergy = new FEEnergyImpl(40000, 20) {
        @Override
        public void onEnergyChange() {
            setChanged();
        }
    };

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BasicElectricityHolderEntity entity) {
       if (!level.isClientSide) {

       }
    }

    public FEEnergyImpl getEnergyStorage() {
        return blockEnergy;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BasicElectricityHolderMenu(id, inventory, this); //, this.data
    }

    public BlockEntity getEntity() { return level.getBlockEntity(getBlockPos()); }
}
