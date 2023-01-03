package com.saulius.quantum_world.networking.packets.energySync;

import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.tools.FEEnergyImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientEnergySync {

    private BlockPos blockPos;
    private int currentEnergyStored;


    public ClientEnergySync(BlockPos blockPos, int currentEnergyStored) {
        this.blockPos = blockPos;
        this.currentEnergyStored = currentEnergyStored;
    }

    public ClientEnergySync(FriendlyByteBuf friendlyByteBuf) {
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.currentEnergyStored = friendlyByteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeInt(currentEnergyStored);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractModEnergy blockEntity = (AbstractModEnergy) Minecraft.getInstance().level.getBlockEntity(blockPos);
            blockEntity.getEnergyStorage().setEnergy(currentEnergyStored);
        });
        return true;
    }
}
