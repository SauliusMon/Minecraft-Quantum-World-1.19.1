package com.saulius.quantum_world.networking.packets.energyAndScaleSync;

import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergyAndTick;
import com.saulius.quantum_world.tools.FEEnergyImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientEnergyAndScaleSync {

    private BlockPos blockPos;
    private int currentEnergyStored;
    private int scale;


    public ClientEnergyAndScaleSync(BlockPos blockPos, int currentEnergyStored, int scale) {
        this.blockPos = blockPos;
        this.currentEnergyStored = currentEnergyStored;
        this.scale = scale;
    }

    public ClientEnergyAndScaleSync(FriendlyByteBuf friendlyByteBuf) {
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.currentEnergyStored = friendlyByteBuf.readInt();
        this.scale = friendlyByteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeInt(currentEnergyStored);
        friendlyByteBuf.writeInt(scale);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractModEnergyAndTick blockEntity = (AbstractModEnergyAndTick) Minecraft.getInstance().level.getBlockEntity(blockPos);
            blockEntity.getEnergyStorage().setEnergy(currentEnergyStored);
            blockEntity.getProgressScale().setScale(scale);
        });
        return true;
    }
}
