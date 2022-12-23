package com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI;

import com.saulius.quantum_world.blocks.blocksGui.ModMenus;
import com.saulius.quantum_world.blocks.BlocksRegistry;
import com.saulius.quantum_world.blocks.blocksTile.BasicElectricityGeneratorEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BasicElectricityGeneratorMenu extends AbstractContainerMenu {
    public final BasicElectricityGeneratorEntity blockEntity;
    private final Level level;
    //private final ContainerData containerData;

    public BasicElectricityGeneratorMenu(int id, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(id, inventory, inventory.player.level.getBlockEntity(friendlyByteBuf.readBlockPos())); //, new SimpleContainerData(1)
    }
    public BasicElectricityGeneratorMenu(int id, Inventory inventory, BlockEntity blockEntity) { //ContainerData containerData
        super(ModMenus.BASIC_ELECTRICITY_GENERATOR_MENU.get(), id);
        checkContainerSize(inventory, 1);
        this.blockEntity = (BasicElectricityGeneratorEntity) blockEntity;
        this.level = inventory.player.level;
        //this.containerData = containerData;

        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler ->
                this.addSlot(new SlotItemHandler(iItemHandler, 0, 107, 29)));
        //addDataSlots(containerData);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 9 * 3;
    private static final int VANILLA_SLOT_COUNT = 9 + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 36;
    private static final int TE_INVENTORY_SLOT_COUNT = 1;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Loop starts if moveItemStackTo doesn't go off
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,VANILLA_FIRST_SLOT_INDEX +  VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, BlocksRegistry.BASIC_ELECTRICITY_GENERATOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 87 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 145));
        }
    }
}
